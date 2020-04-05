package cn.itcast.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: LockDemo
 * @Description: 演示多线程控制类之三：JUC下的Lock的实现类ReentrantLock，保证有序性
 * @Author: BobLiang
 * @Date: 2020/3/28 14:21
 **/
public class ReentrantLockDemo {

    public static void main(String[] args) {
        //可重入锁可在已经持有锁对象的情况下再次要求加锁
        ReentrantLock reentrantLock = new ReentrantLock();
        //循环加锁10次
        for (int i = 1; i <=10; i++) {
            reentrantLock.lock();
            try {
                System.out.println("加锁次数" + i);
            }catch (Exception e){
                e.getMessage();
            }

        }
        //循环解锁
        for (int j = 0; j < 10; j++) {
            try {
                System.out.println("解锁次数" + j);
            } finally {
                reentrantLock.unlock();
            }
        }

    }

}
