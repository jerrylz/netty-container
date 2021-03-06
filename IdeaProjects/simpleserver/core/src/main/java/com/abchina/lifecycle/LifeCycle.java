package com.abchina.lifecycle;

/**
 * @author jerrylz
 * @date 2021/3/4
 */
public interface LifeCycle {

    /**
     * 初始化
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * 启动
     * @throws Exception
     */
    void start() throws Exception;

    /**
     * 销毁
     * @throws Exception
     */
    void destroy() throws Exception;
}
