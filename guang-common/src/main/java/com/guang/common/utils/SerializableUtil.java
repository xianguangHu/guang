package com.guang.common.utils;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.session.Session;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * session序列化工具
 *
 * @author huxianguang
 * @create 2017-11-09-上午9:48
 **/
public class SerializableUtil {

    public static String serializa(Session session) {
        if (null == session) {
            return null;
        }
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(session);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error :" + e);
        }
    }
}
