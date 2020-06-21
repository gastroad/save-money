package com.savemoney.core.domain;

import com.savemoney.core.util.cryption.Aes256;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends TimeEntity {

    /**
     * 회원 구별 정보(아이디)
     */
    @NotNull(message = "아이디를 입력해주세요")
    @Size(min = 4, max = 32, message = "아이디는 4 ~ 32자리로 입력해주세요")
    private String id;

    /**
     * 회원 암호
     */
    @NotNull(message = "비밀번호를 입력해주세요")
    @Size(min = 8, max = 64, message = "비밀번호는 8 ~ 64자리로 입력해주세요")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
             message = "비밀번호는 최소 8자리, 문자, 특수문자1개 이상 포함하여 입력해주세요")
    private String password;

    /**
     * 회원 이름
     */
    @NotNull(message = "이름을 입력해주세요")
    @Size(min = 1, max = 64, message = "이름은 1 ~ 64자리로 입력해주세요")
    private String name;

    /**
     * 회원 이메일
     */
    @NotNull(message = "이메일을 입력해주세요")
    @Size(min = 3, max = 256, message = "이메일은 3 ~ 256자리로 입력해주세요")
    @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$",
             message = "이메일 형식에 맞게 입력해주세요")
    private String email;

    /**
     * 암호화
     * @param aes256    AES-256 Cryption Component
     * @return          암호화 객체
     */
    public UserEntity toEncryptionEntity(Aes256 aes256) {
        return UserEntity.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(aes256.encryption(email))
                .build();
    }

    /**
     * 복호화
     * @param aes256    AES-256 Cryption Component
     * @return          복호화 객체
     */
    public UserEntity toDecryptionEntity(Aes256 aes256) {
        return UserEntity.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(aes256.decryption(email))
                .build();
    }

}
