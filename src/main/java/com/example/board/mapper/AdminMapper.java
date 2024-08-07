package com.example.board.mapper;

import com.example.board.domain.dto.BannerDTO;
import com.example.board.domain.dto.NoticeDTO;
import com.example.board.domain.dto.UsersDTO;
import com.example.board.domain.vo.BannerVO;
import com.example.board.domain.vo.NoticeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {


    //////////////  관리자 페이지  //////////////

    // 유저 관리
    List<UsersDTO> getUserListForAdmin(int startRow, int endRow);

    List<UsersDTO> getUserList();

    int deleteUser(String providerId);


    // 공지사항 관리
    List<NoticeDTO> getNoticeList();

    int deleteNotice(Long noticeId);

    NoticeDTO getNotice(Long noticeId);

    int editNotice(NoticeVO vo);

    int saveNotice(NoticeVO vo);


    // 배너 관리
    List<BannerDTO> getBanners();

    int deleteBanner(Long bannerId);

    int saveBanner(BannerVO vo);
}
