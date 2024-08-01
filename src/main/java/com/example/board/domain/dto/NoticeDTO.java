package com.example.board.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoticeDTO {

    private Long noticeId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime noticeDate;

}
