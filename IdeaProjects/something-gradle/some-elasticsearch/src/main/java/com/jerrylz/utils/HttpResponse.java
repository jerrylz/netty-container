package com.jerrylz.utils;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.Header;

/**
 * @author jerrylz
 * @date 2020/8/23
 */
public class HttpResponse {
    private int statusCode;
    private String reasonPhrase;
    private Header[] headers;
    private String body;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
