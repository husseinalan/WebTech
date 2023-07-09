package htwberlin.demo.web.api;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Reminder {
    private LocalDateTime dateTime;
    private String message;
    @Id
    private Long id;

    public Reminder() {
        // Leerer Konstruktor ohne Argumente
    }

    public Reminder(LocalDateTime dateTime, String message) {
        this.dateTime = dateTime;
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
