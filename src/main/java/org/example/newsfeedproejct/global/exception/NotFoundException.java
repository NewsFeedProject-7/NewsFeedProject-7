package org.example.newsfeedproejct.global.exception;

public class NotFoundException extends GlobalException {
    public NotFoundException(ErrorType errorType) {
        super(errorType);
    }

}
