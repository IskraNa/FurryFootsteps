package org.example.furryfootstepsapi.model.requests;


import lombok.Data;

@Data
public class AvailabilityRequest {
    public String dateTimeFrom;
    public String dateTimeTo;
}
