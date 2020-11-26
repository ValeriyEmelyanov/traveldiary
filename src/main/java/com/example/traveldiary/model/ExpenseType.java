package com.example.traveldiary.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expense_type")
@NoArgsConstructor
@Setter
@Getter
public class ExpenseType extends AbstractEntity {

    @Column(name = "name")
    private String name;

}
