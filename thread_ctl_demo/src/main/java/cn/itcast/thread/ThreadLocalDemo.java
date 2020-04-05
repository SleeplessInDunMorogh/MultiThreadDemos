package cn.itcast.thread;

/**
 * @ClassName: ThreadLocal
 * @Description: 演示线程控制类之一：ThreadLocal
 *              initialValue：副本创建方法，给每一个线程创建一个变量副本
 *              get: 获取变量副本的方法
 *              set: 设置变量副本，将线程更改后的值保存到副本
 * @Author: BobLiang
 * @Date: 2020/3/27 11:39
 **/
public class ThreadLocalDemo {
    //1.创建银行对象：钱(账户，这里指ThreadLocal)，取款，存款
    static class Bank {
        //1.1银行里每个用户的钱不一样，可以用ThreadLocal实现
        private ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
            //1.2因为ThreadLocal需要为每一个线程变量存一个值的副本，这个值如何获取,
            // 我们需要在ThreadLocal里提供这个创建副本的方法,Ctrl+O选择要重写的方法。
            //protected对 本类，同一个包下的类，继承类可见，其他类不可见
            //public -->protected-->无(默认)-->private
            @Override
            protected Integer initialValue() {
                //初始化都为0
                return 0;
            }
        };

        //1.3 获取变量副本 ,在这里比作取钱
        Integer get() {
            return threadLocal.get();
        }

        //1.4 设置变量副本,在这里比做存钱
        void set(Integer money) {
            threadLocal.set(threadLocal.get() + money);
        }
    }
    //2.创建转账对象：从银行中取钱，转账，存到账户
    static class Transfer implements Runnable{
        private Bank bank;

        //期望创建Transfer对象时传进来Bank对象
        public Transfer(Bank bank) {
            this.bank = bank;
        }

        public void run() {
            for(int i = 0;i< 10;i++){
                bank.set(10);
                System.out.println(Thread.currentThread().getName()+" 账户余额 :" +bank.get());
            }
        }
    }
    //3.在main方法中利用这两个对象模拟转账
    public static void main(String[] args) {
        //多个线程共享ThreadLocal变量
        Bank bank = new Bank();

        Transfer transfer = new Transfer(bank);
        Thread thread1 = new Thread(transfer," 张三 ");
        Thread thread2 = new Thread(transfer," 李四 ");

        thread1.start();
        thread2.start();
    }

}
