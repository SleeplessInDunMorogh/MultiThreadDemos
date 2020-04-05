package cn.itcast.thread;

import java.util.concurrent.Callable;

/**
 * @ClassName: MyCallable
 * @Description: Java中线程有四种创建方式之三：实现Callable接口(java.util.concurrent(J.U.C)包下)
 * @Author: BobLiang
 * @Date: 2020/3/23 18:19
 **/
public class MyCallable implements Callable<String> {

    //Callable接口需要指定返回值的泛型
    //Callable的call()方法与Runnable的run()有些类似，不同在于
    //1.方法名不一样 2.call方法有返回值,run方法没有

    public String call() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " 执行时间：" + System.currentTimeMillis()
                    + " 执行次数：" + i);

        }
        return "Mycallable接口执行完成!";
    }
}
