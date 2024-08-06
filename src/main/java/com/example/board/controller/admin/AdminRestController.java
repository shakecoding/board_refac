package com.example.board.controller.admin;

import com.example.board.domain.dto.BannerDTO;
import com.example.board.domain.dto.NoticeDTO;
import com.example.board.domain.dto.UsersDTO;
import com.example.board.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/notices/{noticeId}")
    public ResponseEntity<NoticeDTO> getNotice(@PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok(adminService.getNoticeById(noticeId));
    }

    @PostMapping("/notices")
    public ResponseEntity<String> saveNotice(@RequestBody NoticeDTO noticeDTO) {
        int result = adminService.saveNotice(noticeDTO);

        if (result == 1){
            return ResponseEntity.ok("추가완료");
        }

        return ResponseEntity.ok("추가 중 오류");
    }

    @PutMapping("/notices")
    public ResponseEntity<String> editNotice(@RequestBody NoticeDTO noticeDTO) {
        int result = adminService.editNotice(noticeDTO);

        if (result == 1){
            return ResponseEntity.ok("수정완료");
        }

        return ResponseEntity.ok("수정 중 오류");
    }

    @DeleteMapping("/notices/{noticeId}")
    public ResponseEntity<String> deleteNotice(@PathVariable("noticeId") Long noticeId) {
        int result = adminService.deleteNotice(noticeId);

        if (result == 1){
            return ResponseEntity.ok("삭제완료");
        }

        return ResponseEntity.ok("삭제 중 오류");
    }


    // 배너 관리
    @GetMapping("/banners")
    public ResponseEntity<List<BannerDTO>> getBannerListAdmin(){
        return ResponseEntity.ok(adminService.getBannerList());
    }

    @DeleteMapping("/banners/{bannerId}")
    public ResponseEntity<String> deleteBanner(@PathVariable("bannerId") Long bannerId) {
        int result = adminService.deleteBanner(bannerId);

        if (result == 1){
            return ResponseEntity.ok("삭제완료");
        }

        return ResponseEntity.ok("삭제 중 오류");
    }

    @PostMapping("/banners")
    public ResponseEntity<String> saveBanner(@RequestParam("bannerTitle") String bannerTitle,
                                             @RequestParam("bannerImage") MultipartFile bannerImage) {
        String result = adminService.saveBanner(bannerTitle, bannerImage);
        return ResponseEntity.ok(result);
    }

}
