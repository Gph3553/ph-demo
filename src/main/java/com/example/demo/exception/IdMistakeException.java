package com.example.demo.exception;
/**
 * <h3>dictory</h3>
 * <p>自定义Id错误类</p>
 * @author : PanhuGao
 * @date : 2020-05-15 20:28
 **/

public class IdMistakeException extends RuntimeException {
    public IdMistakeException(String message){
        super.setStackTrace(getStackTrace());
    }
}
