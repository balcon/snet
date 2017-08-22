package com.epam.study.snet.view;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Image {
    long id;
    public String getSourcePath() {
        if (id != 0) return "/main/image?imageId=" + id;
        else return "/images/anonymous.jpg";
    }
}
