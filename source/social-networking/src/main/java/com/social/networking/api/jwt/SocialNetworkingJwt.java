package com.social.networking.api.jwt;

import com.social.networking.api.utils.ZipUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

@Slf4j
@Data
public class SocialNetworkingJwt implements Serializable {
    public static final String DELIM = "\\|";
    public static final String EMPTY_STRING = "<>";
    private Long tokenId;
    private Long accountId = -1L;
    private String kind = EMPTY_STRING;//token kind
    private String permission = EMPTY_STRING;
    private Integer userKind = -1; //loại user là admin hay là gì
    private String email = EMPTY_STRING;// username hoac order code
    private Long orderId = -1L;
    private Boolean isSuperAdmin = false;

    public String toClaim() {
        if (userKind == null) {
            userKind = -1;
        }
        if (email == null) {
            email = EMPTY_STRING;
        }
        if (orderId == null) {
            orderId = -1L;
        }
        return ZipUtils.zipString(accountId + DELIM + kind + DELIM + permission + DELIM + userKind + DELIM + email + DELIM + orderId + DELIM + isSuperAdmin);
    }

    public static SocialNetworkingJwt decode(String input) {
        SocialNetworkingJwt result = null;
        try {
            String[] items = ZipUtils.unzipString(input).split(DELIM, 7);
            if (items.length >= 6) {
                result = new SocialNetworkingJwt();
                result.setAccountId(parserLong(items[0]));
                result.setKind(checkString(items[1]));
                result.setPermission(checkString(items[2]));
                result.setUserKind(parserInt(items[3]));
                result.setEmail(checkString(items[4]));
                result.setOrderId(parserLong(items[5]));
                if (items.length > 6) {
                    result.setIsSuperAdmin(checkBoolean(items[6]));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    private static Long parserLong(String input) {
        try {
            Long out = Long.parseLong(input);
            if (out > 0) {
                return out;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private static Integer parserInt(String input) {
        try {
            Integer out = Integer.parseInt(input);
            if (out > 0) {
                return out;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private static String checkString(String input) {
        if (!input.equals(EMPTY_STRING)) {
            return input;
        }
        return null;
    }

    private static Boolean checkBoolean(String input) {
        try {
            return Boolean.parseBoolean(input);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }
}
