package com.example.board.mapper;

import com.example.board.domain.dto.UsersDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {


    //////////////  관리자 페이지  //////////////
    int getUserCountForAdmin();

    List<UsersDTO> getUserListForAdmin(int startRow, int endRow);

}
