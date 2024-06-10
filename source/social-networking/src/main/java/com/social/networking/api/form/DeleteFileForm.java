package com.social.networking.api.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class DeleteFileForm {
    @NotEmpty(message = "fileName cannot be empty!")
    private String fileName;
}
