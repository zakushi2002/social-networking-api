package com.social.networking.api.dto.category.topic;

import com.social.networking.api.dto.category.CategoryDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostTopicDto {
    @ApiModelProperty(name = "id")
    private Long id;
    @ApiModelProperty(name = "postId")
    private Long postId;
    @ApiModelProperty(name = "topic")
    private CategoryDto topic;
}
