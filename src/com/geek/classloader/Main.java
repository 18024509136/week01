package com.geek.classloader;

import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws Exception {
        // 这里因为放到了项目根目录下，所以传了相对路径，可以支持写绝对路径
        Class<?> clazz = new HelloClassLoader().findByClassFile("Hello.xlass", "Hello");
        // 获取Hello类的方法对象
        Method helloMethod = clazz.getDeclaredMethod("hello");
        // 反射调用Hello类的hello方法
        helloMethod.invoke(clazz.newInstance());
    }
}
