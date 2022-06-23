package com.example.jpa_basic_crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.jpa_basic_crud.dto.BoardSaveRequestDto;
import com.example.jpa_basic_crud.model.Board;
import com.example.jpa_basic_crud.service.BoardService;

@Controller
public class BoardController {

	// http://localhost:8081/list?page=1
	@Autowired
	private BoardService boardService;

	@GetMapping({ "", "/", "list" })
	public String list(@PageableDefault(size = 3, sort = "id", direction = Direction.DESC) Pageable pageable,
			Model model) {

		// 서비스 접근해서 목록 가져와야 된다.
		Page<Board> boards = boardService.글목록보기(pageable);
		model.addAttribute("boards", boardService.글목록보기(pageable)); // key, value
		return "list";
	}

	@GetMapping("/listPage")
	@ResponseBody
	public Page<Board> listPage(@PageableDefault(size = 3, sort = "id", direction = Direction.DESC) Pageable pageable,
			Model model) {

		// 서비스 접근해서 목록 가져와야 된다.
		Page<Board> boards = boardService.글목록보기(pageable);
		model.addAttribute("boards", boardService.글목록보기(pageable));
		return boards;
	}

	// 페이지 요청
	@GetMapping("/saveForm")
	public String saveForm() {
		return "saveForm";
	}

	// 동작
	@PostMapping("/save")
	@ResponseBody
	public String save(@RequestBody BoardSaveRequestDto dto) { // title, content
		// 서비스 객체로 가서 DB 저장 요청
		boardService.글쓰기(dto);
		return "ok";
	}

	@GetMapping("/board/{id}")
	public String detail(@PathVariable int id, Model model) {
		// 서비스에 가서 데이터 가져오기
		boardService.글상세보기(id);
		model.addAttribute("board", boardService.글상세보기(id));
		return "detail";
	}
	
	@GetMapping("/updateForm/{id}")
	public String updateForm(@PathVariable int id, Model model) {
		Board board = boardService.글상세보기(id);
		model.addAttribute("board", board);
		return "updateForm";
	}
	
	// 글 수정 주소 설계
	// 스프링의 기본 파싱 전략 -> key-value
	// @RequestBody - messageConverter가 자동으로 파싱해서 오브젝트로 만들어준다
	@PutMapping("/board/{id}")
	@ResponseBody // 데이터 리턴
	public String updateBoard(@PathVariable int id, @RequestBody BoardSaveRequestDto dto) {
		// 서비스, 더티체킹, 영속성 이용
		boardService.글수정하기(id, dto);		
		return "ok";
	}

}
