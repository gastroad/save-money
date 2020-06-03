package com.savemoney.core.mapper;

import com.savemoney.core.domain.UserAuthority;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAuthorityMapper {

    List<UserAuthority> findById(String id);

}
