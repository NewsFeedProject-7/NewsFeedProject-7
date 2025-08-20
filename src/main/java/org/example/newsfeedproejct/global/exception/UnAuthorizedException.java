package org.example.newsfeedproejct.global.exception;

import org.example.newsfeedproejct.global.exception.errorcode.ErrorCode;

public class UnAuthorizedException extends GlobalException {

    public UnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }

}
