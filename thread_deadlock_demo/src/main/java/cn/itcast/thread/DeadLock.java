package cn.itcast.thread;

/**
 * @ClassName: DeadLock
 * @Description: 演示死锁的类
 * @Author: BobLiang
 * @Date: 2020/3/24 15:12
 **/
public class DeadLock implements Runnable {
    //启用线程的开关
    private int flag;
    //此处的锁对象一定要是共享的,如果是非静态的则不是共享的变量，获取锁时会new创建一个新的锁对象，导致如下两个语句都被执行
    //线程 线程2已经获得obj1的锁,
    //线程 线程1已经获得obj2的锁,
    //obj1需要设置成static，static的成员变量所有该类的实例共享这个obj1变量,就是共享资源
    //也就是不管我们创建多少个该类的实例，obj1的变量都是不变的，都是这两个固定的对象。
    private static Object obj1 = new Object();
    private static Object obj2 = new Object();
    //演示两个线程去互相抢占两个锁，死锁的四个条件：互斥条件，不可剥夺条件，请求与保持条件，循环等待条件

    //构造函数，使得在创建该类实例时可以赋值给flag
    public DeadLock(int flag) {
        this.flag = flag;
    }

    public void run() {
        if (flag == 1) {
            //执行线程1的代码,持有obj1的锁
            synchronized (obj1) {
                System.out.println("线程  " + Thread.currentThread().getName()
                        + " 正在持有：obj1的锁");
                try {
                    //让线程休眠,随后去获取obj2的锁.调用sleep()线程将会进入阻塞状态,但是不会释放锁。
//sleep使用的时候，线程并不会放弃对象(笔记：Object)的使用权,即不会释放对象锁,所以在同步方法或同步块中使用sleep,一个线程访问时，其他的线程也是无法访问的。
                    //而wait是会释放对象锁的，就是当前线程放弃对象的使用权，让其他的线程可以访问。
                    //理解：创建了一个内存对象，就相当于加了一把锁
                    //线程执行wait方法时，需要另一个线程调用notify进行唤醒
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //休眠之后,线程1在持有锁1的情况下,去尝试获取obj2的锁。synchronized的锁都是可重入锁，不可中断，非公平锁。
                //由于锁对象obj1,非静态的共享变量，导致线程1和线程2可以互相获得对方的锁
                //问题**：这里因为之前写的第二个同步代码块，放在第一个同步代码块平级的位置，导致线程1总是能获取到obj2的锁,
                // 下面的打印总是执行，即使在给obj1锁对象加static让其变为共享变量之后。
                //需要把下面的代码放到第一个同步代码块的obj1的作用范围内，否则就不持有obj1锁了。
                //挪到了obj1的作用范围内，该线程在休眠阻塞1秒后再去获取obj2的锁
                synchronized (obj2) {
                    System.out.println("线程 " + Thread.currentThread().getName() +
                            "已经获得obj2的锁,");
                }
            }

        } else {
            //执行线程2的代码，持有obj2的锁
            synchronized (obj2) {
                System.out.println("线程  " + Thread.currentThread().getName()
                        + " 正在持有：obj2的锁");
                try {
                    //让线程休眠,随后去获取obj1的锁。调用sleep()线程将会进入阻塞状态,但是不会释放锁。
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //休眠之后,去尝试获取obj1的锁
                //由于锁对象obj2,非静态的共享变量，导致线程1和线程2可以互相获得对方的锁
                synchronized (obj1) {
                    System.out.println("线程 " + Thread.currentThread().getName() +
                            "已经获得obj1的锁,");
                }
            }

        }

    }
}
