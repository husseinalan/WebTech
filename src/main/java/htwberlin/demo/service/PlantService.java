package htwberlin.demo.service;

import htwberlin.demo.web.api.Plant;
import htwberlin.demo.persistence.PlantEntity;
import htwberlin.demo.persistence.PlantRepository;
import htwberlin.demo.web.api.PlantManipulationRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlantService {

    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    public List<Plant> findAll() {
        List<PlantEntity> plants = plantRepository.findAll();
        return plants.stream()
                .map(this::transforEntity)
                .collect(Collectors.toList());
    }

    public Plant findById(Long id) {
        var plantEntity = plantRepository.findById(id);
        return plantEntity.map(this::transforEntity).orElse(null);
    }

    public Plant create(PlantManipulationRequest request) {
        var plantEntity = new PlantEntity(request.getName(), request.getDescription(), request.getWateringIntervalDays());
        plantEntity = plantRepository.save(plantEntity);
        return transforEntity(plantEntity);
    }

    public Plant update(Long id, PlantManipulationRequest request) {
        var plantEntityOptional = plantRepository.findById(id);
        if (plantEntityOptional.isEmpty()) {
            return null;
        }

        var plantEntity = plantEntityOptional.get();
        plantEntity.setName(request.getName());
        plantEntity.setDescription(request.getDescription());
        plantEntity.setWateringIntervalDays(request.getWateringIntervalDays());
        plantEntity = plantRepository.save(plantEntity);

        return transforEntity(plantEntity);
    }

    public boolean deleteById(Long id) {
        if (!plantRepository.existsById(id)) {
            return false;
        }

        plantRepository.deleteById(id);
        return true;
    }

    private Plant transforEntity(PlantEntity plantEntity) {
        return new Plant(
                plantEntity.getName(),
                plantEntity.getDescription(),
                plantEntity.getWateringIntervalDays(),
                plantEntity.getId()
        );
    }
}