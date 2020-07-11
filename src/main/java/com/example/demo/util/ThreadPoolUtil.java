package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <h3>dictory</h3>
 * <p>自定义线程池</p>
 *
 * @author : PanhuGao
 * @date : 2020-05-27 19:54
 **/
@Component
public class ThreadPoolUtil {
       private ThreadPoolUtil(){}

//       private static class LazyHolder{
//           private static final ExecutorService executorService = Executors.newFixedThreadPool(10);
//       }
//
//       public static final ExecutorService getInstance(){
//           return LazyHolder.executorService;
//       }
        private static ExecutorService executorService = null;

       public static  ExecutorService getInstance(){
           if(executorService == null){
               synchronized (ThreadPoolUtil.class) {
                   if(executorService == null){
                       executorService = Executors.newFixedThreadPool(10);
                   }
               }
           }
               return executorService;
           }
       }

