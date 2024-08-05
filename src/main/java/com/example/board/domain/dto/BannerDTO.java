package com.example.board.domain.dto;

import lombok.Data;

@Data
public class BannerDTO {

    private Long bannerId;
    private String bannerTitle;
    private String bannerImg;
    private String bannerDate;

}
