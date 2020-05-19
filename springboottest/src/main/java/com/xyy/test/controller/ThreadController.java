package com.xyy.test.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ThreadController {

    public static int s1 = 0; //静态方法区变量不安全
    public int s2 = 0;        //成员堆变量不安全       //局部栈变量安全

    public final static ThreadLocal<Integer> stL = new ThreadLocal<Integer>(); //ThreadLocal 线程之间不共享

    public volatile int svlt = 0;  //volatile把各线程的工作内存的变量立即刷到主内存 避免出现脏读的现象  不能保证多线程安全

    @RequestMapping(value = {"/thread"})
    public String thread(Model model) throws Exception {
//        s1 = 0;
//        for (int i = 0; i < 10; i++) {
//            s1 = s1 + 1;
//            Thread.sleep(2000);
//        }
//
//        s2 = 0;
//        for (int i = 0; i < 10; i++) {
//            s2 = s2 + 1;
//            Thread.sleep(2000);
//        }

//        stL.set(0);
//        for (int i = 0; i < 10; i++) {
//            stL.set(stL.get() + 1);
//            Thread.sleep(2000);
//        }

        for (int i = 0; i < 10; i++) {
            svlt = svlt + 1;
            Thread.sleep(2000);
        }

        model.addAttribute("name", Thread.currentThread().getId() + "线程：" +
                "s1=" + s1 +
                ",,s2=" + s2 +
                ",,stL=" + stL.get() +
                ",,svlt=" + svlt);

        stL.remove();//避免内存泄露

        return "yml";

    }

}