package com.example.jpa_basic_crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa_basic_crud.dto.BoardSaveRequestDto;
import com.example.jpa_basic_crud.model.Board;
import com.example.jpa_basic_crud.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Transactional
	public void 글쓰기(BoardSaveRequestDto dto) {
		Board boardEntity = BoardSaveRequestDto.toEntity(dto);
		boardRepository.save(boardEntity);
	}

	@Transactional
	public Page<Board> 글목록보기(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional
	public Board 글상세보기(int id) {

		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new RuntimeException("해당 글은 삭제되었습니다.");
		});
		
		// 더티 체킹 = 조회수 증가
		board.setReadCount(board.getReadCount() + 1);

		return board;
	}
	
	@Transactional
	public void 글수정하기(int id, BoardSaveRequestDto dto) {
		// 가져오기
		Board boardEntity = boardRepository.findById(id).orElseThrow(() -> {
			return new RuntimeException("해당 글은 존재하지 않습니다.");
		});
		
		boardEntity.setTitle(dto.getTitle());
		boardEntity.setContent(dto.getContent());
		
		// boardRepository.save(boardEntity);		
		// 트랜잭션 처리 --> 글 수정하기() 메서드가 종료되는 시점에 더티 체킹 발생
	}
	
	@Transactional
	public int 글삭제하기(int id) {
		// 삭제되든 말든 무조건 ok 처리 되기 때문에 리턴타입 void
		return boardRepository.mDeleteById(id);
	}

}
