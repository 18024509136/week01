package com.geek.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 自定义classLoader
 *
 * @author huangxiaodi
 * @since 2021-03-20
 */
public class HelloClassLoader extends ClassLoader {

    /**
     * 通过加密的class文件加载出对应的class类
     *
     * @param filePath      加密的class文件绝对路径
     * @param classFullName 要加载的类的全名
     * @return 待加载的类
     */
    public Class<?> findByClassFile(String filePath, String classFullName) {
        File file = new File(filePath);
        FileInputStream inputStream = null;
        Class<?> clazz = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] encryptBytes = new byte[inputStream.available()];
            inputStream.read(encryptBytes);
            byte[] decodeBytes = decode(encryptBytes);
            clazz = defineClass(classFullName, decodeBytes, 0, decodeBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return clazz;
    }

    /**
     * 解密class文件内容，算法为 y = 255 - x
     *
     * @param encryptBytes 加密的class文件的字节数组
     * @return
     */
    private byte[] decode(byte[] encryptBytes) {
        byte[] decodeBytes = new byte[encryptBytes.length];
        for (int i = 0; i < encryptBytes.length; i++) {
            // 先转为整形
            int encryptInt = encryptBytes[i] & 0xff;
            // 计算完成后再强制转为byte
            decodeBytes[i] = (byte) (255 - encryptInt);
        }
        return decodeBytes;
    }
}
