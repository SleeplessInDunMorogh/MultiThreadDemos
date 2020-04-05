package cn.itcast.thread;

/**
 * @ClassName: OddEvenObjectWaitNotifyDemo
 * @Description: 多线程打印1-10奇偶数演示线程唤醒
 * (Object的wait()和notify())
 * @Author: BobLiang
 * @Date: 2020/3/25 12:14
 **/
public class OddEvenObjectWaitNotifyDemo {
    /**
     * 判断奇偶数的变量
     **/
    private int i = 0;
    //创建Object的实例，调用wait和notify。笔记：这里的Object应该是互斥锁，加了static关键字就变成了共享锁。
    private Object obj = new Object();

    public static void main(String[] args) {
        final OddEvenObjectWaitNotifyDemo oddEvenDemo = new OddEvenObjectWaitNotifyDemo();
        //创建两个线程去执行奇数方法和偶数方法
        //new一个Runnable接口的匿名内部类

        //奇数线程
        Thread thread1 = new Thread(new Runnable() {

            public void run() {
                oddEvenDemo.odd();
            }
        }, "奇数线程");

        //偶数线程
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                oddEvenDemo.even();
            }
        }, "偶数线程");

        //启动线程，让线程进入就绪态
        thread1.start();
        thread2.start();
    }

    //判断是否是奇数的方法
    void odd() {
        while (i < 10) {
            synchronized (obj) {
                if (i % 2 == 1) {
                    //是奇数，我就打印它
                    System.out.println(" " + Thread.currentThread().getName() + " 奇数 " + i);
                    i++;
                    //打印完,我通知偶数线程去执行
                    obj.notify();
                } else {
                    //是偶数,让线程进入等待状态，也释放了锁
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //判断是否是偶数的方法
    void even() {
        while (i < 10) {
            synchronized (obj) {
                if (i % 2 == 0) {
                    //是偶数，我就打印
                    System.out.println(" " + Thread.currentThread().getName() + " 偶数 " + i);
                    i++;
                    //打印完，我通知奇数线程去执行
                    obj.notify();
                } else {
                    //是奇数，让线程进入等待状态，也释放了锁
                    try {
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }

    }
}
