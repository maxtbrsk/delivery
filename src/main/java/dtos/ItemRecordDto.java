package dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemRecordDto(@NotBlank String name, @NotNull BigDecimal value, @NotBlank String description) {

}
