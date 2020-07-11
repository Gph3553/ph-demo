package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.Dictionary;
import com.seaboxdata.commons.id.impl.SnowflakeIdGenerator;

import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <h3>dictory</h3>
 * <p>字典迁移</p>
 * @author : PanhuGao
 * @date : 2020-05-13 11:39
 **/

public class DictoryMv {

    public static void main(String[] args) throws SQLException {
        Connection conn = getConn();
        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(20L,20L);
        String dataSql ="select infoitem_id categoryItemId,create_time createTm,last_modytime modifyTm,structured_data dbValue,column_id id from oracle_data.di_metadb_cloumn where  structured_data is not null and structured_data !='' AND structured_data !='{}'";
        List<Dictionary> dataItem = null;
        try {
            dataItem = execut(conn,dataSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //文件信息项
        Connection conn1 = getConn();
        String fileSql="select infoitem_id categoryItemId,create_time createTm,last_modytime modifyTm,structured_data dbValue ,column_id id  from oracle_data.di_metafile_cloumn where  structured_data is not null and structured_data !='' AND structured_data !='{}'";
        List<Dictionary> df = null;
        try {
            df = execut(conn1,fileSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
       //数据库信息项
        Connection conn2 = getConn();
        String webSql=  "select infoitem_id categoryItemId,create_time createTm,last_modytime modifyTm,structured_data dbValue,column_id id  from oracle_data.di_metaweb_cloumn where  structured_data is not null and structured_data !='' AND structured_data !='{}'";
        List<Dictionary> dw = null;
        try {
            dw = execut(conn2,webSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Dictionary> dictionaries = new ArrayList<>();
        dictionaries.addAll(dataItem);
        dictionaries.addAll(df);
        dictionaries.addAll(dw);
        Connection cf= getConn();
        if(dictionaries.size()>0){
            ArrayList<Dictionary> collect = dictionaries.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(f -> f.getCategoryItemId()))), ArrayList::new));
            collect.parallelStream().forEach( m ->{
                    Long item = m.getCategoryItemId();
                    String ct = m.getCreateTm();
                    String mt = m.getModifyTm();
                    String dicInfo = m.getDbValue();
                    addDic(dicInfo,ct,mt,item,cf);
                }
        );
        }
        if(cf !=null){
            try {
                System.out.println("++++++++++数据迁移结束+++++++++++++++++++");
                cf.close();
            }catch (SQLException e){

                e.printStackTrace();
            }
        }


    }
    private static void addDic(String parm,String ct, String mt, Long item,Connection conn){
        if(parm != null && !parm.equals("")){
            List<Dictionary> dics = new ArrayList<>();
            JSONObject jsonObject = JSON.parseObject(parm);
            Set<String> set = jsonObject.keySet();
            Iterator<String> it = set.iterator();
            while (it.hasNext()){
                Dictionary dic = new Dictionary();
                String key = it.next().toString();
                String dv = jsonObject.getString(key);
                try {
                    Object parse = JSONObject.parse(dv);
                    addDic(dv,ct,mt,item,conn);
                }catch (Exception e){
                    dic.setDbKey(key);
                    dic.setDbValue(dv);
                    dic.setDelStatus(0);
                    dic.setCategoryItemId(item);
//                    dic.setModifyTm(mt);
//                    dic.setCreateTm(ct);
                    dics.add(dic);
                }
            }
            String insertSql = "INSERT INTO `rsp_server_access`.`resources_db_dictionary`(`tenant_id`, `creator`, `modifier`, `modify_tm`, `create_tm`, `del_status`, `db_key`, `db_value`, `categoryItem_id`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                execute(conn,insertSql,dics);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static Connection getConn() {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/rsp_server_dev?allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
        String username = "root";
        String password = "root";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            try {
                conn = (Connection) DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static void execute(Connection conn, String sqlFile,List<Dictionary> parms) throws Exception {
        PreparedStatement stmt = conn.prepareStatement(sqlFile);
        if (parms.size() > 0) {
            for (int i = 0; i < parms.size(); i++) {
                Dictionary dictionary = parms.get(i);
                stmt.setLong(1, 0L);
                stmt.setLong(2, 0L);
                stmt.setLong(3, 0L);
                stmt.setString(4, String.valueOf(dictionary.getModifyTm()));
                stmt.setString(5, String.valueOf(dictionary.getCreateTm()));
                stmt.setLong(6, 0L);
                stmt.setString(7, dictionary.getDbKey());
                stmt.setString(8, dictionary.getDbValue());
                stmt.setLong(9, dictionary.getCategoryItemId());
                stmt.addBatch();
            }
            stmt.executeBatch();
            stmt.close();

        }
    }

    public static List<Dictionary> execut(Connection conn, String sqlFile) throws Exception {
        PreparedStatement stmt = conn.prepareStatement(sqlFile);
        ResultSet resultSet = stmt.executeQuery();
        List<Dictionary> res = new ArrayList<>();
        while (resultSet.next()){
          Dictionary dictionary = new Dictionary();
          dictionary.setCategoryItemId(resultSet.getLong(1));
          dictionary.setCreateTm(resultSet.getString(2));
          dictionary.setModifyTm(resultSet.getString(3));
          dictionary.setDbValue(resultSet.getString(4));
          res.add(dictionary);
        }
        stmt.close();
        conn.close();
        return res;
    }

    }

