package com.example.board.service;

import com.example.board.domain.dto.BoardDTO;
import com.example.board.domain.dto.BoardDetailDTO;
import com.example.board.domain.dto.BoardListDTO;
import com.example.board.domain.oauth.CustomOAuth2User;
import com.example.board.domain.util.PagedResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BoardService {

    List<BoardListDTO> getBoardList(int page, int pageSize);

    int getBoardCount();

    void insertBoard(BoardDTO board, List<MultipartFile> files);

    // 상세보기 페이지.
    BoardDetailDTO getBoardById(Long boardId, CustomOAuth2User customOAuth2User);

    // 게시글 업데이트
    void updateBoard(BoardDetailDTO board, List<MultipartFile> files);

    // 게시글 삭제
    void deleteBoard(Long boardId);

    PagedResponse<BoardListDTO> selectAllByDateDESC(int page, int pageSize);
    PagedResponse<BoardListDTO> selectAllByDateASC(int page, int pageSize);
    PagedResponse<BoardListDTO> selectAllByViews(int page, int pageSize);


    PagedResponse<BoardListDTO> selectDQuery(int page, int pageSize, String sort, String search, String searchType);
}
