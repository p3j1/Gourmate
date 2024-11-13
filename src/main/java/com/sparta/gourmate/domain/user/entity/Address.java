package com.sparta.gourmate.domain.user.entity;

import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "p_addresses")
public class Address extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String address;

    @Column
    private String addressRequest;

    @Column
    private Boolean isDeleted = false;

    @Column
    private Long deletedBy;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime deletedAt;


    public Address(User user, String address, String addressRequest) {
        this.user = user;
        this.address = address;
        this.addressRequest = addressRequest;
    }


    public void updateAddress(String address, String addressRequest) {
        this.address = address;
        this.addressRequest = addressRequest;
    }

    public void softDelete(Long deletedBy) {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy;
    }
}
