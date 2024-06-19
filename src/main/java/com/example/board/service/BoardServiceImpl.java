package com.example.board.service;

import com.example.board.domain.dto.BoardDTO;
import com.example.board.domain.dto.BoardDetailDTO;
import com.example.board.domain.dto.BoardListDTO;
import com.example.board.domain.dto.FileDTO;
import com.example.board.domain.oauth.CustomOAuth2User;
import com.example.board.domain.util.PagedResponse;
import com.example.board.domain.vo.BoardVO;
import com.example.board.domain.vo.FileVO;
import com.example.board.mapper.BoardMapper;
import com.example.board.mapper.FileMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Override
    public List<BoardListDTO> getBoardList(int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = startRow + pageSize;

        return boardMapper.selectAll(startRow, endRow);
    }

    @Override
    public int getBoardCount() {
        return boardMapper.countBoard();
    }

    @Override
    public void insertBoard(BoardDTO board, List<MultipartFile> files) {

        Long boardId = boardMapper.getSeq();
        board.setBoardId(boardId);
        System.out.println(board);
        boardMapper.insertBoard(BoardVO.toEntity(board)); // 게시글 정보 저장

        // 현재 날짜를 기반으로 폴더 경로 생성
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String datePath = now.format(formatter);

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue; // 파일이 비어있으면 건너뜀

            String originalFileName = file.getOriginalFilename();
            String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFileName;
            Long fileSize = file.getSize();

            try {
                // 파일 저장 경로 설정
                Path directoryPath = Paths.get("C:/upload/" + datePath);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath); // 폴더가 없으면 생성
                }
                Path filePath = directoryPath.resolve(storedFileName);
                // 파일 저장
                Files.copy(file.getInputStream(), filePath);

                FileDTO fileDTO = new FileDTO();
                fileDTO.setBoardId(boardId);
                fileDTO.setOriginalFileName(originalFileName);
                fileDTO.setStoredFileName(directoryPath + "/" + storedFileName);
                fileDTO.setFileSize(fileSize);

                fileMapper.insertFile(FileVO.toEntity(fileDTO)); // 파일 정보 저장

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public BoardDetailDTO getBoardById(Long boardId, CustomOAuth2User customOAuth2User) {
        String boardProviderId = boardMapper.getProviderIdByBoardId(boardId);

        if (customOAuth2User == null || !customOAuth2User.getProviderId().equals(boardProviderId)) {
            // 조회 수+1
            boardMapper.plusViews(boardId);
        }
        return boardMapper.selectBoardDetail(boardId);
    }

    @Override
    public void updateBoard(BoardDetailDTO board, List<MultipartFile> files) {

        boardMapper.updateBoard(BoardVO.toEntity(board));
        fileMapper.deleteFiles(board.getBoardId());

        // 현재 날짜를 기반으로 폴더 경로 생성
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String datePath = now.format(formatter);

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue; // 파일이 비어있으면 건너뜀

            String originalFileName = file.getOriginalFilename();
            String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFileName;
            Long fileSize = file.getSize();

            try {
                // 파일 저장 경로 설정
                Path directoryPath = Paths.get("C:/upload/" + datePath);
                if (!Files.exists(directoryPath)) {
                    Files.createDirectories(directoryPath); // 폴더가 없으면 생성
                }
                Path filePath = directoryPath.resolve(storedFileName);
                // 파일 저장
                Files.copy(file.getInputStream(), filePath);

                FileDTO fileDTO = new FileDTO();
                fileDTO.setBoardId(board.getBoardId());
                fileDTO.setOriginalFileName(originalFileName);
                fileDTO.setStoredFileName(directoryPath + "/" + storedFileName);
                fileDTO.setFileSize(fileSize);

                fileMapper.insertFile(FileVO.toEntity(fileDTO)); // 파일 정보 저장

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void deleteBoard(Long boardId) {
        boardMapper.deleteBoard(boardId);
    }

    @Override
    public PagedResponse<BoardListDTO> selectAllByDateDESC(int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = startRow + pageSize;

        int totalBoard = boardMapper.countBoard();
        int totalPages = (int) Math.ceil((double) totalBoard / pageSize);

        int pageGroupSize = 5;
        int startPage = ((page - 1) / pageGroupSize) * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        return new PagedResponse<>(boardMapper.selectAll(startRow, endRow), page, totalPages, pageSize, totalBoard);
    }

    @Override
    public PagedResponse<BoardListDTO> selectAllByDateASC(int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = startRow + pageSize;

        int totalBoard = boardMapper.countBoard();
        int totalPages = (int) Math.ceil((double) totalBoard / pageSize);

        int pageGroupSize = 5;
        int startPage = ((page - 1) / pageGroupSize) * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        return new PagedResponse<>(boardMapper.selectAllByDateASC(startRow, endRow), page, totalPages, pageSize, totalBoard);

    }

    @Override
    public PagedResponse<BoardListDTO> selectAllByViews(int page, int pageSize) {
        int startRow = (page - 1) * pageSize;
        int endRow = startRow + pageSize;

        int totalBoard = boardMapper.countBoard();
        int totalPages = (int) Math.ceil((double) totalBoard / pageSize);

        int pageGroupSize = 5;
        int startPage = ((page - 1) / pageGroupSize) * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        return new PagedResponse<>(boardMapper.selectAllByViews(startRow, endRow), page, totalPages, pageSize, totalBoard);
    }


}
