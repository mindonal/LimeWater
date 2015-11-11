package com.limewater.dto;

import com.limewater.entity.Image;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by mindonal@gmail.com on 11/11/15.
 */
@Getter
@Setter
public class ImageDto {

    private String imageUrl;

    public ImageDto(Image image) {
        this.imageUrl = image.getImageUrl();
    }
}
