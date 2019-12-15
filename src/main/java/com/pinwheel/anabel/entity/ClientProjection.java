package com.pinwheel.anabel.entity;

import org.springframework.data.rest.core.config.Projection;

import java.sql.Timestamp;
import java.util.Set;


@Projection(name = "default", types = {Client.class})
public interface ClientProjection {
    Long getId();
    String getDescription();
    Status getStatus();
    User getUser();
    Product getProduct();
    Timestamp getCreatedAt();
    Timestamp getUpdatedAt();
}
