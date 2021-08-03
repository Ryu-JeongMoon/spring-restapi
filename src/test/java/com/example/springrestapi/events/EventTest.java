package com.example.springrestapi.events;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class EventTest {

    @Test
    void builder() {
        Event event = Event.builder()
                .name("Spring REST Api")
                .description("REST Api development")
                .build();

        assertThat(event).isNotNull();
    }
    
    @Test
    void javaBean() {
        String name = "Event";
        String description = "String REST Api";

        Event event = new Event();
        event.setName(name);
        event.setDescription(description);

        assertThat(event.getName()).isEqualTo(name);
        assertThat(event.getDescription()).isEqualTo(description);
    }
}