package com.xyy.test.t;


public class ThreadUnSecurity extends Object{

    static int tickets = 10;
    //int tickets = 10;
    //public final static ThreadLocal<Integer> tickets = new ThreadLocal<Integer>();

    class SellTickets implements Runnable {

        @Override
        public void run() {

            // 未加同步时产生脏数据
            while (tickets > 0) {
                synchronized (this) {
                    if (tickets <= 0)
                        break;
                    System.out.println(Thread.currentThread().getName() + "--->售出第：  " + tickets + " 票");
                    tickets--;
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            System.out.println(Thread.currentThread().getName() + "--->售票结束！");


        }
    }


    public static void main(String[] args) {


        SellTickets sell = new ThreadUnSecurity().new SellTickets();


        Thread thread1 = new Thread(sell, "1号窗口");
        Thread thread2 = new Thread(sell, "2号窗口");
        Thread thread3 = new Thread(sell, "3号窗口");
        Thread thread4 = new Thread(sell, "4号窗口");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();


    }


}

