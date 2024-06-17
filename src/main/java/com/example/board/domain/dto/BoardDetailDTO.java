package com.example.board.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class BoardDetailDTO {

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
