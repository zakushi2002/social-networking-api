package com.social.networking.api.view.dto.aws;

import lombok.Data;

@Data
public class FileS3Dto {
    private byte[] fileByte;
    private String fileType;
}
