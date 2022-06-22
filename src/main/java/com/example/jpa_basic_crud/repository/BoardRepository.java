package com.example.jpa_basic_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa_basic_crud.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
