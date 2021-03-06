package com.abchina.scnner.listener;


import com.abchina.core.scnner.Event;

public interface Listener {
    void onEvent(Event event) throws Exception;
}
