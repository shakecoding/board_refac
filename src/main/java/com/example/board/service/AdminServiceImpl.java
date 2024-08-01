package com.example.board.service;

import com.example.board.domain.dto.NoticeDTO;
import com.example.board.domain.dto.UsersDTO;
import com.example.board.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return adminMapper.editNotice(noticeDTO);
    }
}
