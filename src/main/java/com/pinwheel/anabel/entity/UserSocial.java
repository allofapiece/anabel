package com.pinwheel.anabel.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "link", "socialNetwork"})
@NoArgsConstructor
@Entity
public class UserSocial implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.WithId.class)
    private Long id;

    @JsonView(Views.WithGeneral.class)
    private String link;

    @ManyToOne()
    @JoinColumn
    @JsonView(Views.WithDependencies.class)
    private User user;

    @ManyToOne()
    @JoinColumn
    @JsonView(Views.WithGeneral.class)
    private SocialNetwork socialNetwork;
}
