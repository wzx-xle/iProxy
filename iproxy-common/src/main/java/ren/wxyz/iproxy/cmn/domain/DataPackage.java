/**
 * Copyright (C) 2001-2016 wxyz <hyhjwzx@126.com>
 * <p>
 * This program can be distributed under the terms of the GNU GPL Version 2.
 * See the file LICENSE.
 */
package ren.wxyz.iproxy.cmn.domain;

import lombok.Getter;
import lombok.Setter;
import ren.wxyz.iproxy.cmn.helper.BytesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 数据包，用于客户端和服务端之间传输数据
 *
 * @auther wxyz
 * @since 0.0.1
 */
@Setter
@Getter
public class DataPackage {
    /**
     * 数据包头长度
     */
    private static final int DATA_PACKAGE_HEAD_LENGTH = 5;

    /**
     * 创建一个新的请求
     */
    public static final byte CMD_CREATE_NEW_REQUEST = 0x01;

    /**
     * 数据
     */
    public static final byte CMD_DATA = 0x02;

    /**
     * 指令
     */
    private byte cmd;

    /**
     * 包体长度
     */
    private int bodySize;

    /**
     * 包体
     */
    private byte[] body;

    /**
     * 从输入流读取数据填充到该实例中
     *
     * @param is 输入流
     * @return 当前实例
     */
    public DataPackage full(InputStream is) throws IOException {
        byte[] head = new byte[1];

        // cmd
        is.read(head);
        this.cmd = head[0];
        // bodySize
        head = new byte[DATA_PACKAGE_HEAD_LENGTH - 1];
        this.bodySize = BytesHelper.toInt(head);

        // body
        byte[] body = new byte[bodySize];
        int count = is.read(body);
        if (count == bodySize) {
            this.body = body;
            return this;
        }

        return null;
    }

    /**
     * 将数据包写入输出流中
     *
     * @param os 输出流
     */
    public void write(OutputStream os) throws IOException {
        os.write(new byte[] {this.cmd});
        os.write(BytesHelper.toBytes(this.bodySize));
        os.write(this.body);
        os.flush();
    }
}
