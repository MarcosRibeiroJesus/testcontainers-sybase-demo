package com.marcosync.testcontainers.sybase.persistence;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "people")
@Data
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uuid;
    private String name;
    private int age;
}
