package io.github.augustomello09.Inventory.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTOResponse {

    private Long id;
    private String name;
    private String location;
    private boolean active;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
