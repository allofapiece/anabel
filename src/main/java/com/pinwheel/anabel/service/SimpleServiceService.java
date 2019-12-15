package com.pinwheel.anabel.service;

import com.pinwheel.anabel.entity.Service;
import com.pinwheel.anabel.repository.api.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * @version 1.0.0
 */
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class SimpleServiceService implements ServiceService {
    private final ServiceRepository serviceRepository;

    @Override
    public Service get(Long id) {
        return serviceRepository.findById(id).get();
    }

    @Override
    public List<Service> getByUserId(Long id) {
        return serviceRepository.findByUser_Id(id);
    }
}
