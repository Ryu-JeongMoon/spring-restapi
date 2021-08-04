package com.example.springrestapi.mapper;

import com.example.springrestapi.events.Event;
import com.example.springrestapi.events.EventDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ModernMapper extends GenericMapper<EventDto, Event>{
}
