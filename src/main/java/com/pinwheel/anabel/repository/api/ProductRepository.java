package com.pinwheel.anabel.repository.api;

import com.pinwheel.anabel.entity.Product;
import com.pinwheel.anabel.entity.ProductProjection;
import com.pinwheel.anabel.entity.Service;
import com.pinwheel.anabel.entity.ServiceProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "products", path = "products", excerptProjection = ProductProjection.class)
public interface ProductRepository extends JpaRepository<Product, Long> {
}
