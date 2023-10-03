package com.social.networking.api.view.dto.post.bookmark;

import com.social.networking.api.view.dto.InfoAdminDto;
import com.social.networking.api.view.dto.post.PostDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BookmarkDto extends InfoAdminDto {
    @ApiModelProperty(notes = "postDto")
    private PostDto postDto;
}
