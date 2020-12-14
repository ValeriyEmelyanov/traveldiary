package com.example.traveldiary.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_last_activity")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserLastActivity {

    @Schema(
            description = "Unique identifier, corresponds to a unique user ID",
            example = "1",
            required = true)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Schema(
            description = "user",
            example = "user",
            required = true)
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(
            description = "date and time of activity",
            example = "2020-12-14 19:09:51.873345",
            required = true)
    @Column(name = "last_activity")
    private LocalDateTime lactActivity;

    @Schema(
            description = "the method name with arguments (value or type name)",
            example = "create(TravelDto, user)",
            required = true)
    @Column(name = "description")
    private String description;

}
