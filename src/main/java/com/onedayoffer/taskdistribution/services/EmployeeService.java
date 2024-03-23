package com.onedayoffer.taskdistribution.services;

import com.onedayoffer.taskdistribution.DTO.EmployeeDTO;
import com.onedayoffer.taskdistribution.DTO.TaskDTO;
import com.onedayoffer.taskdistribution.DTO.TaskStatus;
import com.onedayoffer.taskdistribution.repositories.EmployeeRepository;
import com.onedayoffer.taskdistribution.repositories.TaskRepository;
import com.onedayoffer.taskdistribution.repositories.entities.Task;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDTO> getEmployees(@NotNull Sort.Direction sortDirection) {
        return employeeRepository.findAllAndSort(Sort.by(sortDirection, "fio"))
                .stream()
                .map((element) -> modelMapper.map(element, EmployeeDTO.class))
                .toList();
    }

    @Transactional
    public @NotNull EmployeeDTO getOneEmployee(@NotNull Integer id) {
        return employeeRepository.findById(id)
                .map((element) -> modelMapper.map(element, EmployeeDTO.class))
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
    }

    public @NotNull List<TaskDTO> getTasksByEmployeeId(@NotNull Integer id) {
        return taskRepository.findAllByEmployee_Id(id).stream()
                .map((element) -> modelMapper.map(element, TaskDTO.class))
                .toList();
    }

    @Transactional
    public void changeTaskStatus(Integer employeeId, Integer taskId, TaskStatus status) {
        var task = taskRepository.findTaskByIdAndEmployee_Id(taskId, employeeId)
                .orElseThrow(() -> new NoSuchElementException("Task with specified id not found or not belongs to specified user"));
        task.setStatus(status);
        taskRepository.save(task);
    }

    @Transactional
    public void postNewTask(Integer employeeId, TaskDTO newTask) {
        var employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee not found"));
        var taskEntity = modelMapper.map(newTask, Task.class);
        taskEntity.setEmployee(employee);
        taskRepository.save(taskEntity);
    }
}
