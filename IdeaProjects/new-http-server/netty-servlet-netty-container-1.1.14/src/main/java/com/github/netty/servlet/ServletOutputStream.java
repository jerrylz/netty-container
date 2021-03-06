package com.github.netty.servlet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultLastHttpContent;
import io.netty.handler.codec.http.HttpHeaderUtil;
import io.netty.handler.codec.http.HttpResponse;

import javax.servlet.WriteListener;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author 84215
 */
public class ServletOutputStream extends javax.servlet.ServletOutputStream {

    private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;

    private final ChannelHandlerContext ctx;
    private final ServletHttpServletResponse servletResponse;
    private WriteListener writeListener; //监听器，暂时没处理

    private byte[] buf; //缓冲区
    private int count; //缓冲区游标（记录写到哪里）
    private int totalLength;//内容总长度
    private boolean closed; //是否已经调用close()方法关闭输出流

    private final Object buffLock = new Object();

    ServletOutputStream(ChannelHandlerContext ctx, ServletHttpServletResponse servletResponse) {
        this.ctx = ctx;
        this.servletResponse = servletResponse;
        this.buf = new byte[DEFAULT_BUFFER_SIZE];
    }

    @Override
    public boolean isReady() {
        return true; // TODO implement
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        checkNotNull(writeListener);
        if(this.writeListener != null){
            return; //只能设置一次
        }
        this.writeListener = writeListener;
        // TODO ISE when called more than once
        // TODO ISE when associated request is not async
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        synchronized (buffLock) {
            totalLength += len;
            if (len > count) {
                flushBuffer();
                ByteBuf content = ctx.alloc().buffer(len);
                content.writeBytes(b, off, len);
                writeContent(content, false);
                return;
            }
            writeBufferIfNeeded(len);
            System.arraycopy(b, off, buf, count, len); //输入的b复制到缓存buf
            count += len;
        }
    }

    @Override
    public void write(int b) throws IOException {
        synchronized (buffLock) {
            writeBufferIfNeeded(1);
            buf[count++] = (byte) b;
            totalLength++;
        }
    }

    private void writeBufferIfNeeded(int len) throws IOException {
        if (len > buf.length - count) { //buffer剩余空间不足则flush
            flushBuffer();
        }
    }

    @Override
    public void flush() throws IOException {
        flushBuffer();
    }

    private void flushBuffer() {
        flushBuffer(false);
    }

    private void flushBuffer(boolean lastContent) {
        synchronized (buffLock) {
            if (count > 0) {
                ByteBuf content = ctx.alloc().buffer(count);
                content.writeBytes(buf, 0, count);//buf写入ByteBuf
                count = 0;//游标归位
                writeContent(content, lastContent);
            } else if (lastContent) { //如果是最后一次flush，即便内容为空也要执行ctx.write写入EMPTY_LAST_CONTENT
                writeContent(Unpooled.EMPTY_BUFFER, true);
            }
        }
    }

    private void writeContent(ByteBuf content, boolean lastContent) {
        // TODO block if channel is not writable to avoid heap utilisation
        if (!servletResponse.isCommitted()) {
            writeResponse(lastContent);
        }
        if (content.readableBytes() > 0) {
            assert content.refCnt() == 1;
            ctx.write(content, ctx.voidPromise());
        }
        if (lastContent) {
            HttpResponse nettyResponse = servletResponse.getNettyResponse();
            ChannelFuture future = ctx.write(DefaultLastHttpContent.EMPTY_LAST_CONTENT);
            if (!HttpHeaderUtil.isKeepAlive(nettyResponse)) {
                future.addListener(ChannelFutureListener.CLOSE);//如果不是keep-alive，写完后关闭channel
            }
        }
    }

    private void writeResponse(boolean lastContent) {
        HttpResponse response = servletResponse.getNettyResponse();
        // TODO implement exceptions required by http://tools.ietf.org/html/rfc2616#section-4.4
        // 设置content-length头
        if (!HttpHeaderUtil.isContentLengthSet(response)) {
            HttpHeaderUtil.setContentLength(response, totalLength);
        }
        ctx.write(response, ctx.voidPromise());
    }

    @Override
    public void close() throws IOException {
        if (closed) {
            return;
        }
        try {
            flushBuffer(true);
            ctx.flush();
        } finally {
            buf = null;
        }
        closed = true;
    }

    void resetBuffer() {
        assert !servletResponse.isCommitted();
        count = 0;
    }

    int getBufferSize() {
        return buf.length;
    }

    void setBufferSize(int size) {
        assert !servletResponse.isCommitted();
        checkState(count == 0, "Response body content has been written");
        buf = new byte[size];
    }
}
