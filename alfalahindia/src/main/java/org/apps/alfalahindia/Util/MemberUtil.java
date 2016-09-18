package org.apps.alfalahindia.Util;

import org.apps.alfalahindia.enums.UserRole;

public class MemberUtil {

    private static UserRole getUserRole() {
        return UserRole.valueOf(Prefs.getString(PrefKeys.MEMBER_ROLE));
    }

    public static boolean isSuperAdmin() {
        return getUserRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean isAdmin() {
        return getUserRole() == UserRole.ADMIN || getUserRole() == UserRole.SUPER_ADMIN;
    }

    public static boolean isMember() {
        return getUserRole() == UserRole.MEMBER;
    }

    public static boolean isGuest() {
        return getUserRole() == UserRole.GUEST;
    }
}
