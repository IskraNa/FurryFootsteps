package org.example.furryfootstepsapi.model.dto;

import lombok.Data;
import lombok.Setter;
import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.Request;

@Data
public class RequestDto {
    private Long requestId;
    private Long postId;
    private Request.RequestStatus status;
    private Long userPosterId;
    private Long userRequesterId;
    private Long availabilityId;
    private String userPosterName;
    private String userRequesterName;
    private String PostName;
    private String availabilityTime;


    public Request toRequest(Availability availability) {
        Request request = new Request();
        request.setAvailability(availability);
        request.setStatus(status);
        return request;
    }


}
