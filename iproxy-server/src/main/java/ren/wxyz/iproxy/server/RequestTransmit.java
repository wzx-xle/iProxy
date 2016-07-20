/**
 * Copyright (C) 2001-2016 wxyz <hyhjwzx@126.com>
 * <p>
 * This program can be distributed under the terms of the GNU GPL Version 2.
 * See the file LICENSE.
 */
package ren.wxyz.iproxy.server;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.Socket;

/**
 * 请求转发
 *
 * @auther wxyz
 * @since 0.0.1
 */
@Slf4j
public class RequestTransmit implements Runnable {

    /**
     * 请求的编号
     */
    private long no;

    /**
     * HTTP端点
     */
    private HttpEndpoint httpEndpoint;

    /**
     * Socket
     */
    private Socket socket;

    public RequestTransmit(Socket socket, long no, HttpEndpoint httpEndpoint) {
        this.socket = socket;
        this.no = no;
        this.httpEndpoint = httpEndpoint;
    }

    public RequestTransmit(Socket socket, HttpEndpoint httpEndpoint) {
        this(socket, 0, httpEndpoint);
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream(), "UTF-8"));
            while (true) {
                String line = br.readLine();
                httpEndpoint.send2Client(this.no, line + "\r\n");
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }

    /**
     * 获取到请求的输出流
     *
     * @return 输出流
     * @throws IOException
     */
    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    /**
     * 关闭连接
     */
    public void close() {
        // 关闭socket
        if (null != socket) {
            if (!socket.isClosed()) {
                try {
                    socket.close();
                }
                catch (IOException e) {
                }
                finally {
                    log.debug("请求被关闭，编号：{}", this.no);
                }
            }
        }

        // 发送关闭客户端
        httpEndpoint.removeRequest(this.no);
    }
}
