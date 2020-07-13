package com.savemoney.steam.domain;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class VanityUrlReqDto {

    /**
     * 사용자 지정 URL
     */
    @NotBlank(message = "vanityURL을 입력해주세요")
    private String vanityUrl;

}
