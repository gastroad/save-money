package com.savemoney.core.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeEntity {

    private LocalDateTime createAt;
    private LocalDateTime updateAt;

}
