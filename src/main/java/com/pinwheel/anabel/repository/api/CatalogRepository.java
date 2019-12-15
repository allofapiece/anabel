package com.pinwheel.anabel.repository.api;

import com.pinwheel.anabel.entity.Catalog;
import com.pinwheel.anabel.entity.CatalogProjection;
import com.pinwheel.anabel.entity.Service;
import com.pinwheel.anabel.entity.ServiceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "catalogs", path = "catalogs", excerptProjection = CatalogProjection.class)
public interface CatalogRepository extends JpaRepository<Catalog, Long> {

    @RestResource(path = "serviceId", rel = "serviceId")
    List<Catalog> findByService_Id(Long id);
}
