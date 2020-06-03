package com.savemoney.core.mapper;

import com.savemoney.core.domain.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper<T> {

    T findById(String id);

}
