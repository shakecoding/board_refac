package com.example.board.service;

import com.example.board.domain.dto.BoardFileDTO;
import com.example.board.domain.vo.BoardFileVO;
import com.example.board.mapper.BoardFileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardFileServiceImpl implements BoardFileService {

    private final BoardFileMapper boardFileMapper;

    @Override
    public void savaBoardFile(BoardFileDTO boardFileDTO) {
        boardFileMapper.insertBoardFile(BoardFileVO.toEntity(boardFileDTO));
    }
}
