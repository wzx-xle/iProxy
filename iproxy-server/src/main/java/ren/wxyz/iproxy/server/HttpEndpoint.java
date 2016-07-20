/**
 * Copyright (C) 2001-2016 wxyz <hyhjwzx@126.com>
 * <p>
 * This program can be distributed under the terms of the GNU GPL Version 2.
 * See the file LICENSE.
 */
package ren.wxyz.iproxy.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Http的端点
 *
 * @auther wxyz
 * @since 0.0.1
 */
@Slf4j
public class HttpEndpoint implements Runnable {

    /**
     * 最大的请求处理线程数
     */
    private static final int MAX_REQUEST_THREADS = 100;

    /**
     * 监听的主机
     */
    private String host;

    /**
     * 监听的端口
     */
    private int port;

    /**
     * 客户端的socket
     */
    private Socket client;

    /**
     * 请求集合
     */
    private Map<Long, RequestTransmit> requests = new HashMap<>();

    /**
     * 初始化Http端点
     *
     * @param host 主机名或IP
     * @param port 监听的端口
     */
    public HttpEndpoint(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 初始化Http端点
     * <br>
     * 默认IP为 0.0.0.0
     *
     * @param port 监听的端口
     */
    public HttpEndpoint(int port) {
        this("0.0.0.0", port);
    }

    @Override
    public void run() {
        // 开启一个监听服务，对外提供访问
        try (ServerSocket listen = new ServerSocket()) {
            listen.bind(new InetSocketAddress(this.host, this.port));
            log.info("启动一个端点[{}:{}]", this.host, this.port);

            // 请求转发的线程池
            ExecutorService pool = Executors.newFixedThreadPool(MAX_REQUEST_THREADS);

            long requestNo = 0;
            while (true) {
                Socket req = listen.accept();
                RequestTransmit rt = new RequestTransmit(req, ++requestNo, this);
                requests.put(requestNo, rt);
                pool.submit(rt);
                log.debug("发起一个请求转发，请求者IP：{}，请求者端口：{}，请求编号：{}",
                        req.getLocalAddress(), req.getLocalPort(), requestNo);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
            log.info("结束一个端点[{}:{}]", this.host, this.port);
        }
    }

    /**
     * 设置客户端的socket
     *
     * @param socket
     */
    public void setClientSocket(Socket socket) {
        this.client = socket;
    }

    /**
     * 发送消息到客户端
     *
     * @param no 编号
     * @param line 数据行
     */
    public void send2Client(long no, String line) {

    }

    /**
     * 移除请求
     *
     * @param no 编号
     */
    public void removeRequest(long no) {
        if (requests.containsKey(no)) {
            requests.remove(no);
        }
    }
}
