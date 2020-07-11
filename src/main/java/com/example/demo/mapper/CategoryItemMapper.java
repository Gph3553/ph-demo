package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.CategoryItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemMapper extends BaseMapper<CategoryItem> {

    @Update("<script>" +
            "UPDATE data_access_item " +
            "<set>" +
            "<if test='c.name != null'>name = #{c.name},</if>" +
            "<if test='c.dataType != null'>data_type = #{c.dataType},</if>" +
            "<if test='c.dataLength != null'>data_length = #{c.dataLength},</if>" +
            "<if test='c.shareAttr != null'>share_attr = #{c.shareAttr},</if>" +
            "<if test='c.shareReason != null'>share_reason = #{c.shareReason},</if>" +
            "<if test='c.dbFiledName != null'>db_filed_name = #{c.dbFiledName},</if>" +
            "<if test='c.dbFiledType != null'>db_filed_type = #{c.dbFiledType},</if>" +
            "<if test='c.dbFiledLength != null'>db_filed_length = #{c.dbFiledLength},</if>" +
            "<if test='c.isPush != null'>is_push = #{c.isPush},</if>" +
            "<if test='c.isPublish != null'>is_publish = #{c.isPublish},</if>" +
            "<if test='c.isOpenSociety != null'>is_open_society = #{c.isOpenSociety},</if>" +
            "<if test='c.openSocietyConditions != null'>open_society_conditions = #{c.openSocietyConditions},</if>" +
            "modifier = #{c.modifier}," +
            "modify_tm = #{c.modifyTm}" +
            "</set>" +
            "where id = #{c.id}" +
            "</script>")
    Boolean updataDataAccessItem(@Param("c") CategoryItem categoryItem);

    @Insert("<script>" +
            "INSERT INTO data_access_item \n" +
            "(id,tenant_id,creator,modify_tm,create_tm,data_access_id,name,\n" +
            "data_type,data_length,share_attr,share_reason,db_filed_name,db_filed_type,\n" +
            "db_filed_length,is_push,is_open_society,open_society_conditions,\n" +
            "del_status,standardfield_status,resources_item_id) VALUES " +
            "<foreach collection =\"categoryItemList\" item=\"item\" index= \"index\" separator =\",\">" +
            "(#{item.id},#{item.tenantId},#{item.creator},now(),now(),#{item.dataAccessId}," +
            "#{item.name},#{item.dataType},#{item.dataLength},#{item.shareAttr},#{item.shareReason}," +
            "#{item.dbFiledName},#{item.dbFiledType},#{item.dbFiledLength},#{item.isPush},#{item.isOpenSociety}," +
            "#{item.openSocietyConditions},0,#{item.standardfieldStatus},#{item.resourcesItemId})" +
            "</foreach>" +
            "</script>")
    void saveCategoryList(@Param("categoryItemList") List<CategoryItem> categoryItemList);


    @Delete("DELETE FROM  data_access_item WHERE data_access_id = #{accessItemId} ")
    void deleteCategoryItem(@Param("accessItemId") Long accessItemId);
}
