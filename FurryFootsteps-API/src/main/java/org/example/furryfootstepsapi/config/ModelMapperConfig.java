package org.example.furryfootstepsapi.config;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.model.Review;
import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.dto.RequestDto;
import org.example.furryfootstepsapi.model.dto.ReviewDto;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper dtoMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addMappings(postDtoPropertyMap());
        modelMapper.addMappings(reviewDtoPropertyMap());
        modelMapper.addMappings(requestDtoPropertyMap());
        configureAvailabilityMapping(modelMapper);
        return modelMapper;
    }

    private PropertyMap<Post, PostDto> postDtoPropertyMap() {
        return new PropertyMap<Post, PostDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setDescription(source.getDescription());
                //map().setPetSize(source.getPetSize());
                map().setPrice(source.getPrice());
                map().setPetTypeId(source.getPetType().getId());
                map().setActivityTypeId(source.getActivityType().getId());
                map().setPicture(source.getPicture());
                map().setUserId(source.getUser().getId());
                map().setActivityTypeName(source.getActivityType().getType());
            }
        };
    }

    private PropertyMap<Review, ReviewDto> reviewDtoPropertyMap() {
        return new PropertyMap<Review, ReviewDto>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setComment(source.getComment());
                map().setRating(source.getRating());
                map().setUserId(source.getUser().getId());
                map().setPicture(source.getUser().getPicture());
                map().setPostId(source.getPost().getId());
            }
        };
    }

    private PropertyMap<Request, RequestDto> requestDtoPropertyMap() {
        return new PropertyMap<Request, RequestDto>() {
            @Override
            protected void configure() {
                map().setRequestId(source.getId());
                map().setAvailabilityId(source.getAvailability().getId());
                map().setUserPosterId(source.getUserPoster().getId());
                map().setUserRequesterId(source.getUserRequester().getId());
                map().setStatus(source.getStatus());
                map().setPostId(source.getPost().getId());
                map().setAvailabilityTime("");
            }
        };
    }

    private void configureAvailabilityMapping(ModelMapper modelMapper) {
        modelMapper.createTypeMap(Availability.class, AvailabilityRequest.class)
                .addMappings(mapping -> {
                    mapping.using(localDateTimeToStringConverter())
                            .map(Availability::getDateTimeFrom, AvailabilityRequest::setDateTimeFrom);
                    mapping.using(localDateTimeToStringConverter())
                            .map(Availability::getDateTimeTo, AvailabilityRequest::setDateTimeTo);
                    mapping.map(Availability::getId, AvailabilityRequest::setId);
                });
    }

    @Bean
    public Converter<LocalDateTime, String> localDateTimeToStringConverter() {
        return new AbstractConverter<LocalDateTime, String>() {
            @Override
            protected String convert(LocalDateTime source) {
                return source != null ? source.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) : null;
            }
        };
    }

}
