package cn.itcast.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: CoachRacerDemo
 * @Description: 演示JUC的CountDownLatch的countDown()和await方法
 *              使一个线程等待其他线程完成各自的工作后再执行。
 * @Author: BobLiang
 * @Date: 2020/3/25 16:34
 **/
public class CoachRacerDemo {

    static final Integer THREAD_NUMBER = 3;
    //CountDownLatch的参数需要传需要等待几个线程执行完毕
    private CountDownLatch countDownLatch = new CountDownLatch(THREAD_NUMBER);

    //运动员方法，这里会有三个运动员线程调用该方法
    public void racer(){
        //1.获取运动员名称
        String name = Thread.currentThread().getName();
        //2.运动员：打印准备信息
        System.out.println(name+ "  正在准备...");

        //3.线程休眠1000毫秒，代表运动员正在准备
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //4.运动员准备完毕，代表一个运动员线程执行结束，CounDownLatch减一
        System.out.println(name+"   报告准备完毕");
        countDownLatch.countDown();

    }

    //教练方法
    public void coach(){

        //1.获取教练名称
        String name = Thread.currentThread().getName();
        //2.教练：打印等待信息
        System.out.println(name +"  正在等待运动员全部准备完毕...");
        //3.教练开始等待
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //4.全部运动员线程准备完毕后，教练线程开始训练
        System.out.println("运动员全部准备完毕，开始训练！");
    }

    public static void main(String[] args) {
        //final修饰的引用变量一旦初始化赋值就不能再指向其他的对象，但是该引用变量指向的内容可变
        final CoachRacerDemo coachRacerDemo = new CoachRacerDemo();

        //创建三个运动员线程
        Thread thread1 = new Thread(() -> coachRacerDemo.racer(),"运动员1");
        Thread thread2 = new Thread(() -> coachRacerDemo.racer(),"运动员2");
        Thread thread3 = new Thread(() -> coachRacerDemo.racer(),"运动员3");
        //创建一个教练线程
        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                coachRacerDemo.coach();
            }
        },"教练");

        thread4.start();
        thread1.start();
        thread2.start();
        thread3.start();

        //二、为什么要用join()方法
        //在很多情况下，主线程生成并启动了子线程，
        // 如果子线程里要进行大量的耗时的运算，主线程往往将于子线程之前结束，但是如果主线程处理完其他的事务后，
        // 需要用到子线程的处理结果，也就是 主线程需要等待子线程执行完成之后再结束，这个时候就要用到join()方法了。
        try {
            thread4.join();
            System.out.println(thread4.isAlive());
            thread1.join();
            System.out.println(thread1.isAlive());
            thread2.join();
            System.out.println(thread2.isAlive());
            thread3.join();
            System.out.println(thread3.isAlive());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
