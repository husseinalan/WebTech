package htwberlin.demo.persistence;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "plants")
public class PlantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "watering_interval_days", nullable = false)
    private Integer wateringIntervalDays;

    @Column(name = "next_watering_time")
    private LocalDateTime nextWateringTime;

    public PlantEntity(String name, String description, Integer wateringIntervalDays) {
        this.name = name;
        this.description = description;
        this.wateringIntervalDays = wateringIntervalDays;
    }

    protected PlantEntity() {}

    // Getter and setter methods for the id field
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWateringIntervalDays() {
        return wateringIntervalDays;
    }

    public void setWateringIntervalDays(Integer wateringIntervalDays) {
        this.wateringIntervalDays = wateringIntervalDays;
    }

    public LocalDateTime getNextWateringTime() {
        return nextWateringTime;
    }

    public void setNextWateringTime(LocalDateTime nextWateringTime) {
        this.nextWateringTime = nextWateringTime;
    }
}
