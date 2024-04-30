package org.example.furryfootstepsapi.config;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.dto.PostDto;
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
                map().setUserId(source.getUser().getId());
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
