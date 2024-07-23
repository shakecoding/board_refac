package com.example.board.controller.admin;

import com.example.board.domain.dto.UsersDTO;
import com.example.board.domain.util.PagedResponse;
import com.example.board.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {

    private final AdminService adminService;

    @GetMapping("/user")
    public ResponseEntity<PagedResponse<UsersDTO>> getUsersList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                           @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return ResponseEntity.ok(adminService.getUserList(page, pageSize));

    }

}
