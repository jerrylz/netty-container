package com.abchina.scnner.listener;


import com.abchina.core.loader.ContextLoader;
import com.abchina.core.scnner.Event;
import com.abchina.core.scnner.FileModifyEvent;

public class FileModifyListener implements Listener {
    private ContextLoader contextLoader;

    public FileModifyListener(ContextLoader contextLoader) {
        this.contextLoader = contextLoader;
    }

    @Override
    public void onEvent(Event event) throws Exception {
        if (event instanceof FileModifyEvent) {
            contextLoader.reload();
        }
    }
}
