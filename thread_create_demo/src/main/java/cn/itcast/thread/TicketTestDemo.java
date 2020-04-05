package cn.itcast.thread;

/**
 * @ClassName: TicketTestDemo
 * @Description: 测试卖票程安全问题的主类
 * @Author: BobLiang
 * @Date: 2020/3/23 22:51
 **/
public class TicketTestDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        //创建三个线程,执行Ticket的run()线程体
        //线程新建
        Thread thread1 = new Thread(ticket, "售票窗口1");
        Thread thread2 = new Thread(ticket, "售票窗口2");
        Thread thread3 = new Thread(ticket, "售票窗口3");

        //线程进入就绪态
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
