package com.example.finalprj.db.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Playground extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @JsonProperty("pg_name")
    String pgName;
    String lat;
    String lng;

    @OneToOne
    User user;
}
