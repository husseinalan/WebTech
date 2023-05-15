package htwberlin.demo.web;

import htwberlin.demo.web.api.Plant;
import htwberlin.demo.service.PlantService;
import htwberlin.demo.web.api.PlantManipulationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class PlantRestController {

    private List<Plant> plants;

    private final PlantService plantService; // Repository-Injektion ermöglichen

    public PlantRestController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping(path = "/api/v1/plants")
    public ResponseEntity<List<Plant>> fetchPlants() {
        return ResponseEntity.ok(plantService.findAll());
    }

    @GetMapping(path = "/api/v1/plants/{id}")
    public ResponseEntity<Plant> fetchPlantById(@PathVariable Long id){
        var plant = plantService.findById(id);
        return plant != null? ResponseEntity.ok(plant) : ResponseEntity.notFound().build();
    }

    @PostMapping(path = "/api/v1/plants")
    public ResponseEntity<Void> createPlants(@RequestBody PlantManipulationRequest request) throws URISyntaxException {
        var plant = plantService.create(request);
        URI uri = new URI("/api/v1/plants/" + plant.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(path = "/api/v1/plants/{id}")
    public ResponseEntity<Plant> udpatePlat(@PathVariable Long id, @RequestBody PlantManipulationRequest request){
     var plant = plantService.update(id, request);
        return plant != null? ResponseEntity.ok(plant) : ResponseEntity.notFound().build();
    }

    @DeleteMapping (path = "/api/v1/plants/{id}")
    public ResponseEntity<Void> deletePlant(@PathVariable Long id) {
        boolean successful = plantService.deleteById(id);
        return successful? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

}