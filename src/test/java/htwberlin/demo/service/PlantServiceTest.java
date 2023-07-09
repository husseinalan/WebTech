package htwberlin.demo.service;

import htwberlin.demo.persistence.PlantEntity;
import htwberlin.demo.persistence.PlantRepository;
import htwberlin.demo.web.api.Plant;
import htwberlin.demo.web.api.PlantManipulationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlantServiceTest {

    private PlantRepository plantRepository;
    private PlantService plantService;

    @BeforeEach
    void setUp() {
        plantRepository = mock(PlantRepository.class);
        plantService = new PlantService(plantRepository);
    }

    @Test
    void findAll_ReturnsListOfPlants() {
        // Arrange
        PlantEntity plantEntity1 = new PlantEntity("Plant1", "Description1", 1);
        PlantEntity plantEntity2 = new PlantEntity("Plant2", "Description2", 2);
        when(plantRepository.findAll()).thenReturn(Arrays.asList(plantEntity1, plantEntity2));

        // Act
        List<Plant> plants = plantService.findAll();

        // Assert
        assertEquals(2, plants.size());
        assertEquals("Plant1", plants.get(0).getName());
        assertEquals("Description2", plants.get(1).getDescription());
        verify(plantRepository, times(1)).findAll();
    }

    @Test
    void findById_WithValidId_ReturnsPlant() {
        // Arrange
        Long id = 1L;
        PlantEntity plantEntity = new PlantEntity("Plant", "Description", 3);
        plantEntity.setId(id); // Set the ID field to a non-null value
        when(plantRepository.findById(id)).thenReturn(Optional.of(plantEntity));

        // Act
        Plant plant = plantService.findById(id);

        // Assert
        assertNotNull(plant);
        assertEquals("Plant", plant.getName());
        assertEquals("Description", plant.getDescription());
        assertEquals(3, plant.getWateringIntervalDays());
        verify(plantRepository, times(1)).findById(id);
    }


    @Test
    void findById_WithInvalidId_ReturnsNull() {
        // Arrange
        Long id = 100L;
        when(plantRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Plant plant = plantService.findById(id);

        // Assert
        assertNull(plant);
        verify(plantRepository, times(1)).findById(id);
    }

    @Test
    void create_ReturnsCreatedPlant() {
        // Arrange
        PlantManipulationRequest request = new PlantManipulationRequest("Plant", "Description", 3);
        PlantEntity savedPlantEntity = new PlantEntity("Plant", "Description", 3);
        savedPlantEntity.setId(1L);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expectedNextWateringTime = now.plusDays(3);
        savedPlantEntity.setNextWateringTime(expectedNextWateringTime);
        when(plantRepository.save(Mockito.any(PlantEntity.class))).thenReturn(savedPlantEntity);

        // Act
        Plant createdPlant = plantService.create(request);

        // Assert
        assertNotNull(createdPlant);
        assertEquals("Plant", createdPlant.getName());
        assertEquals("Description", createdPlant.getDescription());
        assertEquals(3, createdPlant.getWateringIntervalDays());
        assertEquals(expectedNextWateringTime, savedPlantEntity.getNextWateringTime());
        verify(plantRepository, times(1)).save(Mockito.any(PlantEntity.class));
    }

    @Test
    void update_WithValidId_ReturnsUpdatedPlant() {
        // Arrange
        Long id = 1L;
        PlantManipulationRequest request = new PlantManipulationRequest("Updated Plant", "Updated Description", 5);
        PlantEntity existingPlantEntity = new PlantEntity("Plant", "Description", 3);
        existingPlantEntity.setId(id);
        when(plantRepository.findById(id)).thenReturn(Optional.of(existingPlantEntity));
        when(plantRepository.save(Mockito.any(PlantEntity.class))).thenReturn(existingPlantEntity);

        // Act
        Plant updatedPlant = plantService.update(id, request);

        // Assert
        assertNotNull(updatedPlant);
        assertEquals("Updated Plant", updatedPlant.getName());
        assertEquals("Updated Description", updatedPlant.getDescription());
        assertEquals(5, updatedPlant.getWateringIntervalDays());
        verify(plantRepository, times(1)).findById(id);
        verify(plantRepository, times(1)).save(Mockito.any(PlantEntity.class));
    }

    @Test
    void update_WithInvalidId_ReturnsNull() {
        // Arrange
        Long id = 100L;
        PlantManipulationRequest request = new PlantManipulationRequest("Updated Plant", "Updated Description", 5);
        when(plantRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Plant updatedPlant = plantService.update(id, request);

        // Assert
        assertNull(updatedPlant);
        verify(plantRepository, times(1)).findById(id);
        verify(plantRepository, never()).save(Mockito.any(PlantEntity.class));
    }

    @Test
    void deleteById_WithExistingId_ReturnsTrue() {
        // Arrange
        Long id = 1L;
        when(plantRepository.existsById(id)).thenReturn(true);

        // Act
        boolean result = plantService.deleteById(id);

        // Assert
        assertTrue(result);
        verify(plantRepository, times(1)).existsById(id);
        verify(plantRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteById_WithNonExistingId_ReturnsFalse() {
        // Arrange
        Long id = 100L;
        when(plantRepository.existsById(id)).thenReturn(false);

        // Act
        boolean result = plantService.deleteById(id);

        // Assert
        assertFalse(result);
        verify(plantRepository, times(1)).existsById(id);
        verify(plantRepository, never()).deleteById(id);
    }
}
