package com.pinwheel.anabel.entity.education;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String abbreviation;

    private String address;

    @OneToMany(mappedBy = "establishment")
    private Set<Faculty> faculties = new HashSet<>();
}
