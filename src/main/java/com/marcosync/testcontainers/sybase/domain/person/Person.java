package com.marcosync.testcontainers.sybase.domain.person;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private int uuid;
    private String name;
    private int age;
}
