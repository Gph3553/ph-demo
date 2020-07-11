package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.BelongSystem;
import com.example.demo.model.RCBelongSystem;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BelongSystemMapper extends BaseMapper<BelongSystem> {

    @Insert("<script>" +
            "INSERT INTO data_access_belong_system " +
            "(id,creator,create_tm,system_name,system_description,data_access_id) \n" +
            "VALUES" +
            "<foreach collection='belongSystemList' item='item' index='index' separator=','>" +
            "(#{item.id},#{item.creator},now(),#{item.systemName}," +
            "#{item.systemDescription},#{item.dataAccessId})" +
            "</foreach>" +
            "</script>")
    Boolean saveBelongSystemList(@Param("belongSystemList") List<RCBelongSystem> belongSystemList);

    @Delete("DELETE FROM data_access_belong_system WHERE data_access_id = #{dataAccessId}")
    Boolean deleteBelongSystem(@Param("dataAccessId") Long dataAccessId);
}
