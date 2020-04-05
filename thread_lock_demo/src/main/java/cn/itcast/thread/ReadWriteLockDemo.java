package cn.itcast.thread;

import com.sun.applet2.AppletParameters;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: ReadWriteLockDemo
 * @Description: 测试JUC包下的ReadWriteALock
 *              读写锁，读的时候可以同时读，但读的时候不能写
 *              写的时候不能同时写，写的时候不能读。
 * @Author: BobLiang
 * @Date: 2020/3/28 19:07
 **/
public class ReadWriteLockDemo {

    //创建集合
    private static Map<String, Object> map = new HashMap<String, Object>();
    //创建可重入读写锁实例
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    //获取读锁
    private Lock readLock = readWriteLock.readLock();
    //获取写锁
    private Lock writeLock = readWriteLock.writeLock();

    /**
     * 读操作
     **/
    public String read(String key) {
        readLock.lock();
        System.out.println(Thread.currentThread().getName() + " 读操作开始了...");
        try {
            //线程休眠1秒，从而模拟又读又写的操作
            Thread.sleep(1000);
            //线程开始读
            return (String) map.get(key);
        } catch (Exception e) {
            e.getMessage();
            return null;
        } finally {
            readLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 读操作完毕！");
        }

    }

    /**
     * 写操作
     **/
    public void write(String key,String value) {
        writeLock.lock();
        System.out.println(Thread.currentThread().getName() + " 写操作开始了...");
        try {
            //线程休眠1秒，从而模拟又读又写的操作
            Thread.sleep(1000);
            //线程开始读
            map.put(key, value);
        } catch (Exception e) {
            e.getMessage();
        } finally {
            writeLock.unlock();
            System.out.println(Thread.currentThread().getName() + " 写操作完毕！");
        }
    }

    public static void main(String[] args) {
        final ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        readWriteLockDemo.write("key1", "value1");
        Thread thread1 = new Thread(new Runnable() {
            public void run() {
                readWriteLockDemo.read("key1");
            }
        },"线程1");

        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                readWriteLockDemo.read("key1");
            }
        },"线程2");

        Thread thread3 = new Thread(new Runnable() {
            public void run() {
                readWriteLockDemo.read("key1");
            }
        },"线程3");

        Thread thread4 = new Thread(new Runnable() {
            public void run() {
                readWriteLockDemo.read("key1");
            }
        },"线程4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
