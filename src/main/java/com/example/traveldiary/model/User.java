package com.example.traveldiary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class User extends AbstractEntity {

    @Schema(description = "user name",
            example = "alex",
            required = true)
    @Column(name = "username")
    private String username;

    @Schema(description = "encoded user password",
            example = "$2a$12$3TPtUdEaPH4ARZAMhi3V/uvcMvU4hut6ZywRE7TUh9ASIz0BBQuiu",
            required = true)
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Schema(description = "date of user creation (filled in automatically)",
            example = "2020-11-27 19:41:43.623399",
            required = true)
    @Column(name = "created")
    private LocalDateTime created;

    @Schema(description = "determines the user's ability to work in the service",
            example = "true",
            required = true)
    @Column(name = "enable")
    private Boolean enabled;

    @Schema(description = "user roles",
            example = "[\"SENIOR\", \"USER\"]",
            required = true)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Schema(description = "user's travels ")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Travel> travels;

    @Schema(description = "when and what user did last time")
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserLastActivity lastActivity;
}
