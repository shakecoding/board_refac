package com.example.board.domain.dto;

import lombok.Data;

@Data
public class BoardFileDTO {

    private Long fileId;
    private String storedFileName;
    private Long boardId;

}
