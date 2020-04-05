package cn.itcast.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @ClassName: CyclicBarrierDemo
 * @Description: 演示JUC的CyclicBarrier的线程休眠唤醒方式
 *          底层基于ReentrantLock和Condition实现
 *          一组线程等待至某个状态之后再全部同时执行。
 *
 *          线程1 正在准备....
 *          线程2 正在准备....
 *          线程3 正在准备....
 *          线程1启动完毕，时间 1585198065714
 *          线程2启动完毕，时间 1585198065714
 *          线程3启动完毕，时间 1585198065714
 * @Author: BobLiang
 * @Date: 2020/3/26 12:01
 **/
public class CyclicBarrierDemo {
    /**
     * 创建CyclicBarrier实例,这里指定三个线程来唤醒。
     **/
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

    public static void main(String[] args) {
        //1.创建三个线程，并分别启动
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 正在准备....");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "启动完毕，时间 " +
                        System.currentTimeMillis());
            }
        }, "线程1").start();

        new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " 正在准备....");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "启动完毕，时间 " +
                            System.currentTimeMillis());
                }, "线程2").start();

        new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " 正在准备....");
                    try {
                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "启动完毕，时间 " +
                            System.currentTimeMillis());
                }, "线程3").start();


    }

}
