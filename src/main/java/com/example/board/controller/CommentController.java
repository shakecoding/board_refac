package com.example.board.controller;

import com.example.board.domain.dto.CommentDTO;
import com.example.board.domain.vo.CommentVO;
import com.example.board.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // 댓글 추가
    @PostMapping
    // 요청 본문을 객체로 변환!
    public ResponseEntity<?> addComment(@RequestBody CommentDTO comment) {
        commentService.addComment(CommentVO.toEntity(comment));

        return ResponseEntity.ok().body("댓글이 추가되었습니다.");
    }

    // 댓글 조회 (특정 게시글의 모든 댓글 조회)
    @GetMapping("/{boardId}")
    public ResponseEntity<?> getCommentsByBoardId(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentService.getCommentsByBoardId(boardId));
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO comment) {
        comment.setCommentId(commentId);
        commentService.updateComment(CommentVO.toEntity(comment));
        return ResponseEntity.ok().body("댓글이 수정되었습니다.");
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.removeComment(commentId);
        return ResponseEntity.ok().body("댓글이 삭제되었습니다.");
    }
}
