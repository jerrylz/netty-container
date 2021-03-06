package com.abchina.reactor;





import com.abchina.core.handler.Event;

import java.io.IOException;
import java.nio.channels.Selector;

public interface Reactor extends Runnable {
    String getName();

    Selector getSelector();

    void register(Event event);

    void stop() throws IOException;
}
