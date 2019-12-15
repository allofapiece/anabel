package com.pinwheel.anabel.controller.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.pinwheel.anabel.entity.Service;
import com.pinwheel.anabel.entity.Views;
import com.pinwheel.anabel.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RepositoryRestController
@RequestMapping("api//services")
@RequiredArgsConstructor
public class ServiceController {

    private final RepositoryEntityLinks links;

    private final ServiceService serviceService;

    @GetMapping("search/userId")
    @JsonView(Views.WithGeneral.class)
    public ResponseEntity<MappingJacksonValue> getServices(@RequestParam Long id, HttpServletRequest request) {
        List<Service> services = serviceService.getByUserId(id);

        Resources<Service> resources = new Resources<>(services);

        resources.add(new Link(request.getRequestURL().toString()).withSelfRel());

        MappingJacksonValue wrapper = new MappingJacksonValue(resources);

        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("serviceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "id", "name", "about", "catalogs"))
                .addFilter("catalogFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "id", "name", "products"))
                .addFilter("productFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "id", "name", "price")));

        return ResponseEntity.ok(wrapper);
    }

    @GetMapping("/{id}")
    @JsonView(Views.WithGeneral.class)
    public ResponseEntity<MappingJacksonValue> getService(@PathVariable Long id, HttpServletRequest request) {
        Service services = serviceService.get(id);

        Resource<Service> resource = new Resource<>(services);

        resource.add(new Link(request.getRequestURL().toString()).withSelfRel());

        MappingJacksonValue wrapper = new MappingJacksonValue(resource);

        wrapper.setFilters(new SimpleFilterProvider()
                .addFilter("serviceFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "id", "name", "about", "catalogs"))
                .addFilter("catalogFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "id", "name", "products"))
                .addFilter("productFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "id", "name", "price"))/*
                .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                        "id", "fullName", "status", "slug"))*/);

        return ResponseEntity.ok(wrapper);
    }
}
