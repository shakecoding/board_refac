package com.example.board.service;

import com.example.board.domain.dto.UsersDTO;
import com.example.board.domain.util.PagedResponse;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {

    PagedResponse<UsersDTO> getUserList(int page, int pageSize);

}
