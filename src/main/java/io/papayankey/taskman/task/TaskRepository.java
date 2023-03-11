package io.papayankey.taskman.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {

    List<TaskEntity> findByStatus(String status);

    @Query(value = "UPDATE tasks t SET t.description = :description, t.status = :status WHERE t.id = :id ", nativeQuery = true)
    void findByIdAndUpdate(
            @Param("id") Integer id,
            @Param("description") String description,
            @Param("status") TaskStatus status
    );

}