package com.pinwheel.anabel.entity.setting;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private String description;


    @Type(type = "boolean")
    private boolean constrained;


    private String dataType;


    private Double minValue;


    private Double maxValue;

    @OneToMany(mappedBy = "setting", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AllowedSettingValue> allowedSettingValues = new HashSet<>();

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "section_id")
    private SettingSection section;
}
