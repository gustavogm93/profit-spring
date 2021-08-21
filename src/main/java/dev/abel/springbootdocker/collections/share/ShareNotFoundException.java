package dev.abel.springbootdocker.collections.share;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ShareNotFoundException extends RuntimeException{

    public ShareNotFoundException(String msg) {
        super(msg);
    }
}