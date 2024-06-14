package com.example.board.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// request 로 받아올 때 담아줄 DTO
@Component
@Data
public class BoardDTO {

    private Long boardId;
    private String boardTitle;
    private String providerId;
    private String boardContent;
    private int boardViews;
    private LocalDateTime boardRegisterDate;
    private LocalDateTime boardUpdateDate;

}
