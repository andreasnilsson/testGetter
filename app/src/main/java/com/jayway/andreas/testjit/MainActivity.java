package com.jayway.andreas.testjit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends ActionBarActivity {

    private static final boolean USE_ARRAY = true;
    private TextView mResultText;

    public enum AccessType {
        DIRECT, GETTER, GETTER_FINAL
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
                v.setEnabled(false);
                startTest(AccessType.DIRECT);
                v.setEnabled(true);
            }
        });

        findViewById(R.id.startGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                startTest(AccessType.GETTER);
                v.setEnabled(true);
            }
        });

        findViewById(R.id.startGetFinal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);
                startTest(AccessType.GETTER_FINAL);
                v.setEnabled(true);
            }
        });

    }

    private void startTest(AccessType accessType) {
        MyObject localData = new MyObject(new Random().nextInt());
        String result = "[";
        MyObject[] data = MyObject.createTestData(DATA_SIZE);

        int sum = 0;
        long start = System.nanoTime();
        for (int i = 0; i < N_LOOPS; i++) {
            for (int j = 0; j < DATA_SIZE; j++) {

                if (USE_ARRAY) {
                    switch (accessType) {
                        case DIRECT:
                            sum += data[i].data;
                            break;
                        case GETTER:
                            sum += data[i].getData();
                            break;
                        case GETTER_FINAL:
                            sum += data[i].getDataFinal();
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
                        case GETTER_FINAL:
                            sum += localData.getDataFinal();
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
