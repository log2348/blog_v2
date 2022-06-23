package com.example.jpa_basic_crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa_basic_crud.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	
	@Modifying // 네이티브 쿼리 사용시 수정, 삭제, 저장할 때
	@Query(value= "DELETE FROM board WHERE id = :id", nativeQuery = true)
	int mDeleteById(int id);

}
