package com.pinwheel.anabel.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name", "price"})
@NoArgsConstructor
@Entity
@JsonFilter("productFilter")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.WithId.class)
    private Long id;

    @JsonView(Views.WithGeneral.class)
    @Column(updatable = false)
    private String name;

    @JsonView(Views.WithGeneral.class)
    private BigDecimal price;

    @Enumerated(value = EnumType.STRING)
    @JsonView(Views.WithGeneral.class)
    private Status status = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "catalog_id")
    private Catalog catalog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonView(Views.WithTimestamps.class)
    private User user;

    @CreationTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp updatedAt;
}
