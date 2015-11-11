package com.limewater.dto;

import com.limewater.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by mindonal@gmail.com on 11/11/15.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private String imageUrl;

    public ImageDto(Image image) {
        this.imageUrl = image.getImageUrl();
    }
}
