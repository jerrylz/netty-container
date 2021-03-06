package com.abchina.core;

import com.abchina.utils.HostUtil;
import com.abchina.utils.LoggerFactoryX;
import com.abchina.utils.LoggerX;
import com.abchina.utils.NamespaceUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.epoll.Epoll;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.PlatformDependent;

import java.net.InetSocketAddress;

/**
 *
 * @author jerrylz
 * @date 2021/3/4
 */
public abstract class AbstractNettyServer implements Runnable{
    protected LoggerX logger = LoggerFactoryX.getLogger(getClass());
    private InetSocketAddress serverAddress;
    private final boolean enableEpoll;
    private String name;

    public AbstractNettyServer(InetSocketAddress address) {
        this("", address);
    }

    public AbstractNettyServer(String preName,InetSocketAddress address) {
        super();
        this.enableEpoll = Epoll.isAvailable();
        this.serverAddress = address;
        this.name = NamespaceUtil.newIdName(preName,getClass());
    }


    @Override
    public void run() {
        //
    }


    protected void stopAfter(Future future){
        //有异常抛出
        Throwable cause = future.cause();
        if(cause != null){
            logger.error("stopAfter error={}",cause.toString(),cause);
        }
        logger.info("{} stop [port = {} , cause = {}]...",getName(),getPort(),cause);
    }

    protected void startAfter(ChannelFuture future){
        //有异常抛出
        Throwable cause = future.cause();
        if(cause != null){
            PlatformDependent.throwException(cause);
        }
        logger.info("{} start (port = {}, pid = {}, os = {}) ...",
                getName(),
                getPort()+"",
                HostUtil.getPid()+"",
                HostUtil.getOsName());
    }

    @Override
    public String toString() {
        return name+"{" +
                "port=" + getPort() +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getPort() {
        if(serverAddress == null){
            return 0;
        }
        return serverAddress.getPort();
    }
}
