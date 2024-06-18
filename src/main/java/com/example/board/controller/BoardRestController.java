package com.example.board.controller;

import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardRestController {

    private final BoardService boardService;

//    @GetMapping("/sorted-list")
//    public ResponseEntity<List<BoardListDTO>> getSortedBoardList(@RequestParam String sort) {
//        List<BoardListDTO> sortedBoards = null;
//
//        switch (sort) {
//            case "newest":
//                sortedBoards = boardService.getBoardList();
//                break;
//            case "oldest":
//                sortedBoards = boardService.selectAllByDateASC();
//                break;
//            case "views":
//                sortedBoards = boardService.selectAllByViews();
//                break;
//            default:
//                sortedBoards = boardService.selectAll();
//        }
//
//        return ResponseEntity.ok(sortedBoards);
//    }

}
