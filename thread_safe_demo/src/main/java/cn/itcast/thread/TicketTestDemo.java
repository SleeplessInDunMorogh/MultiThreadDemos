package cn.itcast.thread;

/**
 * @ClassName: TicketTestDemo
 * @Description: 卖票线程安全测试主类
 * @Author: BobLiang
 * @Date: 2020/3/23 23:11
 **/
public class TicketTestDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        //卖票在单线程时是没有问题的。
        //启用多个线程去卖票,结果与单线程执行结果不一致。有重复卖的，有不存在的票的。
        Thread thread1 = new Thread(ticket,"卖票窗口1");
        Thread thread2 = new Thread(ticket,"卖票窗口2");
        Thread thread3 = new Thread(ticket,"卖票窗口3");

        thread1.start();
        thread2.start();
        thread3.start();
        //执行结果大致如下，问题1: 81这张票窗口2和窗口1卖了两次，问题2，不存在0和-1的票数
        //这就是线程不安全了
        //线程 ：售票窗口2 票数： 83
        //线程 ：售票窗口1 票数： 84
        //线程 ：售票窗口3 票数： 82
        //线程 ：售票窗口2 票数： 81
        //线程 ：售票窗口1 票数： 81
        //线程 ：售票窗口3 票数： 80
        //线程 ：售票窗口1 票数： 79
        //线程 ：售票窗口2 票数： 78
//        线程 ：售票窗口1 票数： 1
//        线程 ：售票窗口3 票数： 0
//        线程 ：售票窗口2 票数： -1

    }
}
