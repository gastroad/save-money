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
     * SteamId(64 bit) 조회
     * @param id    사용자 구별 정보(아이디)
     * @return      steamid(64 bit)
     */
    public String findById(@Param("id") String id);

    /**
     * Steam 계정 저장
     * @param steamUserEntity   Steam 사용자 정보
     * @return                  STEAM_USER_ID
     */
    public Long save(SteamUserEntity steamUserEntity);

}
