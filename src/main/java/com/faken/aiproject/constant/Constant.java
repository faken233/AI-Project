package com.faken.aiproject.constant;

public class Constant {
    public static final String SECURE_KEY = "HUAWEIALIBABA";
    public static final String adminId = "admin";

    //模型是否有删除权限的常量
    public static final int CAN_DELETE = 1;
    public static final int CANNOT_DELETE = 0;

    //模型类型的常量，图形或者文本
    public static final int IMAGE_MODEL = 0;
    public static final int TEXT_MODEL = 1;

    //模型是官方的还是用户的
    public static final int  OFFICIAL = 0;
    public static final int  USER = 1;

    //申请表的申请状态
    public static final int APPLICATION_PENDING = 0;
    public static final int APPLICATION_ACCEPT = 1;
    public static final int APPLICATION_REJECT = 2;


}
