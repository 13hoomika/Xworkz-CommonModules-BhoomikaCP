package com.xworkz.userapp.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class AuditEntity implements Serializable {
    private String createdBy;
    private LocalDateTime createdTime = LocalDateTime.now();
    private String updateBy;
    private LocalDateTime updatedTime;
}
