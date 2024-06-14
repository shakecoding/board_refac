package com.example.board.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

// 화면에 출력할 때 담아줄 DTO
@Component
@Data
public class BoardListDTO {

    private Long boardId;
    private String boardTitle;
    private String name;
    private String boardContent;
    private int boardViews;
    private LocalDateTime boardRegisterDate;
    private LocalDateTime boardUpdateDate;
    private int fileCount;
    private String providerId;

}
