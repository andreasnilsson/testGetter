package com.jayway.andreas.testjit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private static final boolean USE_ARRAY = false;
    private TextView mResultText;

    public enum AccessType {
        DIRECT, GETTER
    }

    private static final int DATA_SIZE = Integer.MAX_VALUE / 1024;
    private static final int N_LOOPS = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultText = (TextView) findViewById(R.id.result);

        findViewById(R.id.startDirect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest(AccessType.DIRECT);
            }
        });

        findViewById(R.id.startGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTest(AccessType.GETTER);
            }
        });

    }

    private void startTest(AccessType accessType) {
        MyObject localData = new MyObject(new Random().nextInt());
        String result = "[";
        MyObject[] data = MyObject.createTestData(DATA_SIZE);

        for (int i = 0; i < N_LOOPS; i++) {
            int sum = 0;
            long start = System.nanoTime();
            for (int j = 0; j < DATA_SIZE; j++) {

                if (USE_ARRAY) {
                    switch (accessType) {
                        case DIRECT:
                            sum += data[i].data;
                            break;
                        case GETTER:
                            sum += data[i].getData();
                            break;
                    }
                } else {
                    switch (accessType) {
                        case DIRECT:
                            sum += localData.data;
                            break;
                        case GETTER:
                            sum += localData.getData();
                            break;
                    }
                }


            }

            long dt = System.nanoTime() - start;
            mResultText.append("Test " + (i + 1) + "Time: " + dt + " ns\n");

            if (i != N_LOOPS - 1) {
                result += dt + ", ";
            } else {
                result += dt + "]";
            }

            System.out.println("sum: " + sum);
        }


        System.out.println(result);
    }
}