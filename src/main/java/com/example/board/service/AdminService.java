package com.example.board.service;

import com.example.board.domain.dto.BannerDTO;
import com.example.board.domain.dto.NoticeDTO;
import com.example.board.domain.dto.UsersDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface AdminService {

    // 유저 관리
    List<UsersDTO> getUserListAdmin();

    int deleteUser(String providerId);


    // 공지 사항 관리
    List<NoticeDTO> getNoticeList();

    int deleteNotice(Long noticeId);

    NoticeDTO getNoticeById(Long noticeId);

    int editNotice(NoticeDTO noticeDTO);

    int saveNotice(NoticeDTO noticeDTO);


    // 배너 관리
    List<BannerDTO> getBannerList();

    int deleteBanner(Long bannerId);

    String saveBanner(String bannerTitle, MultipartFile file);


}
