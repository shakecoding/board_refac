package com.example.board.mapper;

import com.example.board.domain.vo.BoardFileVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardFileMapper {

    void insertBoardFile(BoardFileVO boardFileVO);

}
