package com.example.StockManagement.data.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(includeFieldNames = false)
@MappedSuperclass
public class Audit {

    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createDate;

    @Column(name = "updated_date")
    private LocalDateTime updateDate;

    // TODO add deleted
    @Column(name = "deleted_by", nullable = false)
    private boolean deleted = false;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
        this.createdBy = getCurrentUsername();
        this.updateDate = this.createDate;
        this.updatedBy = this.createdBy;
        this.deleted = false;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = LocalDateTime.now();
        this.updatedBy = getCurrentUsername();
    }

    public void markDeleted() {
        this.deleted = true;
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}