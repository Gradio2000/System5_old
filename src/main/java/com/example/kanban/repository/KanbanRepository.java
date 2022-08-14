package com.example.kanban.repository;

import com.example.kanban.model.Kanban;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KanbanRepository extends JpaRepository<Kanban, Integer> {
}