package com.example.traveldiary.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expense_type")
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
public class ExpenseType extends AbstractEntity {

    @Schema(
            description = "name of the expense type",
            example = "Transort",
            required = true)
    @Column(name = "name")
    private String name;

}
