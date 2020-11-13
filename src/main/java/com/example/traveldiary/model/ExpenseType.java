package com.example.traveldiary.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "expense_type")
public class ExpenseType extends AbstractEntity {

    @Column(name = "name")
    private String name;

    public ExpenseType() {
    }

    public ExpenseType(Long id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
