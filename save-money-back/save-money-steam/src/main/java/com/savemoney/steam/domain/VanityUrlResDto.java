package com.savemoney.steam.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VanityUrlResDto {

    /**
     * 성공 여부
     */
    private Boolean success;

    /**
     * 메세지
     */
    private String message;

    /**
     * 사용자 지정 URL(64 bit)
     */
    private String steamId;

}
