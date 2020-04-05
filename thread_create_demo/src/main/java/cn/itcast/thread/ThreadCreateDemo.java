package cn.itcast.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName: ThreadCreateDemo
 * @Description:
 * @Author: BobLiang
 * @Date: 2020/3/23 16:39
 **/
public class ThreadCreateDemo {
    static final int MAIN_CYCLE_TIMES = 10;

    public static void main(String[] args) {

        //方式一：继承Thread类
//          1.1创建自定义线程类实例,main方法是主线程，我们创建的自定义线程是子线程
//        MyThread myThread = new MyThread();
//          1.2启动线程
//        myThread.start();
//          1.3在main主线程打印信息,用来区别子线程
//        for (int j = 0; j < MAIN_CYCLE_TIMES; j++) {
//            System.out.println("main主线程启动了" + System.currentTimeMillis());
//
//        }

        //方式二：实现Runnable接口
            //Thread类有很多构造方法
            //一种是无参构造函数,调用Thread类中的run方法
            //一种是可以传进去实现了Runnable接口的类实例
            //还可以在传进去Runnable接口的实例后，为线程再传进去一个线程名
//        Thread thread = new Thread(new MyRunnable(),"runnable");
//        thread.start();
//        for (int j = 0; j < MAIN_CYCLE_TIMES; j++) {
//            System.out.println("main主线程  " + Thread.currentThread().getName() + "  执行时间  "
//                    + System.currentTimeMillis()+ " 执行次数 " + j);
//
//        }
        //方式三：实现Callable接口
        //3.1 创建FutureTask实例，再去创建MyCallable实例.FutureTask构造里需要传Callable接口的实现类实例
            //因为FutureTask类实现了Runnable接口，因此可以作为参数传进Thread的构造方法里
        //说明：Callable接口依赖FutureTask类，FutureTask类>> 实现了 RunnableFuture接口 >>继承了 Runnable接口和Future接口
//        FutureTask futureTask = new FutureTask<String>(new MyCallable());
//         //3.2 创建Thread类实例
//        Thread thread = new Thread(futureTask,"myCallable");
//        thread.start();
//        //3.3 创建main方法线程打印
//        for (int j = 0; j < MAIN_CYCLE_TIMES; j++) {
//            System.out.println("main主线程  " + Thread.currentThread().getName() + "  执行时间  "
//                    + System.currentTimeMillis()+ " 执行次数 " + j);
//        }
//        //3.4 获取并打印MyCallable执行结果
//        try {
//            //获取结果
//            System.out.println(futureTask.get());
//            //判断任务是否执行完成
//            System.out.println(futureTask.isDone());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        //方式四：使用线程池创建线程
        //4.1 使用Executors工具类来获取线程池对象,返回的是次顶级的ExecutorService对象
        // 参数的意思是最大保证有多少个线程
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        //4.2 使用线程池对象获取线程并执行MyRunnable实例。
        // ExecutorService的execute(Runnable runnable)传的就是Runnable结构的实例。
        executorService.execute(new MyRunnable());
        //4.3区分main主线程,再进行打印。
        for (int j = 0; j < MAIN_CYCLE_TIMES; j++) {
            System.out.println("main主线程  " + Thread.currentThread().getName() + "  执行时间  "
                    + System.currentTimeMillis()+ " 执行次数 " + j);
        }
    }
}
