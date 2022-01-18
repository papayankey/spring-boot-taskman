package io.papayankey.taskman.model;

import io.papayankey.taskman.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;

    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    private void prePersis() {
        createdAt = new Date();
        updatedAt = createdAt;
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = new Date();
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

}
