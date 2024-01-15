package io.foodtechlab.core.entities;

import lombok.Value;

@Value
public class SocialAccount {
    SocialType type;
    String id;

    public enum SocialType {
        VK, INSTAGRAM, FB, OK, TIKTOK, TELEGRAM
    }
}