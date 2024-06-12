package com.rithub.r2dbc.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("person")
public class Person {
    @Id
    private Long id;
    private String name;
    private int age;
    private String password;  // Sensitive field
}