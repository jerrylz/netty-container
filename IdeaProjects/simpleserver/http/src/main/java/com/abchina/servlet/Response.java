package com.abchina.servlet;

import java.io.OutputStream;

public interface Response {

    OutputStream getResponseOutputStream();
}
