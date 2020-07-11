package com.example.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.example.demo.service.IdGenger;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.util.ObjectUtils;

public class MySqlHandler implements MetaObjectHandler {
    private static final String ID = "id";

    private final IdGenger idGenger;

    public MySqlHandler(IdGenger idGenger){
        this.idGenger = idGenger;
    }
    //插入时进行主键填充，判断该字段是否为主键，如果是主键且为null，那么生成的雪花id进行填充
    @Override
    public void insertFill(MetaObject metaObject) {
        System.out.println("填充+++++++++++++++++++++++++++++++开始");
        if(ObjectUtils.isEmpty(metaObject)){
            return ;
        }
        if (metaObject.hasGetter(ID) && metaObject.hasSetter(ID) && metaObject.getGetterType(ID).equals(Long.class) && metaObject.getSetterType(ID).equals(Long.class)){
            Long value = (Long) metaObject.getValue(ID);
            if(value == null || value.equals(0L)){
                metaObject.setValue(ID,idGenger.getId());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {}
}
