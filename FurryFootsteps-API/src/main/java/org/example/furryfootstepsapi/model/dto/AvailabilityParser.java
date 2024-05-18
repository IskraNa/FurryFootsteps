package org.example.furryfootstepsapi.model.dto;

import org.example.furryfootstepsapi.model.Availability;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AvailabilityParser {


    public static Availability parseAvailability(String selectedAvailability) {
        // Define the date format of the selectedAvailability string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        // Parse the selectedAvailability string to obtain start and end LocalDateTime objects
        LocalDateTime startDateTime = LocalDateTime.parse(selectedAvailability, formatter);
        // Assuming end date is 1 hour later than start date for simplicity
        LocalDateTime endDateTime = startDateTime.plusHours(1);

        // Create and return an Availability object with the parsed start and end LocalDateTime objects
        return new Availability(startDateTime, endDateTime);
    }
}
