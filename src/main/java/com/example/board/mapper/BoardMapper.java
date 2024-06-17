package com.example.board.mapper;

import com.example.board.domain.dto.BoardDetailDTO;
import com.example.board.domain.dto.BoardListDTO;
import com.example.board.domain.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardListDTO> selectAll(int startRow, int endRow);

    int countBoard();

    Long getSeq();

    void insertBoard(BoardVO board);

    BoardDetailDTO selectBoardDetail(Long boardId);

    // 현재 작성자 pk 가져오기
    String getProviderIdByBoardId(Long boardId);

    // 조회수 +1
    void plusViews(Long boardId);



}
