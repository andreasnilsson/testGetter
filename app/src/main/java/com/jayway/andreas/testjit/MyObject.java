package com.jayway.andreas.testjit;

import java.util.Random;

public class MyObject {
    static Random sRandom = new Random();
    int data;

    public MyObject(int data) {
        this.data = data;
    }



    public int getData() {
        return data;
    }

    public static MyObject[] createTestData(int nObjects) {
        MyObject[] list = new MyObject[nObjects];
        for (int i = 0; i < nObjects; i++) {
            list[i] = new MyObject(sRandom.nextInt());
        }
        return list;
    }
}
