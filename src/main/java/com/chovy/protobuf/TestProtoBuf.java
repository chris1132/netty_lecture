package com.chovy.protobuf;

import static java.lang.System.*;
/**
 * Created by wangchaohui on 2018/8/2.
 */
public class TestProtoBuf {

    public static void main(String args[])throws Exception{

        //机器A
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("汪朝晖")
                .setAddress("jiaxing")
                .setAge(20).build();

        //转换成字节数组
        byte[] student2ByteArray = student.toByteArray();
        /**
         * 网络传输
         * */
        //机器B
        DataInfo.Student student1 = DataInfo.Student.parseFrom(student2ByteArray);
        out.println(student1);
        out.println(student1.getName());
        out.println(student1.getAddress());
        out.println(student1.getAge());

    }
}
