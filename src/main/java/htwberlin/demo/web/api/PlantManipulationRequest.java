package htwberlin.demo.web.api;

public class PlantManipulationRequest {

    private String name;
    private String description;
    private int wateringIntervalDays;

    public PlantManipulationRequest(String name, String description, int wateringIntervalDays) {
        this.name = name;
        this.description = description;
        this.wateringIntervalDays = wateringIntervalDays;
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

    public int getWateringIntervalDays() {
        return wateringIntervalDays;
    }

    public void setWateringIntervalDays(int wateringIntervalDays) {
        this.wateringIntervalDays = wateringIntervalDays;
    }

}
