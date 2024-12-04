package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ClientRecordDto(
        @NotBlank String name,
        @NotBlank String telefone,
        @NotBlank String cpf,
        @NotBlank String password
) {}
