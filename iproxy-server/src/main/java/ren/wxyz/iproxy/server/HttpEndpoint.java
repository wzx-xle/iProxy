/**
 * Copyright (C) 2001-2016 wxyz <hyhjwzx@126.com>
 * <p>
 * This program can be distributed under the terms of the GNU GPL Version 2.
 * See the file LICENSE.
 */
package ren.wxyz.iproxy.server;

import lombok.Getter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Http的端点
 *
 * @auther wxyz
 * @since 0.0.1
 */
@Getter
public class HttpEndpoint implements Runnable {

    /**
     * 监听的主机
     */
    private String host;

    /**
     * 监听的端口
     */
    private int port;

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
        try (ServerSocket listen = new ServerSocket(this.getPort())) {
            while (true) {
                Socket req = listen.accept();

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息到客户端
     *
     * @param no 编号
     * @param line 数据行
     */
    public void send2Client(int no, String line) {

    }

    /**
     * 关闭客户端
     *
     * @param no 编号
     */
    public void closeClient(int no) {

    }

    public void send2Request(int no, String line) {

    }
}
