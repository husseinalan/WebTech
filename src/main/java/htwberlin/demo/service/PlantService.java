package htwberlin.demo.service;

import htwberlin.demo.web.api.Plant;
import htwberlin.demo.persistence.PlantEntity;
import htwberlin.demo.persistence.PlantRepository;
import htwberlin.demo.web.api.PlantManipulationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
                .map(this::transformEntity)
                .collect(Collectors.toList());
    }

    public Plant findById(Long id) {
        Optional<PlantEntity> plantEntityOptional = plantRepository.findById(id);
        return plantEntityOptional.map(this::transformEntity).orElse(null);
    }

    public Plant create(PlantManipulationRequest request) {
        PlantEntity plantEntity = new PlantEntity(request.getName(), request.getDescription(), request.getWateringIntervalDays());

        // Set the next watering time
        LocalDateTime nextWateringTime = LocalDateTime.now().plusDays(request.getWateringIntervalDays());
        plantEntity.setNextWeteringTime(nextWateringTime);

        plantEntity = plantRepository.save(plantEntity);
        return transformEntity(plantEntity);
    }


    public Plant update(Long id, PlantManipulationRequest request) {
        Optional<PlantEntity> plantEntityOptional = plantRepository.findById(id);
        if (plantEntityOptional.isEmpty()) {
            return null;
        }

        PlantEntity plantEntity = plantEntityOptional.get();
        plantEntity.setName(request.getName());
        plantEntity.setDescription(request.getDescription());
        plantEntity.setWateringIntervalDays(request.getWateringIntervalDays());

        // Set the next watering time
        LocalDateTime nextWateringTime = LocalDateTime.now().plusDays(request.getWateringIntervalDays());
        plantEntity.setNextWeteringTime(nextWateringTime);

        plantEntity = plantRepository.save(plantEntity);
        return transformEntity(plantEntity);
    }

    public boolean deleteById(Long id) {
        if (!plantRepository.existsById(id)) {
            return false;
        }

        plantRepository.deleteById(id);
        return true;
    }

    private Plant transformEntity(PlantEntity plantEntity) {
        return new Plant(
                plantEntity.getName(),
                plantEntity.getDescription(),
                plantEntity.getWateringIntervalDays(),
                plantEntity.getId()
        );
    }

}