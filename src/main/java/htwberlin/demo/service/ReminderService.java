package htwberlin.demo.service;

import htwberlin.demo.persistence.ReminderRepository;
import htwberlin.demo.web.api.Reminder;
import htwberlin.demo.web.api.ReminderRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class ReminderService {

    private final ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public boolean setReminder(Long id, LocalDateTime dateTime, String message) {
        Optional<Reminder> reminderOptional = reminderRepository.findById(id);
        Reminder reminder;
        if (reminderOptional.isPresent()) {
            reminder = reminderOptional.get();
        } else {
            reminder = new Reminder();
            reminder.setId(id);
        }

        reminder.setDateTime(dateTime);
        reminder.setMessage(message);

        reminderRepository.save(reminder);

        return true;
    }

    public boolean updateReminder(Long id, LocalDateTime dateTime, String message) {
        Optional<Reminder> reminderOptional = reminderRepository.findById(id);
        if (reminderOptional.isEmpty()) {
            return false; // Erinnerung nicht gefunden
        }
        Reminder reminder = reminderOptional.get();
        reminder.setDateTime(dateTime);
        reminder.setMessage(message);

        reminderRepository.save(reminder);

        return true;
    }

    public boolean deleteReminder(Long id) {
        if (!reminderRepository.existsById(id)) {
            return false; // Erinnerung nicht gefunden
        }

        reminderRepository.deleteById(id);
        return true;
    }

    public Optional<Reminder> getReminder(Long id) {
        return reminderRepository.findById(id);
    }
}
