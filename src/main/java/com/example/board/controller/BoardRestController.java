package com.example.board.controller;

import com.example.board.domain.dto.BoardListDTO;
import com.example.board.domain.util.PagedResponse;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@Slf4j
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("/sorted-list")
    public ResponseEntity<PagedResponse<BoardListDTO>> getSortedBoardList(@RequestParam String sort,
                                                                 @RequestParam(value = "page", defaultValue = "1") int page,
                                                                 @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        PagedResponse<BoardListDTO> sortedBoards = switch (sort) {
            case "oldest" -> boardService.selectAllByDateDESC(page, pageSize);
            case "views" -> boardService.selectAllByViews(page, pageSize);
            default -> boardService.selectAllByDateASC(page, pageSize);
        };

//        switch (sort) {
//            case "newest":
//                sortedBoards = boardService.getBoardList(page, pageSize);
//                break;
//            case "oldest":
//                sortedBoards = boardService.selectAllByDateASC(page, pageSize);
//                break;
//            case "views":
//                sortedBoards = boardService.selectAllByViews(page, pageSize);
//                break;
//            default:
//                sortedBoards = boardService.getBoardList(page, pageSize);
//        }
        log.info(sortedBoards.toString());

        return ResponseEntity.ok(sortedBoards);
    }

}
