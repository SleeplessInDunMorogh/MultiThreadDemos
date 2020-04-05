package cn.itcast.thread;

/**
 * @ClassName: MyThreadImplementsRunnable
 * @Description:Java中线程有四种创建方式之二：实现Runnable接口(java.lang包下)
 * @Author: BobLiang
 * @Date: 2020/3/23 17:43
 **/
public class MyRunnable implements Runnable {
    public void run() {
        //在run()方法里实现自己的线程任务,就是线程体。
        for (int i = 0; i < 10; i++) {
            System.out.println("MyRunnable>>>>>" + Thread.currentThread().getName() + "  执行时间"
                    + System.currentTimeMillis() + "执行次数" + i);

        }
    }
}
