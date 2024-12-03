package com.example.springboot.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID idOrder,
        @NotNull UUID userId,
        @NotNull List<UUID> itemIds,
        @NotNull List<Integer> quantities,
        @NotBlank String street,
        @NotBlank String number,
        @NotBlank String district,
        @NotBlank String city
) {}