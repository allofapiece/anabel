package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Service;

import java.util.List;

/**
 * @version 1.0.0
 */
public interface ServiceService {
    Service get(Long id);

    List<Service> getByUserId(Long id);
}
