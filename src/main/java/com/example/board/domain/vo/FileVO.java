package com.example.board.domain.vo;

import com.example.board.domain.dto.FileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Getter
@ToString
@NoArgsConstructor
public class FileVO {

    private Long fileId;
    private String originalFileName;
    private String storedFileName;
    private Long fileSize;
    private LocalDateTime uploadTime;
    private Long boardId;

    @Builder
    public FileVO (Long fileId, String originalFileName, String storedFileName, Long fileSize, LocalDateTime uploadTime, Long boardId) {
        this.fileId = fileId;
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.fileSize = fileSize;
        this.uploadTime = uploadTime;
        this.boardId = boardId;
    }

    public static FileVO toEntity (FileDTO fileDTO){
        return FileVO.builder().fileId(fileDTO.getFileId())
                .originalFileName(fileDTO.getOriginalFileName())
                .storedFileName(fileDTO.getStoredFileName())
                .fileSize(fileDTO.getFileSize())
                .uploadTime(fileDTO.getUploadTime())
                .boardId(fileDTO.getBoardId())
                .build();
    }


}
