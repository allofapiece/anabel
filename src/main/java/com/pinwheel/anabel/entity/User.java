package com.pinwheel.anabel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.pinwheel.anabel.service.validation.UniqueEmail;
import com.pinwheel.anabel.service.validation.UniqueSlug;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.*;

@Data
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "email", "status", "displayName", "firstName", "lastName", "about", "createdAt", "updatedAt",
        "roles"})
@NoArgsConstructor
@UniqueSlug
@UniqueEmail
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(Views.WithId.class)
    private Long id;

    @NotNull
    @Size(min = 4, max = 40)
    @JsonView(Views.WithExtended.class)
    @Column(updatable = false)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @JsonView(Views.WithGeneral.class)
    private Status status;

    @JsonView(Views.WithGeneral.class)
    private String displayName;

    @JsonView(Views.WithGeneral.class)
    private String firstName;

    @JsonView(Views.WithGeneral.class)
    private String lastName;

    @JsonView(Views.WithGeneral.class)
    private String slug;

    @Type(type = "text")
    @JsonView(Views.WithExtended.class)
    private String about;

    @CreationTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp createdAt;

    @UpdateTimestamp
    @JsonView(Views.WithTimestamps.class)
    private Timestamp updatedAt;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @JsonView(Views.WithGeneral.class)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonView(Views.WithGeneral.class)
    private Set<UserSocial> socials = new HashSet<>();

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id")
    @JsonView(Views.WithGeneral.class)
    private Image avatar;

    @OneToMany(mappedBy = "user")
    @JsonView(Views.WithDependencies.class)
    private Set<Notification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonView(Views.WithDependencies.class)
    private Set<OrderComment> orderComments = new HashSet<>();

    @ManyToMany(mappedBy = "employees")
    @JsonView(Views.WithDependencies.class)
    private Set<Company> companies = new HashSet<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonView(Views.WithDependencies.class)
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(Views.WithDependencies.class)
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonView(Views.WithDependencies.class)
    @JsonIgnore
    private List<Password> passwords = new LinkedList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonView(Views.WithDependencies.class)
    private List<VerificationToken> verificationTokens = new LinkedList<>();

    @Transient
    @Formula("select * from message m where m.author_id = id or m.receiver_id = id")
    @Basic(fetch = FetchType.LAZY)
    @JsonView(Views.WithDependencies.class)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonView(Views.WithDependencies.class)
    private Set<Upload> uploads = new HashSet<>();

    public boolean isAdmin() {
        return this.getRoles().contains(Role.ADMIN);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status != Status.BANNED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == Status.ACTIVE;
    }

    public void setPassword(Password password) {
        this.getPasswords().add(0, password);
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return !this.getPasswords().isEmpty() ? this.getPasswords()
                .stream()
                .filter(p -> p.getStatus().equals(Status.ACTIVE))
                .findFirst()
                .get()
                .getValue() : null;
    }

    public void addVerificationToken(VerificationToken verificationToken) {
        this.verificationTokens.add(verificationToken);
    }

    public VerificationToken getVerificationToken(int i) {
        return this.verificationTokens.get(i);
    }

    public VerificationToken getLastVerificationToken() {
        return !getVerificationTokens().isEmpty() ? getVerificationToken(getVerificationTokens().size() - 1) : null;
    }

    public String getFullName() {
        String name = "";

        if (!StringUtils.isEmpty(this.firstName)) {
            name = this.firstName;
        }

        if (!StringUtils.isEmpty(this.lastName)) {
            name = StringUtils.isEmpty(name) ? this.lastName : name + " " + this.lastName;
        }

        return StringUtils.isEmpty(name) ? this.displayName : name;
    }
}
