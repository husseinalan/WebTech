package htwberlin.demo.persistence;

import htwberlin.demo.web.api.Reminder;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface ReminderRepository extends CrudRepository<Reminder, Long> {

    Reminder findByDateTime(LocalDateTime dateTime);
}

