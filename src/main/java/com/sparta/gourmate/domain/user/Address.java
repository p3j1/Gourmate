package com.sparta.gourmate.domain.user;

import com.sparta.gourmate.domain.order.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_addresses")
public class Address extends BaseEntity {  // BaseEntity 상속 (타임스탬프와 생성/수정자 필드 처리)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID addressId;  // 주소 ID, PK

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)  // 사용자 ID (FK)
    private User user;  // 사용자 엔티티와 관계 설정

    @Column(nullable = false)
    private String address;  // 주소

    @Column
    private String addressRequest;  // 요청 사항

    @Column(nullable = false)
    private boolean isDeleted;  // 삭제 여부

    public void prePersist() {
        this.isDeleted = false;  // 기본값 false
    }

    // Getter, Setter
    public UUID getAddressId() {
        return addressId;
    }

    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressRequest() {
        return addressRequest;
    }

    public void setAddressRequest(String addressRequest) {
        this.addressRequest = addressRequest;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}