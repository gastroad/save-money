package com.savemoney.steam.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnedGameDto {

    /**
     * Unique identifier for the game
     */
    private String appId;

    /**
     * The name of the game
     */
    private String name;

    /**
     * The total number of minutes played in the last 2 weeks
     */
    private String playTime2Weeks;

    /**
     * The total number of minutes played "on record", since Steam began tracking total playtime in early 2009.
     */
    private String playTimeForever;

    /**
     * ese are the filenames of various images for the game.
     * To construct the URL to the image, use this format:
     * http://media.steampowered.com/steamcommunity/public/images/apps/{appid}/{hash}.jpg.
     * For example, the TF2 logo is returned as "07385eb55b5ba974aebbe74d3c99626bda7920b8", which maps to the URL:
     * <a href="http://media.steampowered.com/steamcommunity/public/images/apps/440/07385eb55b5ba974aebbe74d3c99626bda7920b8.jpg">[1]</a>
     */
    private String imgIconUrl;

    /**
     * ese are the filenames of various images for the game.
     * To construct the URL to the image, use this format:
     * http://media.steampowered.com/steamcommunity/public/images/apps/{appid}/{hash}.jpg.
     * For example, the TF2 logo is returned as "07385eb55b5ba974aebbe74d3c99626bda7920b8", which maps to the URL:
     * <a href="http://media.steampowered.com/steamcommunity/public/images/apps/440/07385eb55b5ba974aebbe74d3c99626bda7920b8.jpg">[1]</a>
     */
    private String imgLogoUrl;

    /**
     * indicates there is a stats page with achievements or other game stats available for this game.
     * The uniform URL for accessing this data is http://steamcommunity.com/profiles/{steamid}/stats/{appid}.
     * For example, Robin's TF2 stats can be found at: http://steamcommunity.com/profiles/76561197960435530/stats/440.
     * You may notice that clicking this link will actually redirect to a vanity URL like /id/robinwalker/stats/TF2
     */
    private String hasCommunityVisibleStats;

}
