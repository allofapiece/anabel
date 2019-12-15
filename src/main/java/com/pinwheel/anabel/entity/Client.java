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
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "description"})
@NoArgsConstructor
@Entity
@JsonFilter("serviceFilter")
public class Client implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.WithId.class)
    private Long id;

    @JsonView(Views.WithExtended.class)
    @Column(updatable = false)
    private String description;

    @Enumerated(value = EnumType.STRING)
    @JsonView(Views.WithGeneral.class)
    private Status status = Status.PENDING_VERIFICATION;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp updatedAt;
}
