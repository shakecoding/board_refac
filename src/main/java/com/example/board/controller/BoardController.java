package com.example.board.controller;

import com.example.board.domain.dto.BoardDTO;
import com.example.board.domain.dto.BoardDetailDTO;
import com.example.board.domain.dto.BoardListDTO;
import com.example.board.domain.dto.FileDTO;
import com.example.board.domain.oauth.CustomOAuth2User;
import com.example.board.service.BoardService;
import com.example.board.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    @GetMapping("/list")
    public String list(@RequestParam(value = "page", defaultValue = "1") int page,
                       @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                       Model model) {

        int totalBoard = boardService.getBoardCount();
        int totalPages = (int) Math.ceil((double) totalBoard / pageSize);

        int pageGroupSize = 5;
        int startPage = ((page - 1) / pageGroupSize) * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        List<BoardListDTO> boards = boardService.getBoardList(page, pageSize);

        model.addAttribute("boards", boards);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("pageSize", pageSize);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "board/list";
    }

    // 게시글 작성 폼
    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("board", new BoardDTO());
        return "board/write";
    }

    // 게시글 작성 처리
    @PostMapping("/write")
    public String write(@ModelAttribute BoardDTO board, @RequestParam("files") List<MultipartFile> files, @RequestParam("providerId") String providerId, RedirectAttributes redirectAttributes) {
        board.setProviderId(providerId);
        boardService.insertBoard(board, files);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 등록되었습니다.");
        return "redirect:/board/list";
    }

    // 게시글 조회
    @GetMapping("/detail/{boardId}")
    public String view(@PathVariable Long boardId, Model model, @AuthenticationPrincipal CustomOAuth2User customUser) {
        BoardDetailDTO board = boardService.getBoardById(boardId, customUser);
        List<FileDTO> files = fileService.getFileListByBoardId(boardId);
        model.addAttribute("board", board);
        model.addAttribute("files", files);
        return "board/detail";
    }

    // 게시글 수정 폼
    @GetMapping("/edit/{boardId}")
    public String editForm(@PathVariable Long boardId, Model model, @AuthenticationPrincipal CustomOAuth2User customUser) {
        BoardDetailDTO board = boardService.getBoardById(boardId, customUser);
        model.addAttribute("board", board);
        return "board/edit";
    }

    // 게시글 수정 처리
    @PostMapping("/edit")
    public String update(BoardDetailDTO board, RedirectAttributes redirectAttributes, @RequestParam("files") List<MultipartFile> files) {
        boardService.updateBoard(board, files);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 수정되었습니다.");
        return "redirect:/board/detail/" + board.getBoardId();
    }

    // 게시글 삭제 처리
    @PostMapping("/delete/{boardId}")
    public String delete(@PathVariable Long boardId, RedirectAttributes redirectAttributes) {
        boardService.deleteBoard(boardId);
        redirectAttributes.addFlashAttribute("message", "게시글이 성공적으로 삭제되었습니다.");
        return "redirect:/board/list";
    }




}
