package org.example.newsfeedproejct.global.exception;

import org.example.newsfeedproejct.global.exception.errorcode.ErrorCode;

public class NotFoundException extends GlobalException {
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}
