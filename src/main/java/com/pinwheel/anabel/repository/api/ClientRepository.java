package com.pinwheel.anabel.repository.api;

import com.pinwheel.anabel.entity.Catalog;
import com.pinwheel.anabel.entity.CatalogProjection;
import com.pinwheel.anabel.entity.Client;
import com.pinwheel.anabel.entity.ClientProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "clients", path = "clients", excerptProjection = ClientProjection.class)
public interface ClientRepository extends JpaRepository<Client, Long> {

    @RestResource(path = "serviceId", rel = "serviceId")
    List<Client> findByProduct_Catalog_Service_id(Long id);
}
