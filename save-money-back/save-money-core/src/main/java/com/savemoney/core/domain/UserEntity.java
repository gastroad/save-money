package com.savemoney.core.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntity extends TimeEntity {

    private String id;
    private String password;
    private String name;

}
