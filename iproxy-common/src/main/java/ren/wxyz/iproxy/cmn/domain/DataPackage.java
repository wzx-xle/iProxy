/**
 * Copyright (C) 2001-2016 wxyz <hyhjwzx@126.com>
 * <p>
 * This program can be distributed under the terms of the GNU GPL Version 2.
 * See the file LICENSE.
 */
package ren.wxyz.iproxy.cmn.domain;

import lombok.Getter;
import lombok.Setter;

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

    public DataPackage full(InputStream is) {
        return null;
    }

    public void write(OutputStream os) {

    }
}
