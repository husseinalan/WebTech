package htwberlin.demo.web;

import htwberlin.demo.service.ReminderService;
import htwberlin.demo.web.api.Reminder;
import htwberlin.demo.web.api.ReminderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin

@RequestMapping("/api/v1/reminders")
public class ReminderController {

    private final ReminderService reminderService;

    public ReminderController(ReminderService reminderService) {
        this.reminderService = reminderService;
    }

    @PostMapping("/api/v1/reminders/{id}")
    public ResponseEntity<Void> createReminder(@RequestBody ReminderRequest reminderRequest) {
        // Extrahieren der erforderlichen Daten aus dem ReminderRequest und Aufruf der entsprechenden Service-Methode
        Long plantId = reminderRequest.getPlantId();
        LocalDateTime dateTime = reminderRequest.getDateTime();
        String message = reminderRequest.getMessage();

        boolean success = reminderService.setReminder(plantId, dateTime, message);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "/api/v1/reminders")
    public ResponseEntity<Reminder> getReminder(@PathVariable Long id) {
        Optional<Reminder> reminder = reminderService.getReminder(id);
        return reminder.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/v1/reminders/{id}")
    public ResponseEntity<Void> updateReminder(@PathVariable Long id, @RequestBody ReminderRequest reminderRequest) {
        // Extrahieren der erforderlichen Daten aus dem ReminderRequest und Aufruf der entsprechenden Service-Methode
        LocalDateTime dateTime = reminderRequest.getDateTime();
        String message = reminderRequest.getMessage();

        boolean success = reminderService.updateReminder(id, dateTime, message);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/v1/reminders/{id}")
    public ResponseEntity<Void> deleteReminder(@PathVariable Long id) {
        boolean success = reminderService.deleteReminder(id);

        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
