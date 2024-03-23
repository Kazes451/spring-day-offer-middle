package com.onedayoffer.taskdistribution.repositories;

import com.onedayoffer.taskdistribution.repositories.entities.Task;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    List<Task> findAllByEmployee_Id(@NotNull Integer employeeId);

    Optional<Task> findTaskByIdAndEmployee_Id(@NotNull Integer taskId, @NotNull Integer employeeId);
}
