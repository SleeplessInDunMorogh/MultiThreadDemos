package cn.itcast.thread;

import java.util.concurrent.Semaphore;

/**
 * @ClassName: SemaphoreWorkerMachineDemo
 * @Description: 演示Semaphore对一组组员的权限控制
 *              使用了成员内部类
 *              控制对某组资源的访问权限。
 * 8个工人使用三台机器工作,机器为互斥资源(即每次只能一个人使用)
 * @Author: BobLiang
 * @Date: 2020/3/26 16:25
 **/
public class SemaphoreWorkerMachineDemo {

    //创建成员内部类
    public static class Machine implements Runnable {
        //机器数
        private Semaphore semaphore;

        //创建构造函数，给成员变量machineNumber和semaphore初始化赋值。
        //方便在创建Worker对象时传实参
        public Machine(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void run() {
            //1.工人去获取机器,就是当前线程获取CPU的执行权
            try {
                semaphore.acquire();
                //2.打印哪个工人获取到了机器
                System.out.println(Thread.currentThread().getName() + " 获得了机器，正在工作...");
                //3.模拟工作，使线程休眠1秒
                Thread.sleep(1000);
                //4.工人工作结束，释放机器
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + "   工作完毕，释放了机器！");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //工人数
        int workerNums = 8;
        //机器数
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < workerNums; i++) {
            //为每个工人创建一个线程
            new Thread(new Machine(semaphore), "工人 " + i).start();
        }
    }

}
