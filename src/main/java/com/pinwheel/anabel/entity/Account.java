package com.pinwheel.anabel.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column
    @Type(type = "text")
    private String about;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @Column
    @CreationTimestamp
    private Timestamp createdAt;

    @Column
    @UpdateTimestamp
    private Timestamp updatedAt;
}
