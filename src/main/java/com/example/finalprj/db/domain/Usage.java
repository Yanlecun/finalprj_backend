package com.example.finalprj.db.domain;

import lombok.* ;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.* ;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "authority")
@IdClass(Authority.class) // @Id를 여러 개 가질 경우
public class Usage extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Id
    private Long playground_id ;
    @Id
    private Long user_id ;

    private Long status; // -1 : 관리자
}
