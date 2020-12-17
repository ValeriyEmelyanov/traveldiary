package com.example.traveldiary.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_last_activity")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class UserLastActivity extends AbstractEntity {

    @Schema(
            description = "user",
            example = "user",
            required = true)
    @OneToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @Schema(
            description = "date and time of activity",
            example = "2020-12-14 19:09:51.873345",
            required = true)
    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    @Schema(
            description = "the method name with arguments (value or type name)",
            example = "create(TravelDto, user)",
            required = true)
    @Column(name = "description")
    private String description;

}
