package com.example.demo.controller;

import com.example.demo.service.EsOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class EsOptionController {
  @Autowired
  private EsOptionService esOptionService;
  @GetMapping("/es/initOption")
  public Boolean initEs(){
      return esOptionService.initOption();
  };

  @GetMapping("/es/resInfo")
  public Boolean getResInfo(){
    return esOptionService.selResInfo();
  };

  //es求各部门的资源数量
  @GetMapping("/select/avgCount")
  public void orgCnt(){ esOptionService.sumAfterTwiceAgg();    }

  //es求和
  @GetMapping("/select/sumCount")
  public int orgSum() throws IOException { return esOptionService.sumOrg(); }

  //es多字段分组求和
  @GetMapping("/select/dfSum")
  public Boolean dSum(){
     return esOptionService.fieldsSum();
  }

  //es实现搜索的联想功能
  @GetMapping("/select/think")
  public List<String> queryThink(String keyword){
       return esOptionService.esThink(keyword);
  }

  //es suggest
  @GetMapping("/select/thinkDouble")
  public List<String> queryThinkDl(String keyword){
    return esOptionService.esThinkDl(keyword);
  }

  //进行分词生成索引
  @GetMapping("/select/analyIndex")
  public Boolean analyIndex(){
    return esOptionService.analyIndex();
  }


}
