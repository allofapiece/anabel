package com.pinwheel.anabel.entity;

import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

/**
 */
@Projection(name = "default", types = {Service.class})
public interface ServiceProjection {
    Long getId();

    String getName();

    String getAbout();

    Status getStatus();

    Set<Catalog> getCatalogs();

    User getUser();
}
