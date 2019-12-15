package com.pinwheel.anabel.repository.api;

import com.pinwheel.anabel.entity.Service;
import com.pinwheel.anabel.entity.ServiceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "services", path = "services", excerptProjection = ServiceProjection.class)
public interface ServiceRepository extends JpaRepository<Service, Long> {

    @RestResource(path = "userId", rel = "userId")
    List<Service> findByUser_Id(Long id);
}
