package com.example.board.mapper;

import com.example.board.domain.dto.FileDTO;
import com.example.board.domain.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    void insertFile(FileVO fileVO);
    List<FileDTO> getFileListByBoardId(Long boardId);
    FileDTO getFileById(Long fileId);
    void deleteFiles(Long boardId);
}
