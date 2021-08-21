package dev.abel.springbootdocker.collections.region;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RegionNotFoundException extends RuntimeException{

    public RegionNotFoundException(String msg) {
        super(msg);
    }
}