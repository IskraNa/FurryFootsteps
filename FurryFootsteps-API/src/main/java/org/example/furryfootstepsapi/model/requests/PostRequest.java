package org.example.furryfootstepsapi.model.requests;

import java.util.List;

public class PostRequest {
    public String description;
    public String petSize;
    public double price;
    public Long petTypeId;
    public Long activityTypeId;
    public Long userId;
    public List<AvailabilityRequest> availabilities;
}
