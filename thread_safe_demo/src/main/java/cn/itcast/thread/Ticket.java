package cn.itcast.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: Ticket
 * @Description: 演示线程安全票对象
 * @Author: BobLiang
 * @Date: 2020/3/23 23:05
 **/
public class Ticket implements Runnable {
    static int ticketNum = 100;

    //    @Override
//    public void run() {
//        while (true) {
//            //售卖逻辑就是票卖没了就停止售票
//            if(ticketNum>0){
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                String threadName = Thread.currentThread().getName();
//                System.out.println("线程 ："+threadName+" 票数： "+ ticketNum--);
//            }
//        }
//    }

    //1.解决线程不安全的问题，Java提供的7种同步机制之第一：同步代码块 synchronized
    //1.1同步代码块 synchronized, 需要先创建一个同步锁对象
//    private Object object = new Object();
//    @Override
//    public void run() {
//        while (true) {
//            //售卖逻辑就是票卖没了就停止售票
//            //1.2，给这段售票代码加上同步代码块，确保只有一个线程执行
//                //同步代码块的使用需要先创建一个锁对象,这个同步锁对象相当于一个钥匙，只有获取该锁对象的一个线程才能执行卖票逻辑。
//            synchronized (object) {
//                if(ticketNum>0){
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    String threadName = Thread.currentThread().getName();
//                    System.out.println("线程 ："+threadName+" 票数： "+ ticketNum--);
//                }
//            }
//        }
//    }
    //加了同步代码块后，执行的结果与该程序如果是单线程时结果一致
    //线程 ：卖票窗口3 票数： 6
    //线程 ：卖票窗口3 票数： 5
    //线程 ：卖票窗口1 票数： 4
    //线程 ：卖票窗口1 票数： 3
    //线程 ：卖票窗口1 票数： 2
    //线程 ：卖票窗口1 票数： 1

    //2.解决线程不安全的问题，Java提供的7种同步机制之第二：同步方法 synchronized
//    @Override
//    public void run() {
//        while (true) {
//            sellTicket();
//        }
//    }

    //2.1同步方法synchronized
    //这里因为只在本类中使用，所以访问级别声明为private
    //同步方法有静态和非静态的
         //非静态的同步方法的锁对象就是当前类的实例 this，就是new Ticket(),就是调用同步方法的实例对象。
         //静态的同步方法的锁对象就是当前类的字节码对象,也就是Ticket.class
         //这些代码不用显示声明在synchronized关键字后，它会自动根据我们写的方法去加锁
    //如果同步方法是静态的,非静态的成员变量不能被静态方法所引用
//    private static synchronized void sellTicket(){
//            //把卖票逻辑挪到同步方法里
//            if (ticketNum > 0) {
//                try {
//                    Thread.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                String threadName = Thread.currentThread().getName();
//                System.out.println("线程 ：" + threadName + " 票数： " + ticketNum--);
//            }
//        }
        //非静态的同步方法打印的结果与卖票逻辑假如为单线程时执行的一样
        //静态的方法在debug时，测试的一次是卖票窗口二一直在卖票，和单线程执行结果一致
        //但是静态的方法在放开断点后，执行则会出现
        //    线程 ：售票窗口1 票数： 1
        //    线程 ：售票窗口3 票数： 0
        //    线程 ：售票窗口2 票数： -1

    //3.解决线程不安全的问题，Java提供的7种同步机制之第三：同步锁Lock里的ReentrantLock
    //3.1 第三种，同步锁中的ReentrantLock，创建锁对象，实例化Lock接口
            //可重入锁构造方法传的参数里,true表示公平锁，
            // 线程是否公平获取锁true-公平；false-不公平，即由某个线程独占，默认是false
            //sync = fair ? new FairSync() : new NonfairSync();
    private  Lock lock = new ReentrantLock(false);
    @Override
    public void run() {
        while (true) {
                //3.2 加锁 后面必须有try/catch代码块.lock方法与unlock()必须同时出现,如果没有unlock会出现死锁。
                        //一定要保证unlock方法一定会执行
            lock.lock();
            try {
                if(ticketNum>0){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程 ："+threadName+" 票数： "+ ticketNum--);
                }
            } catch (Exception e) {
                    e.printStackTrace();
            }finally {
                //finally保证了try代码块里的代码无论是否执行成功还是抛出异常,finally里的代码总是会执行
                //3.3 释放锁 lock的unlock方法必须处在finally代码块的第一行声明里
                lock.unlock();
            }
        }
    }
}
