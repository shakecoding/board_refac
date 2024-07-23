package com.example.board.service;

import com.example.board.domain.dto.UsersDTO;
import com.example.board.domain.util.PagedResponse;
import com.example.board.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminMapper adminMapper;

    @Override
    public PagedResponse<UsersDTO> getUserList(int page, int pageSize) {

        int startRow = (page - 1) * pageSize;
        int endRow = startRow + pageSize;

        // 얘 수정
        int totalUsers = adminMapper.getUserCountForAdmin();
        int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

        int pageGroupSize = 5;
        int startPage = ((page - 1) / pageGroupSize) * pageGroupSize + 1;
        int endPage = Math.min(startPage + pageGroupSize - 1, totalPages);

        return new PagedResponse<>(adminMapper.getUserListForAdmin(startRow, endRow), page, totalPages, pageSize, totalUsers);

    }
}
