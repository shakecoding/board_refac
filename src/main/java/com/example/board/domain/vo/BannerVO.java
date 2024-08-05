package com.example.board.domain.vo;

import com.example.board.domain.dto.BannerDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class BannerVO {

    private Long bannerId;
    private String bannerTitle;
    private String bannerImg;
    private String bannerDate;

    @Builder
    public BannerVO(Long bannerId, String bannerTitle, String bannerImg, String bannerDate) {
        this.bannerId = bannerId;
        this.bannerTitle = bannerTitle;
        this.bannerImg = bannerImg;
        this.bannerDate = bannerDate;
    }

    public static BannerVO toEntity(BannerDTO bannerDTO) {
        return BannerVO.builder().bannerId(bannerDTO.getBannerId())
                        .bannerTitle(bannerDTO.getBannerTitle())
                        .bannerImg(bannerDTO.getBannerImg())
                        .bannerDate(bannerDTO.getBannerDate())
                        .build();
    }

}
