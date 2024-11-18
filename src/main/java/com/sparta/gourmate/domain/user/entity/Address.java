package com.sparta.gourmate.domain.user.entity;

import com.sparta.gourmate.domain.user.dto.AddressRequestDto;
import com.sparta.gourmate.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String addressRequest;

    public Address(User user, String address, String addressRequest) {
        this.user = user;
        this.address = address;
        this.addressRequest = addressRequest;
    }

    public void update(AddressRequestDto requestDto) {
        this.address = requestDto.getAddress();
        this.addressRequest = requestDto.getAddressRequest();
    }
}
