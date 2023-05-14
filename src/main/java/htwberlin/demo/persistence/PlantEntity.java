package htwberlin.demo.persistence;

import jakarta.persistence.*;


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

    public PlantEntity(String name, Integer wateringIntervalDays) {
        this.name = name;
        this.wateringIntervalDays = wateringIntervalDays;
    }

    public PlantEntity(String name, String description, Integer wateringIntervalDays) {
        this.name = name;
        this.description = description;
        this.wateringIntervalDays = wateringIntervalDays;
    }

    protected PlantEntity() {}

    public Long getId() {
        return id;
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
}