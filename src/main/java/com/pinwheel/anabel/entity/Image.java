package com.pinwheel.anabel.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String filename;

    private String extension;

    private String path;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "image", fetch = FetchType.LAZY)
    @MapKey(name = "alias")
    private Map<String, Thumbnail> thumbnails = new HashMap<>();

    @OneToOne(mappedBy = "avatar")
    private User user;
}
