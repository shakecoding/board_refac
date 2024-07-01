package com.example.board.service;

import com.example.board.domain.dto.BoardFileDTO;
import org.springframework.stereotype.Service;

@Service
public interface BoardFileService {

    void savaBoardFile(BoardFileDTO boardFileDTO);

}
