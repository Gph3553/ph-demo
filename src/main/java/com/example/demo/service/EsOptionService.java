package com.example.demo.service;

import java.io.IOException;
import java.util.List;

public interface EsOptionService {

public Boolean initOption();

Boolean selResInfo();
    void sumAfterTwiceAgg();

    int sumOrg() throws IOException;

    Boolean fieldsSum();

    List<String> esThink(String keyword);

    List<String> esThinkDl(String keyword);

    Boolean analyIndex();
}
