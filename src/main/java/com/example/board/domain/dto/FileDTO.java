package com.example.board.domain.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class FileDTO {
    private Long fileId;
    private String originalFileName;
    private String storedFileName;
    private Long fileSize;
    private LocalDateTime uploadTime;
    private Long boardId;
}
