package org.example.furryfootstepsapi.model.dto;

import lombok.Data;
import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;

import java.util.List;

@Data
public class PostDto {
    public Long id;
    public String description;
    public String petSize;
    public double price;
    public Long petTypeId;
    public Long activityTypeId;
    public Long userId;
    public List<AvailabilityRequest> availabilities;
}
