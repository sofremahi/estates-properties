package com.TIDDEV.mhn.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response<R> {
    private R response;

    public Response(R response) {
        this.response = response;
    }

    private Message message;
    public Response(Message message) {
        this.message = message;
    }
}
