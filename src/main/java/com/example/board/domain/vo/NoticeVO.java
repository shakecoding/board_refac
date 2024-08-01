package com.example.board.domain.vo;

import com.example.board.domain.dto.NoticeDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class NoticeVO {

    private Long noticeId;
    private String noticeTitle;
    private String noticeContent;
    private LocalDateTime noticeDate;

    @Builder
    public NoticeVO(Long noticeId, String noticeTitle, String noticeContent, LocalDateTime noticeDate) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeContent = noticeContent;
        this.noticeDate = noticeDate;
    }

    public static NoticeVO toEntity(NoticeDTO noticeDTO) {
        return NoticeVO.builder().noticeId(noticeDTO.getNoticeId())
                .noticeTitle(noticeDTO.getNoticeTitle())
                .noticeContent(noticeDTO.getNoticeContent())
                .noticeDate(noticeDTO.getNoticeDate())
                .build();
    }
}
