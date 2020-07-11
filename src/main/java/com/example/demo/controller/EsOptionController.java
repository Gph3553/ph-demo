package com.example.demo.controller;

import com.example.demo.service.EsOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
