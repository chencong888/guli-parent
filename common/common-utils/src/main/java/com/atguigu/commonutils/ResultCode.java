package com.atguigu.commonutils;

public enum ResultCode {
    ERROR(20001),SUCESS(20000);


    private Integer value;
    public Integer getValue(){
        return this.value;
    }
    private ResultCode(Integer value){
        this.value = value;
    }

    public static void main(String[] args) {
        System.out.println(ERROR.value);
    }
}
