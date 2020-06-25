package com.savemoney.core.mapper;

import com.savemoney.core.domain.SteamUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SteamUserMapper {

    /**
     * Steam 계정 등록 갯수
     * @param vanityUrl 사용자 지정 URL
     * @return          갯수
     */
    public Long countByVanityUrl(@Param("vanityUrl") String vanityUrl);

    /**
     * Steam 계정 저장
     * @param steamUserEntity   Steam 사용자 정보
     * @return                  STEAM_USER_ID
     */
    public Long save(SteamUserEntity steamUserEntity);

}
