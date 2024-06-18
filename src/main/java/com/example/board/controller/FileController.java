package com.example.board.controller;

import com.example.board.domain.dto.FileDTO;
import com.example.board.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController // 이 클래스를 RESTful 컨트롤러로 표시
@RequestMapping("/download") // "/download"로 시작하는 요청을 이 컨트롤러에 매핑
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}") // "/download/{fileId}"로 들어오는 GET 요청을 이 메서드에 매핑
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        // 주어진 fileId로 파일 정보 조회
        FileDTO fileDTO = fileService.getFileById(fileId);

        // 실제 파일 경로 생성
        // storedFileName에 파일의 전체 경로가 포함되어 있다고 가정
        Path filePath = Paths.get(fileDTO.getStoredFileName());
        // 파일을 리소스로 로드
        Resource resource = new FileSystemResource(filePath);

        // 리소스가 존재하는지 확인하고, 존재하지 않으면 404 Not Found 응답 반환
        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 원본 파일 이름을 Content-Disposition 헤더에서 사용할 수 있도록 인코딩
        String encodedFileName = URLEncoder.encode(fileDTO.getOriginalFileName(), StandardCharsets.UTF_8).replace("+", "%20");

        // Content-Disposition 헤더 설정하여 원본 파일 이름으로 다운로드를 유도
        // attachment; <- 파일을 다운로드 하도록 한다.
        String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

        // 적절한 헤더와 콘텐츠 타입으로 파일 반환
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // 콘텐츠 타입을 바이너리 스트림으로 설정
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition) // Content-Disposition 헤더 추가
                .body(resource); // 응답 본문에 파일 리소스 설정
    }

}
