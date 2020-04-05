package cn.itcast.thread;

/**
 * @ClassName: Ticket
 * @Description: 暴露线程安全问题测试类
 * @Author: BobLiang
 * @Date: 2020/3/23 22:48
 **/
public class Ticket implements Runnable {
    int ticketNum = 100;

    public void run() {
        while (true) {
            if (ticketNum > 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String threadName = Thread.currentThread().getName();
                System.out.println("线程 ：" + threadName + " 票数： " + ticketNum--);
            }
        }
    }
}
