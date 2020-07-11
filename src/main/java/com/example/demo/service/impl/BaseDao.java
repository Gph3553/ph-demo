package com.example.demo.service.impl;

import com.example.demo.annotation.Colume;
import com.example.demo.annotation.Id;
import com.example.demo.annotation.Table;
import com.example.demo.exception.IdMistakeException;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <h3>dictory</h3>
 * <p>测试数据库</p>
 * @author : PanhuGao
 * @date : 2020-05-15 15:29
 **/
public class BaseDao<E> {
    private Class<?> cls;
    private Map<String,String> columnPropertyMap = new HashMap<>();
    public BaseDao(){
        //得到父类的泛型
        Type genericSuperclass = getClass().getGenericSuperclass();
        //得到实际的类型参数数组
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        //得到第一个泛型的Class
        cls= (Class<?>) actualTypeArguments[0];
    }
    /**
     *@description
     *@parm  
     *@return  
     *@user  Panhu.Gao
     *@date  2020/5/15
     */
    public String delte(Object id){
        int affectRow = 0 ;
        String sql = "";
        if(scanId()==null){
            throw  new IdMistakeException("没有Id设置，请设置Id");
        }else {
           sql = "delete from "+ scanTableName()+"where "+ scanId()+"=?";
        }
        return sql;
    }
    //传入一个对象，根据主键更新这个对象里面其他的值
    public String update(E e) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
       String methodname= "get"+scanId().substring(0,1).toUpperCase()+scanId().substring(1);
        Method declaredMethod = e.getClass().getDeclaredMethod(methodname);
        Object invoke = declaredMethod.invoke(e);
        String i = delte(invoke);

      return i;
    }
    /**
     * 拼装字符串的时候根据传入的对象自动生成对应的属性值
     */
    public String fields(){
        String result = null;
        String field2 = null;
        List<Object> field_columnName = getField_ColumnName();
        StringBuffer stringBuffer = new StringBuffer();
        for(Object fd: field_columnName){
           field2= fd.toString()+",";
           stringBuffer.append(field2);
        }
        result = stringBuffer+"";
        String substring = result.substring(0, result.length() - 1);
        return substring;
    }
    /**
     * 拼装字符串的时候根据传入的对象自动生成的占位部分
     */
    public String placeholder(){
        String result = null;
        String field = null;
        StringBuffer stringBuffer = new StringBuffer();
        Field[] declaredFields = cls.getDeclaredFields();
        String s = scanId();
        for (Field fd: declaredFields){
            if(fd.equals(s)){
                if(idIsPrimaryKey(fd)) {
                    field=null+",";
                    System.out.println("主键是自增的");
                }else {
                    field="?,";
                    System.out.println("主键不是自增的");
                }
            }else {
                field="?,";
            }
            stringBuffer.append(field);
        }
         result=stringBuffer+"";
         result=result.substring(0, result.length()-1);
        return result;
    }

    /**
     *
     */

    /**
     * 传入一个对象并将他的值保存到数据库
     * @return
     */
    public String save(E e){
        String sql = "insert into "+scanTableName()+"("+fields()+")"+" values( "+placeholder()+")";
        Field[] declaredFields = e.getClass().getDeclaredFields();
        List<Object> objects = new ArrayList<>();
        for(Field field: declaredFields){
            PropertyDescriptor pd;
            try {
               pd= new PropertyDescriptor(field.getName(),e.getClass());
                Method readMethod = pd.getReadMethod();
                objects.add(readMethod.invoke(e));
            } catch (IntrospectionException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
        return sql+","+objects.toString();
    }

    /**
     * 根据注解获取列名以及列名
     * @return
     */
    private List<Object> getField_ColumnName(){
        Field[] declaredFields = cls.getDeclaredFields();
        ArrayList<Object> l1 = new ArrayList<>();
        for(Field field : declaredFields){
            //如果属性上有对应的列注解类型则获取这个注解类型
            Colume colume = field.getAnnotation(Colume.class);
            if (colume != null){
                String cname = colume.value();
                l1.add(cname);
            }else {
                String fn = field.getName();
                l1.add(fn);
            }
        }
        return l1;
    }

    /**
     * 判断主键是否自增
     * @return
     */
    public boolean idIsPrimaryKey(Object obj){
        boolean flag = false;
        Field[] declaredFields = cls.getDeclaredFields();
        for(Field field: declaredFields){
            Id id = field.getDeclaredAnnotation(Id.class);
            if(id.isAutoIncrease()){
                flag = true;
            }else {
                flag = false;
            }
        }
        return flag;
    }




    //根据注释获取主键值
  private String scanId(){
        //获取所有声明的属性
      Field[] declaredFields = cls.getDeclaredFields();
      for(Field field:declaredFields){
          Id did = field.getDeclaredAnnotation(Id.class);
          if(did !=null){
              String value = did.value();
              if(value.trim().equals("")){
                  value = field.getName();
              }
              columnPropertyMap.put(value,field.getName());
              return value;
          }
      }
    return null;
  }

  //根据注释获取对应的表名
  private String scanTableName(){
      Table t = cls.getAnnotation(Table.class);
      String tn = null;
      if(t !=null){
          tn = t.value();
      }else {
          tn = cls.getSimpleName();
      }
      return tn;



  }
}
