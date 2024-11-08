package com.sparta.gourmate.domain.menu.entity;

public enum MenuStatusEnum {
    AVAILABLE(status.AVAILABLE),
    OUT_OF_STOCK(status.OUT_OF_STOCK),
    HIDDEN(status.HIDDEN);

    private final String MenuStatus;

    MenuStatusEnum(String menuStatus) {
        this.MenuStatus = menuStatus;
    }

    public String getMenuStatus() {
        return this.MenuStatus;
    }

    public static class status {
        public static final String AVAILABLE = "AVAILABLE";
        public static final String OUT_OF_STOCK = "OUT_OF_STOCK";
        public static final String HIDDEN = "HIDDEN";
    }

}
