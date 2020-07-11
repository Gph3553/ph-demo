//package com.example.demo.util;
//
//
//import com.seaboxdata.commons.id.IdGenerator;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// *  雪花算法生成id
// * @author lituan
// */
//@Component
//public class IdGeneratorUtil {
//
//    private static IdGenerator idGenerator;
//
//    @Autowired
//    public void setIdGenerator(IdGenerator idGenerator){
//        IdGeneratorUtil.idGenerator = idGenerator;
//    }
//
//    public static Long getId(){
//        return idGenerator.getId();
//    }
//
//    public static List<Long> getIdS(int i){
//        return idGenerator.getIds(i);
//    }
//}
