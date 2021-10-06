package com.example.finalprj.db.user.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "sp_authority")
@IdClass(Authority.class) // @Id를 여러 개 가질 경우
public class Authority implements GrantedAuthority {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MANAGER = "ROLE_MANAGER";
    public static final String ROLE_USER = "ROLE_USER";

    public static final Authority ADMIN_AUTHORITY = Authority.builder().authority(ROLE_ADMIN).build();
    public static final Authority MANAGER_AUTHORITY = Authority.builder().authority(ROLE_MANAGER).build();
    public static final Authority USER_AUTHORITY = Authority.builder().authority(ROLE_USER).build();


    @Id
    private Long userId;

    @Id
    private String authority;

}
