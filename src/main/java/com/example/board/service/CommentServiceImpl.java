package com.example.board.service;

import com.example.board.domain.dto.CommentListDTO;
import com.example.board.domain.vo.CommentVO;
import com.example.board.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    @Override
    @Transactional
    public void addComment(CommentVO comment) {
        commentMapper.insertComment(comment);
    }

    @Override
    public List<CommentListDTO> getCommentsByBoardId(Long boardId) {
        return commentMapper.selectCommentsByBoardId(boardId);
    }

    @Override
    @Transactional
    public void updateComment(CommentVO comment) {
        commentMapper.updateComment(comment);
    }

    @Override
    @Transactional
    public void removeComment(Long commentId) {
        commentMapper.deleteComment(commentId);
    }
}
