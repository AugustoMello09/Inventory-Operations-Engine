package io.github.augustomello09.Inventory.services;

import io.github.augustomello09.Inventory.dtos.WarehouseDTOResponse;
import io.github.augustomello09.Inventory.dtos.WarehouseInsertDTORequest;
import io.github.augustomello09.Inventory.dtos.WarehouseUpdateDTORequest;
import io.github.augustomello09.Inventory.entities.Warehouse;
import io.github.augustomello09.Inventory.exceptions.BusinessException;
import io.github.augustomello09.Inventory.exceptions.ResourceNotFoundException;
import io.github.augustomello09.Inventory.mappers.WarehouseMapper;
import io.github.augustomello09.Inventory.repositories.WarehouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WarehouseService {

    private final WarehouseMapper warehouseMapper;
    private final WarehouseRepository warehouseRepository;

    @Transactional
    public WarehouseDTOResponse create(WarehouseInsertDTORequest request){
        if (warehouseRepository.existName(request.getName())) {
            throw new BusinessException("Name Aready exists");
        }

        Warehouse warehouse = warehouseMapper.toEntity(request);

        warehouseRepository.save(warehouse);

        return warehouseMapper.toDto(warehouse);
    }

    @Transactional
    public void active(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID Not Found"));

        warehouse.setActive(!warehouse.isActive());

        warehouseRepository.save(warehouse);
    }

    @Transactional(readOnly = true)
    public Page<WarehouseDTOResponse> paged(Pageable page) {
        Page<Warehouse> paged = warehouseRepository.findAll(page);
        return paged.map(warehouseMapper::toDto);
    }

    @Transactional(readOnly = true)
    public WarehouseDTOResponse findById(Long id) {
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        Warehouse entity = warehouse.orElseThrow(() -> new ResourceNotFoundException("ID NotFound"));
        return warehouseMapper.toDto(entity);
    }

    public WarehouseDTOResponse update(Long id, WarehouseUpdateDTORequest request) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ID Not Found"));

        if (request.getName() != null) {

            if (!request.getName().equals(warehouse.getName()) && warehouseRepository.existName(request.getName())){
                throw new BusinessException("Name Already exists");
            }

            warehouse.setName(request.getName());
            warehouse.setUpdatedAt(LocalDate.now());
        }

        if (request.getLocation() != null) {
            warehouse.setLocation(request.getLocation());
            warehouse.setUpdatedAt(LocalDate.now());
        }

        warehouseRepository.save(warehouse);

        return warehouseMapper.toDto(warehouse);
    }
}
