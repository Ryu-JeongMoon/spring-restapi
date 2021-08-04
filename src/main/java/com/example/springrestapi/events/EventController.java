package com.example.springrestapi.events;

import com.example.springrestapi.mapper.ModernMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_VALUE)
public class EventController {

    private final EventRepository eventRepository;
    private final ModernMapper modernMapper;
    private final EventValidator eventValidator;

    // HATEOAS link added
    @PostMapping
    public ResponseEntity createEvent(@RequestBody @Validated EventDto eventDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        eventValidator.validate(eventDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        Event event = modernMapper.toEntity(eventDto);
        event.update();
        Event newEvent = eventRepository.save(event);

        /**
         * ControllerLinkBuilder -> WebMvcBuilder 로 변경
         * 이놈은 deprecated
         */

        WebMvcLinkBuilder builder = linkTo(EventController.class).slash(newEvent.getId());
        URI createdURI = builder.toUri();
        EventModel eventModel = new EventModel(newEvent);

        eventModel.add(linkTo(EventController.class).withRel("query-events"));
        eventModel.add(linkTo(EventController.class).withRel("update-events"));
        return ResponseEntity.created(createdURI).body(eventModel);
    }
}