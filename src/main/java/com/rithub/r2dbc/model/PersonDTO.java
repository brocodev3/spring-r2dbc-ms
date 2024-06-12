package com.rithub.r2dbc.model;

import lombok.Data;

@Data
public class PersonDTO {
    private Long id;
    private String name;
    private int age;
    // No password field here to avoid exposure
}