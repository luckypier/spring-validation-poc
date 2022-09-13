package com.poc.library;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Book {

    @NotBlank
    private String address;

    @NotNull(message = "floor cannot be null")
    private String floor;

    @Email
    private String mail;
}
