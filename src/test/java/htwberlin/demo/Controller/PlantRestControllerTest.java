package htwberlin.demo.Controller;

import htwberlin.demo.service.PlantService;
import htwberlin.demo.service.ReminderService;
import htwberlin.demo.web.PlantRestController;
import htwberlin.demo.web.api.Plant;
import htwberlin.demo.web.api.PlantManipulationRequest;
import htwberlin.demo.web.api.ReminderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlantRestControllerTest {
    private PlantRestController plantRestController;

    @Mock
    private ReminderService reminderService;

    @Mock
    private PlantService plantService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        plantRestController = new PlantRestController(reminderService, plantService);
    }

    @Test
    public void fetchPlants_ReturnsListOfPlants() {
        // Arrange
        List<Plant> expectedPlants = Arrays.asList(
                new Plant("plant1", "plant1-description", 2, 1),
                new Plant("plant2", "plant2-description", 3, 2)
        );
        when(plantService.findAll()).thenReturn(expectedPlants);

        // Act
        ResponseEntity<List<Plant>> response = plantRestController.fetchPlants();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedPlants, response.getBody());
        verify(plantService, times(1)).findAll();
    }

    @Test
    public void fetchPlantById_WithValidId_ReturnsPlant() {
        // Arrange
        Long id = 1L;
        Plant expectedPlant = new Plant("plant1", "plant1-description", 2, 1);
        when(plantService.findById(id)).thenReturn(expectedPlant);

        // Act
        ResponseEntity<Plant> response = plantRestController.fetchPlantById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(expectedPlant, response.getBody());
        verify(plantService, times(1)).findById(id);
    }

    @Test
    public void fetchPlantById_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;
        when(plantService.findById(id)).thenReturn(null);

        // Act
        ResponseEntity<Plant> response = plantRestController.fetchPlantById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService, times(1)).findById(id);
    }

    @Test
    public void createPlant_ReturnsCreated() throws URISyntaxException {
        // Arrange
        PlantManipulationRequest request = new PlantManipulationRequest("Plant", "Description", 3);
        Plant createdPlant = new Plant("Plant", "Description", 3, 1);
        when(plantService.create(request)).thenReturn(createdPlant);

        // Act
        ResponseEntity<Void> response = plantRestController.createPlants(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(plantService, times(1)).create(request);
    }

    @Test
    public void updatePlant_WithValidId_ReturnsUpdatedPlant() {
        // Arrange
        Long id = 1L;
        PlantManipulationRequest request = new PlantManipulationRequest("Plant", "Description", 3);
        Plant updatedPlant = new Plant("Plant", "Description", 3, 1);
        when(plantService.update(id, request)).thenReturn(updatedPlant);

        // Act
        ResponseEntity<Plant> response = plantRestController.updatePlant(id, request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedPlant, response.getBody());
        verify(plantService, times(1)).update(id, request);
    }

    @Test
    public void updatePlant_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;
        PlantManipulationRequest request = new PlantManipulationRequest("Plant", "Description", 3);
        when(plantService.update(id, request)).thenReturn(null);

        // Act
        ResponseEntity<Plant> response = plantRestController.updatePlant(id, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(plantService, times(1)).update(id, request);
    }

    @Test
    public void setReminder_WithValidData_ReturnsOk() {
        // Arrange
        Long id = 1L;
        ReminderRequest reminderRequest = new ReminderRequest(LocalDateTime.now(), "Water the plant");
        when(reminderService.setReminder(id, reminderRequest.getDateTime(), reminderRequest.getMessage()))
                .thenReturn(true);

        // Act
        ResponseEntity<Void> response = plantRestController.setReminder(id, reminderRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(reminderService, times(1))
                .setReminder(id, reminderRequest.getDateTime(), reminderRequest.getMessage());
    }

    @Test
    public void setReminder_WithInvalidData_ReturnsBadRequest() {
        // Arrange
        Long id = 1L;
        ReminderRequest reminderRequest = new ReminderRequest(LocalDateTime.now(), "Water the plant");
        when(reminderService.setReminder(id, reminderRequest.getDateTime(), reminderRequest.getMessage()))
                .thenReturn(false);

        // Act
        ResponseEntity<Void> response = plantRestController.setReminder(id, reminderRequest);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        verify(reminderService, times(1))
                .setReminder(id, reminderRequest.getDateTime(), reminderRequest.getMessage());
    }

    @Test
    public void deletePlant_WithValidId_ReturnsOk() {
        // Arrange
        Long id = 1L;
        when(plantService.deleteById(id)).thenReturn(true);

        // Act
        ResponseEntity<Void> response = plantRestController.deletePlant(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(plantService, times(1)).deleteById(id);
    }

    @Test
    public void deletePlant_WithInvalidId_ReturnsNotFound() {
        // Arrange
        Long id = 1L;
        when(plantService.deleteById(id)).thenReturn(false);

        // Act
        ResponseEntity<Void> response = plantRestController.deletePlant(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(plantService, times(1)).deleteById(id);
    }
}
