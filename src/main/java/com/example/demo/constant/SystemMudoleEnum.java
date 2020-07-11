package com.example.demo.constant;

public enum SystemMudoleEnum {

    HOEM("首页"),
    RESOURCE_CATA("测试Kafka"),
    DIRECT_MANAGE("目录管理"),
    SYS_MANAGE("系统管理"),
    DOWNLOAD_CEBTER("下载中心");

    private String module;

    SystemMudoleEnum(String module) {
        this.module = module;

    }

    public String getModule() {
        return module;
    }
}
