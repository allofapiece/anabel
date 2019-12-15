package com.pinwheel.anabel.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import com.pinwheel.anabel.service.validation.UniqueEmail;
import com.pinwheel.anabel.service.validation.UniqueSlug;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name"})
@NoArgsConstructor
@Entity
@JsonFilter("serviceFilter")
public class Service implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.WithId.class)
    private Long id;

    @JsonView(Views.WithExtended.class)
    @Column(updatable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @JsonView(Views.WithGeneral.class)
    private Status status = Status.ACTIVE;

    @Type(type = "text")
    @JsonView(Views.WithExtended.class)
    private String about;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER)
    @JsonView(Views.WithDependencies.class)
    private Set<Catalog> catalogs = new HashSet<>();

    @CreationTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp updatedAt;
}
