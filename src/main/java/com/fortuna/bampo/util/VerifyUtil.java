package com.fortuna.bampo.util;

/**
 * 审核工具类
 *
 * @author Eva7
 * @since 0.4.0
 */
public class VerifyUtil {
    public static int getRequiredNumber(int activityNumber) {
        return (int) (100 / (activityNumber + 4) + Math.log(activityNumber + 3) * 7 - 18);
    }
}
