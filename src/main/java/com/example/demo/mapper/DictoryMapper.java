package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.model.Dictionary;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictoryMapper extends BaseMapper<Dictionary> {
    @Select("select infoitem_id categoryItemId,create_time createTm,last_modytime modifyTm,structured_data dbValue from oracle_data.di_metadb_cloumn where  structured_data is not null and structured_data !='' AND structured_data !='{}'")
    List<Dictionary> getDataItem();
    @Select("select infoitem_id categoryItemId,create_time createTm,last_modytime modifyTm,structured_data dbValue from oracle_data.di_metafile_cloumn where  structured_data is not null and structured_data !='' AND structured_data !='{}'")
    List<Dictionary> getFileItem();
    @Select("select infoitem_id categoryItemId,create_time createTm,last_modytime modifyTm,structured_data dbValue from oracle_data.di_metaweb_cloumn where  structured_data is not null and structured_data !='' AND structured_data !='{}'")
    List<Dictionary> getInterItem();

}
