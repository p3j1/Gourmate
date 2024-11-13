package com.sparta.gourmate.global.common;

import com.sparta.gourmate.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @CreatedDate
    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedBy
    private Long updatedBy;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    private Long deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;

    private Boolean isDeleted = false;

    public void markAsDeleted(User user) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = user.getId();
        this.isDeleted = true;
    }
}
