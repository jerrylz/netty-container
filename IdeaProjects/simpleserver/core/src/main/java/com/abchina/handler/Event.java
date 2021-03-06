package com.abchina.handler;

import java.io.IOException;

public interface Event {
    void event() throws IOException;
    BaseHandler getHandler();
}
