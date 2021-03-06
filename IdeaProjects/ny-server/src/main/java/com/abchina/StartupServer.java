package com.abchina;

import com.abchina.core.AbstractNettyServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author jerrylz
 * @date 2021/3/4
 */
public class StartupServer extends AbstractNettyServer {

    public StartupServer(int port){
        this(getServerSocketAddress(null,port));
    }

    public StartupServer(InetSocketAddress serverAddress){
        super(serverAddress);
    }

    //获取地址
    public static InetSocketAddress getServerSocketAddress(InetAddress address, int port) {
        if(address == null) {
            try {
                address = InetAddress.getByAddress(new byte[]{0,0,0,0});
                if(!address.isAnyLocalAddress()){
                    address = InetAddress.getByName("::1");
                }
                if(!address.isAnyLocalAddress()){
                    address = new InetSocketAddress(port).getAddress();
                }
            } catch (UnknownHostException e) {
                address = new InetSocketAddress(port).getAddress();
            }
        }
        return new InetSocketAddress(address,port);
    }
}
