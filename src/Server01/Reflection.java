package Server01;

import java.lang.reflect.InvocationTargetException;

/**
 * 反射：把java类中的各种结构(方法，属性，构造器，结构)映射成一个个的java对象
 * 1. 获取Class对象
 * 三种方式：Class.forName("包名.类名")
 * 2. 可以动态创建对象
 * aClass.getConstructor().newInstance();
 */
public class Reflection {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // 三种方式
        // 1. 对象.getClass()
        Iphone iphone = new Iphone();
        Class<? extends Iphone> iphoneClass = iphone.getClass();
        // 2. 类.class()
        iphoneClass = Iphone.class;
        // 3. Class.forName("包名.类名")
        Class<?> aClass = Class.forName("Server01.Iphone");

        // 创建对象
        Iphone iphone1 = (Iphone) aClass.getConstructor().newInstance();
    }
}

class Iphone {
    public Iphone() {
    }
}
