package com.example.board.controller.admin;

import com.example.board.domain.dto.NoticeDTO;
import com.example.board.domain.dto.UsersDTO;
import com.example.board.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;

    // 유저 관리
    @GetMapping("/users")
    public ResponseEntity<List<UsersDTO>> getUserListAdmin(){
        return ResponseEntity.ok(adminService.getUserListAdmin());
    }

    @DeleteMapping("/users/{providerId}")
    public ResponseEntity<String> deleteUser(@PathVariable("providerId") String providerId) {

        int result = adminService.deleteUser(providerId);
        
        if (result == 1){
            return ResponseEntity.ok("삭제완료");
        }

        return ResponseEntity.ok("삭제 중 오류");
    }

    // 공지 사항 관리
    @GetMapping("/notices")
    public ResponseEntity<List<NoticeDTO>> getNoticeListAdmin(){
        return ResponseEntity.ok(adminService.getNoticeList());
    }

    @DeleteMapping("/notices/{noticeId}")
    public ResponseEntity<String> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        int result = adminService.deleteNotice(noticeId);

        if (result == 1){
            return ResponseEntity.ok("삭제완료");
        }

        return ResponseEntity.ok("삭제 중 오류");
    }

    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok(adminService.getNoticeById(noticeId));
    }

    @PutMapping("/notices")
    public ResponseEntity<String> editNotice(@RequestBody NoticeDTO noticeDTO) {
        int result = adminService.editNotice(noticeDTO);

        if (result == 1){
            return ResponseEntity.ok("삭제완료");
        }

        return ResponseEntity.ok("삭제 중 오류");
    }


}
