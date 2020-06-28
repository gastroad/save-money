package com.savemoney.steam.domain;

import com.savemoney.core.util.cryption.Aes256;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerSummariesDto {

    /**
     * 64bit SteamID of the user
     */
    private String steamId;

    /**
     * The player's persona name (display name)
     */
    private String personaName;

    /**
     * The full URL of the player's Steam Community profile.
     */
    private String profileUrl;

    /**
     * The full URL of the player's 32x32px avatar.
     * If the user hasn't configured an avatar, this will be the default ? avatar.
     */
    private String avatar;

    /**
     * The full URL of the player's 64x64px avatar.
     * If the user hasn't configured an avatar, this will be the default ? avatar.
     */
    private String avatarMedium;

    /**
     * The full URL of the player's 184x184px avatar.
     * If the user hasn't configured an avatar, this will be the default ? avatar.
     */
    private String avatarFull;

    /**
     * The user's current status.
     * 0 - Offline, 1 - Online, 2 - Busy, 3 - Away, 4 - Snooze, 5 - looking to trade, 6 - looking to play.
     * If the player's profile is private, this will always be "0",
     * except if the user has set their status to looking to trade or looking to play,
     * because a bug makes those status appear even if the profile is private.
     */
    private Long personaState;

    /**
     * This represents whether the profile is visible or not, and if it is visible, why you are allowed to see it.
     * Note that because this WebAPI does not use authentication,
     * there are only two possible values returned:
     * 1 - the profile is not visible to you (Private, Friends Only, etc),
     * 3 - the profile is "Public", and the data is visible.
     * Mike Blaszczak's <a href="http://forums.steampowered.com/forums/showpost.php?p=31955251&postcount=3">post on Steam forums</a> says,
     * "The community visibility state this API returns is different than the privacy state.
     * It's the effective visibility state from the account making the request
     * to the account being viewed given the requesting account's relationship to the viewed account."
     */
    private Long communityVisibilityState;

    /**
     * If set, indicates the user has a community profile configured (will be set to '1')
     */
    private Long profileState;

    /**
     * The last time the user was online, in unix time. Only available when you are friends with the requested user (since Feb, 4).
     */
    private Long lastLogOff;

    /**
     * If set, indicates the profile allows public comments.
     */
    private String commentPermission;

    /**
     * Encryption
     * The player's "Real Name", if they have set it.
     */
    private String realName;

    /**
     * Encryption
     * The player's primary group, as configured in their Steam Community profile.
     */
    private String primaryClanId;

    /**
     * Encryption
     * The time the player's account was created.
     */
    private Long timeCreated;

    /**
     * Encryption
     * If the user is currently in-game, this value will be returned and set to the gameid of that game.
     */
    private String gameId;

    /**
     * Encryption
     * The ip and port of the game server the user is currently playing on,
     * if they are playing on-line in a game using Steam matchmaking. Otherwise will be set to "0.0.0.0:0".
     */
    private String gameServerIp;

    /**
     * Encryption
     * If the user is currently in-game, this will be the name of the game they are playing.
     * This may be the name of a non-Steam game shortcut.
     */
    private String gameExtraInfo;

    /**
     * Encryption
     * This value will be removed in a future update (see loccityid)
     */
    private String cityId;

    /**
     * Encryption
     * If set on the user's Steam Community profile, The user's country of residence, 2-character ISO country code
     */
    private String locCountryCode;

    /**
     * Encryption
     * If set on the user's Steam Community profile, The user's state of residence
     */
    private String locStateCode;

    /**
     * Encryption
     * - An internal code indicating the user's city of residence. A future update will provide this data in a more useful way.
     * - steam_location gem/package makes player location data readable for output.
     */
    private Long locCityId;

    /**
     * 암호화
     * @param aes256    암호화 Component
     * @return          암호화된 PlayerSummariesDto
     */
    /*
    public PlayerSummariesDto toEncryptionEntity(Aes256 aes256) {
        return PlayerSummariesDto.builder()
                .steamId(steamId)
                .personaName(personaName)
                .profileUrl(profileUrl)
                .avatar(avatar)
                .avatarMedium(avatarMedium)
                .avatarFull(avatarFull)
                .personaState(personaState)
                .communityVisibilityState(communityVisibilityState)
                .profileState(profileState)
                .communityVisibilityState(communityVisibilityState)
                .profileState(profileState)
                .lastLogOff(lastLogOff)
                .commentPermission(commentPermission)
                .realName(aes256.encryption(realName))
                .primaryClanId(aes256.encryption(primaryClanId))
                .timeCreated(aes256.encryption(timeCreated))
                .gameId(aes256.encryption(gameId))
                .gameServerIp(aes256.encryption(gameServerIp))
                .gameExtraInfo(aes256.encryption(gameExtraInfo))
                .cityId(aes256.encryption(cityId))
                .locCountryCode(aes256.encryption(locCountryCode))
                .locStateCode(aes256.encryption(locStateCode))
                .locCityId(aes256.encryption(locCityId))
                .build();
    }
     */

    /**
     * 복호화
     * @param aes256    암호화 Component
     * @return          복호화 PlayerSummariesDto
     */
    /*
    public PlayerSummariesDto todescryptionEntity(Aes256 aes256) {
        return PlayerSummariesDto.builder()
                .steamId(steamId)
                .personaName(personaName)
                .profileUrl(profileUrl)
                .avatar(avatar)
                .avatarMedium(avatarMedium)
                .avatarFull(avatarFull)
                .personaState(personaState)
                .communityVisibilityState(communityVisibilityState)
                .profileState(profileState)
                .communityVisibilityState(communityVisibilityState)
                .profileState(profileState)
                .lastLogOff(lastLogOff)
                .commentPermission(commentPermission)
                .realName(aes256.decryption(realName))
                .primaryClanId(aes256.decryption(primaryClanId))
                .timeCreated(aes256.decryption(timeCreated))
                .gameId(aes256.decryption(gameId))
                .gameServerIp(aes256.decryption(gameServerIp))
                .gameExtraInfo(aes256.decryption(gameExtraInfo))
                .cityId(aes256.decryption(cityId))
                .locCountryCode(aes256.decryption(locCountryCode))
                .locStateCode(aes256.decryption(locStateCode))
                .locCityId(aes256.decryption(locCityId))
                .build();
    }
     */

}
