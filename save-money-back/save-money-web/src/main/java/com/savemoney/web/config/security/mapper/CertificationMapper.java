package com.savemoney.web.config.security.mapper;

import com.savemoney.web.config.security.domain.CertificationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CertificationMapper {

    /**
     * 회원 정보 조회
     * @param id    회원 구별 정보(아이디)
     * @return      회원 정보 객체
     */
    public CertificationEntity findById(@Param("id") String id);

}
