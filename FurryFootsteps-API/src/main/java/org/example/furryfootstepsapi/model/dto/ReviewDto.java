package org.example.furryfootstepsapi.model.dto;

import lombok.Data;

@Data
public class ReviewDto {

    public Long id;
    public String comment;
    public double rating;
    public Long userId;
    public Long postId;

}
