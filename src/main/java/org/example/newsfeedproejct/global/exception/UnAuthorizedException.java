package org.example.newsfeedproejct.global.exception;

public class UnAuthorizedException extends GlobalException {

    public UnAuthorizedException(ErrorType errorType) {
        super(errorType);
    }

}
