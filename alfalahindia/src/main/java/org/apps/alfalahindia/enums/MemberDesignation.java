package org.apps.alfalahindia.enums;


public enum MemberDesignation {

    PRESIDENT("President"), VICE_PRESIDENT("Vice President"), SECRETARY("Secretary"), JOINT_SECRETARY("Joint Secretary"), TREASURER("Treasurer"), BOARD_MEMBER("Board Member"), MEMBER("Member");

    String value;

    MemberDesignation(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
