package com.example.board.domain.vo;

import com.example.board.domain.dto.BoardFileDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BoardFileVO {

    private Long fileId;
    private String storedFileName;
    private Long boardId;

    @Builder
    public BoardFileVO (Long fileId, String storedFileName, Long boardId) {
        this.fileId = fileId;
        this.storedFileName = storedFileName;
        this.boardId = boardId;
    }

    public static BoardFileVO toEntity (BoardFileDTO boardFileDTO){
        return BoardFileVO.builder().fileId(boardFileDTO.getFileId())
                .storedFileName(boardFileDTO.getStoredFileName())
                .boardId(boardFileDTO.getBoardId())
                .build();
    }

}
