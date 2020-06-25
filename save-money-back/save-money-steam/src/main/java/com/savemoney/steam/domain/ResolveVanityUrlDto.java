package com.savemoney.steam.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResolveVanityUrlDto {

    /**
     * 성공여부
     */
    private Long success;

    /**
     * 사용자 지정 URL(64 bit)
     */
    private String steamid;

    /**
     * 메세지
     */
    private String message;

}
