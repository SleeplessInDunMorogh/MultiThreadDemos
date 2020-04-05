package cn.itcast.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName: AtomicDemo
 * @Description: 演示多线程控制类之二：JUC包下Atomic原子类的操作，保证*原子性*
 *          i++这种步进表达式不是原子操作，事实上被分解成三部
 *          tmp1 = i;
 *          tmp2 =tmp1+1;
 *          i=tmp2;
 * @Author: BobLiang
 * @Date: 2020/3/27 15:59
 **/
public class AtomicDemo {
    //static int n; //执行n++操作的变量
    static AtomicInteger atomicInteger;

    public static void main(String[] args) {
        int j = 0;

        long begin = System.currentTimeMillis();
        while (j < 100) {
            //这里需要给n赋一个初始值,0。否则总是赋值给成员变量n,累加越来越大
            //内部类访问局部变量n时,n需要声明为final类型，是为了防止在构造函数创建内部类之后，局部变量和内部类里的变量值不一致了
            //这里如果不使用AtomicInteger则会出现最终打印结果n有的不到2000，有的超过2000,2000是正常值。
            //n = 0;
            atomicInteger = new AtomicInteger(0);

            Thread thread1 = new Thread(new Runnable() {

                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        //n++;
                        atomicInteger.getAndIncrement();
                    }
                }
            }, "线程1");

            Thread thread2 = new Thread(new Runnable() {

                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        atomicInteger.getAndIncrement();
                    }
                }
            }, "线程2");

            thread1.start();
            thread2.start();
            //为了防止主线程在子线程执行完前先结束，使用thread.join():主线程需要等待子线程执行完
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("n的最终值是： " + atomicInteger.get());
            j++;

        }
        long end = System.currentTimeMillis();
        System.out.println(" 计算花费时间为："+(end-begin)+"毫秒");
    }
}
