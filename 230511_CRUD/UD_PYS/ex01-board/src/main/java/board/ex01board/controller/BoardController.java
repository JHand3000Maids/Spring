package board.ex01board.controller;

import board.ex01board.dto.BoardDTO;
import board.ex01board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board") // 대표 주소 묶기
public class BoardController {
    private final BoardService boardService; // 생성자 주입 방식
    // 1. CREATE====================================================
    // create.html로 들어가기
    @GetMapping("/create")
    public String saveForm() {
        return "create";
    }
    // 글 작성
    @PostMapping("/create")
    public String save(@ModelAttribute BoardDTO boardDTO) { // create.html의 name과 DTO의 필드값이 동일 -> setter알아서 호출
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }

    // 2. READ===================================================
    // list. DB에서 전체 게시글 가져와서 list.html에 보여주기
    @GetMapping("/")
    public String findAll(Model model) { // 데이터 가져온다 -> 모델 객체 사용
        List<BoardDTO> boardDTOList = boardService.findAll(); // 전체 게시글 가져오기
        model.addAttribute("boardList", boardDTOList); // 가져온 데이터 모델 객체에 담는다
        return "list";
    }

    // detail
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        // 1) 해당 게시글의 조회수를 하나 올리고 2) 게시글 데이터를 가져와서 detail.html에 출력 -> 두번 호출 발생
        boardService.updateHits(id); // 1)
        BoardDTO boardDTO = boardService.findByID(id); // 2) DTO로 받기
        model.addAttribute("board", boardDTO); // 모델에 담기
        return "detail";
    }

    // 3. UPDATE==================================================
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findByID(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO); // 수정
        model.addAttribute("board", board); // 수정이 반영된 객체를 detail로 가져간다
        return "detail"; // 수정하고나서 상세 페이지로
//        return "redirect:/board/" + boardDTO.getId(); // 이 방법은 조회수에 영향줄 수 있음
    }

    // 4. DELETE===================================================
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }
}

