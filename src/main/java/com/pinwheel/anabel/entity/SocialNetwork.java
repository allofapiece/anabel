package com.pinwheel.anabel.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.pinwheel.anabel.entity.enums.Protocol;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name", "protocol", "domain", "icon", "createdAt", "updatedAt"})
@NoArgsConstructor
@Entity
public class SocialNetwork implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.WithId.class)
    private Long id;

    @JsonView(Views.WithGeneral.class)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @JsonView(Views.WithGeneral.class)
    private Protocol protocol;

    @JsonView(Views.WithGeneral.class)
    private String domain;

    @JsonView(Views.WithGeneral.class)
    private String icon;

    @CreationTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp updatedAt;

    @OneToMany(mappedBy = "socialNetwork", cascade = CascadeType.ALL)
    @JsonView(Views.WithDependencies.class)
    private Set<UserSocial> userSocials;
}
