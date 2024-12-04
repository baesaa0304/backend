package com.woozuda.backend.account.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.woozuda.backend.account.dto.JoinDTO;
import com.woozuda.backend.global.entity.BaseTimeEntity;
import jakarta.persistence.*;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="woozuda_user")
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String username;

    private String password;

    @Column(nullable = false)
    private String role;

    public static UserEntity transEntity(JoinDTO joinDTO){
        return new UserEntity(null, joinDTO.getUsername(), joinDTO.getPassword(), "ROLE_ADMIN");
    }
}
