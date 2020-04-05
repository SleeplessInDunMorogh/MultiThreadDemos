package cn.itcast.thread;

/**
 * @ClassName: DeadLockDemo
 * @Description: 演示死锁的主类
 * @Author: BobLiang
 * @Date: 2020/3/24 15:13
 **/
public class DeadLockDemo {

    public static void main(String[] args) {
        //原来DeadLock的构造函数是private的，在外部类中就不能创建实例
        //1.创建两个Runnable实例
        DeadLock deadLock1 = new DeadLock(1);
        DeadLock deadLock2 = new DeadLock(2);

        //创建两个线程，去执行两个Runnable的实例
        Thread thread1 = new Thread(deadLock1,"线程1");
        Thread thread2= new Thread(deadLock2,"线程2");

        thread1.start();
        thread2.start();

    }
}
