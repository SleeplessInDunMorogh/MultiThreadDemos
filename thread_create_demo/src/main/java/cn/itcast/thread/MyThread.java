package cn.itcast.thread;

/**
 * @ClassName: MyThread
 * @Description: Java中线程有四种创建方式之一：继承Thread类(java.lang包下)
 * @Author: BobLiang
 * @Date: 2020/3/23 16:08
 **/
public class MyThread extends Thread {
    static final int CYCLE_TIMES = 10;

    @Override
    public void run() {
        for (int i = 0; i < CYCLE_TIMES; i++) {
            System.out.println("MyThread的线程执行了:" + System.currentTimeMillis());

        }
    }
}
