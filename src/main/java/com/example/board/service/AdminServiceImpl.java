package com.example.board.service;

import com.example.board.domain.dto.BannerDTO;
import com.example.board.domain.dto.NoticeDTO;
import com.example.board.domain.dto.UsersDTO;
import com.example.board.domain.vo.BannerVO;
import com.example.board.domain.vo.NoticeVO;
import com.example.board.mapper.AdminMapper;
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
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    // 유저 관리
    @Override
    public List<UsersDTO> getUserListAdmin() {
        return adminMapper.getUserList();
    }

    @Override
    public int deleteUser(String providerId) {
        return adminMapper.deleteUser(providerId);
    }


    // 공지 사항 관리
    @Override
    public List<NoticeDTO> getNoticeList() {
        return adminMapper.getNoticeList();
    }

    @Override
    public int deleteNotice(Long noticeId) {
        return adminMapper.deleteNotice(noticeId);
    }

    @Override
    public NoticeDTO getNoticeById(Long noticeId) {
        return adminMapper.getNotice(noticeId);
    }

    @Override
    public int editNotice(NoticeDTO noticeDTO) {
        return adminMapper.editNotice(NoticeVO.toEntity(noticeDTO));
    }

    @Override
    public int saveNotice(NoticeDTO noticeDTO) {
        return adminMapper.saveNotice(NoticeVO.toEntity(noticeDTO));
    }

    @Override
    public List<BannerDTO> getBannerList() {
        return adminMapper.getBanners();
    }

    @Override
    public int deleteBanner(Long bannerId) {
        return adminMapper.deleteBanner(bannerId);
    }

    @Override
    public String saveBanner(String bannerTitle, MultipartFile file) {
        if (file.isEmpty()) {
            return "파일이 비어있습니다.";
        }

        try {
            LocalDate now = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            String datePath = now.format(formatter);

            String originalFileName = file.getOriginalFilename();
            String storedFileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFileName;

            Path directoryPath = Paths.get("src/main/resources/static/uploads/" + datePath + "/banners/");
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            Path filePath = directoryPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), filePath);

            String fileUrl = "/uploads/" + datePath + "/banners/" + storedFileName;

            BannerDTO bannerDTO = new BannerDTO();
            bannerDTO.setBannerTitle(bannerTitle);
            bannerDTO.setBannerImg(fileUrl);
            adminMapper.saveBanner(BannerVO.toEntity(bannerDTO));

        } catch (IOException e) {
            return "에러 발생";
        }
        return "정상 동작";
    }


}
