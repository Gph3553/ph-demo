package com.example.demo;/**
 * <h3>dictory</h3>
 * <p>测试审核的保存</p>
 *
 * @author : PanhuGao
 * @date : 2020-05-26 00:00
 **/

import com.example.demo.model.BelongSystemDto;
import com.example.demo.model.CategoryDto;
import com.example.demo.model.CategoryItemDto;
import com.example.demo.model.Dictionary;
import com.example.demo.service.AuditOperService;
import com.example.demo.service.DictoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 PanHu.Gao
 * @创建时间 2020/5/26
 * @描述
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = com.example.demo.DictoryApplication.class)
@ActiveProfiles("gph")
public class AuditTest {
  @Autowired
    private AuditOperService auditOperService;
  @Autowired
  private DictoryService dictoryService;

  @Test
    public void auditTest(){
      CategoryDto categoryDto = new CategoryDto();
      List<CategoryItemDto> categoryItemList = new ArrayList<>();
      CategoryItemDto categoryItemDto = new CategoryItemDto();
      categoryItemDto.setName("测试001").setDataType("CHAR").setShareAttr("UNCONDITIONAL").setShareReason("WU")
              .setStandardfieldStatus(1).setOpenSocietyConditions("tongguo").setIsOpenSociety(1)
              .setDataLength("20").setDbFiledLength("20").setDbFiledName("test").setDbFiledType("String")
              .setDataLength("10").setIsPublish(true).setIsPush("0");
     categoryItemList.add(categoryItemDto);
      List<BelongSystemDto> belongSystemList = new ArrayList<>();
      BelongSystemDto belongSystemDto = new BelongSystemDto();
      belongSystemDto.setSystemName("dashuju").setSystemDescription("大数据管理系统");
      belongSystemList.add(belongSystemDto);
      categoryDto.setCategoryItemList(categoryItemList);
      categoryDto.setBelongSystemList(belongSystemList);
      auditOperService.auditData(categoryDto);
  }

  @Test
  public void addDataAccess(){
      Dictionary dictionary = new Dictionary();
      dictionary.setDbKey("001");
      dictionary.setDbValue("ceshi");
      dictionary.setCategoryItemId(1L);
      dictionary.setDelStatus(0);
      dictionary.setCreator(3L);
      dictionary.setModifier(3L);
      dictionary.setTenantId(3L);
      LocalDateTime now = LocalDateTime.now();
      dictionary.setCreateTm(now.toString());
      dictionary.setModifyTm(now.toString());
      dictoryService.save(dictionary);
  }
}
