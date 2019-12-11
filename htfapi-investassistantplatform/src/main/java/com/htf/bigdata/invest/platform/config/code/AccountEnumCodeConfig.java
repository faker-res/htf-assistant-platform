package com.htf.bigdata.invest.platform.config.code;

public enum AccountEnumCodeConfig implements ICodeConfig {
    LOGIN_FAIL(1101,"登录出错"),
    BACK_LOGIN_FAIL(1102,"后端登录失败"),
    PASSWORD_FAIL(1103,"密码错误"),

    MISS_COMPANY(1201,"缺少公司信息"),
    CANNOT_FIND_COMPANY(1202,"找不到公司信息"),
    USER_NO_EXISTS(1203,"找不到用户"),

    ROLE_NO_EXISTS(1251,"找不到角色"),
    ROLE_NOTALLOW_MODIFY(1252,"角色不允许修改"),

    STATUS_WRONG(1401,"错误的状态"),

    MANAGER_NOTALLOW_MODIFY(1501,"超级管理员账户不允许修改"),
    MANAGER_NOTALLOW_CHANGE(1502,"超级管理员角色不允许更换"),
    ;
    private int code;

    private String message;

    private AccountEnumCodeConfig(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
