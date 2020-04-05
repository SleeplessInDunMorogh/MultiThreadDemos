package cn.itcast.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: OddEvenDemo
 * @Description: 多线程打印1-10奇偶数演示线程唤醒
 * JUC的locks包下的Condition
 * @Author: BobLiang
 * @Date: 2020/3/25 12:14
 **/
public class OddEvenJUCLocksConditionDemo {
    /**
     * 判断奇偶数的变量
     **/
    private int i = 0;
    //这里的可重入锁要是线程独占的，不公平的，因此不传参数，默认false，线程独占
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //判断是否是奇数的方法
    void odd() {
        while (i < 10) {
            lock.lock();
            try {
                if (i % 2 == 1) {
                    //是奇数，我就打印
                    System.out.println(" " + Thread.currentThread().getName() + " 奇数 " + i);
                    i++;
                    //打印完,我通知偶数线程去执行
                    condition.signal();
                } else {
                    //是偶数,让线程进入等待状态，也释放了锁
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }


        }
    }

    //判断是否是偶数的方法
    void even() {
        while (i < 10) {
            lock.lock();
            try {
                if (i % 2 == 0) {
                    //是偶数，我就打印
                    System.out.println(" " + Thread.currentThread().getName() + " 偶数 " + i);
                    i++;
                    //打印完，我通知奇数线程去执行
                    condition.signal();
                } else {
                    //是奇数，让线程进入等待状态，也释放了锁
                    condition.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }

        }

    }

    public static void main(String[] args) {
        OddEvenJUCLocksConditionDemo oddEvenDemo = new OddEvenJUCLocksConditionDemo();
        //创建两个线程去执行奇数方法和偶数方法
        //new一个Runnable接口的匿名内部类

        //奇数线程
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                oddEvenDemo.odd();
            }
        }, "-奇数线程");

        //偶数线程
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                oddEvenDemo.even();
            }
        }, "-偶数线程");

        //启动线程，让线程进入就绪态
        thread1.start();
        thread2.start();
    }
}
