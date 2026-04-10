package com.todoapp.entity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task extends PanacheEntity{

    @Column(nullable = false)
    public String title;

    @Column(length = 50)
    public String description;

    @Column(nullable = false)
    public boolean completed;

    @Column(name = "created_at")
    public LocalDateTime createdAt = LocalDateTime.now();

}