package com.limewater.entity;

/**
 * Available categories for blog posts.
 */
public enum Seller {

    TOYSRUS_KR("TOYSRUS-KR", "toysrus.lottemart.com"),
    OFFICIAL_KR("OFFICIAL-KR", "shop.lego.com/ko-KR"),
    OFFICIAL_US("OFFICIAL-US", "shop.lego.com/en-US"),
    AMAZON_US("AMAZON_US", "www.amazon.com");

    private String displayName;
    private String url;

    Seller(String displayName, String urlSlug) {
        this.displayName = displayName;
        this.url = urlSlug;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getUrlSlug() {
        return url;
    }

    public String getId() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}