package com.lubotin.serega.fibonacciapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static final int RECURSIVE_METHOD = 1;
    public static final int MEMORY_METHOD = 2;
    public static final int BINE_METHOD = 3;

    private EditText input;
    private Button calculate;
    private TextView result;
    private TextView methodName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addButtonListener(0);
    }

    private void addButtonListener(int method) {
        calculate.setOnClickListener(v -> {
            if (method == 0) {
                Toast.makeText(this, R.string.toast_method_error, Toast.LENGTH_LONG).show();
                return;
            }

            long res = 0;
            int n = getCorrectInput();
            if (n == -1) {
                return;
            }

            switch (method) {
                case RECURSIVE_METHOD:
                    res = recursiveMethod(n);
                    break;
                case MEMORY_METHOD:
                    res = linearMethod(n);
                    break;
                case BINE_METHOD:
                    res = bineMethod(n);
                    break;
                default:
                    break;
            }
            result.setText(String.valueOf(res));
        });
    }

    public long recursiveMethod(int n) {
        if (n < 2) {
            return n;
        }
        return recursiveMethod(n - 1) + recursiveMethod(n - 2);
    }

    public long linearMethod(int n) {
        long fn2 = 0;
        long fn1 = 1;
        long fn = 0;

        if (n == 0) {
            return fn2;
        }

        if (n == 1) {
            return fn1;
        }

        for (int i = 1; i < n; i++) {
            fn = fn2 + fn1;
            fn2 = fn1;
            fn1 = fn;
        }
        return fn;
    }

    public long bineMethod(int n) {
        double index = Math.pow(5, 0.5);

        double left = (1 + index) / 2;
        double right = (1 - index) / 2;

        return Math.round((Math.pow(left, n) - Math.pow(right, n)) / index);
    }

    private int getCorrectInput() {
        int n;
        try {
            n = Integer.parseInt(input.getText().toString());
            if (n < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.toast_number_error, Toast.LENGTH_LONG).show();
            result.setText("Ошибка");
            n = -1;
        }
        return n;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calculating_methods, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int chosenMethod = 0;
        result.setText("");
        switch (item.getItemId()) {
            case R.id.item1:
                chosenMethod = RECURSIVE_METHOD;
                methodName.setText(getResources().getString(R.string.recursive));
                break;
            case R.id.item2:
                chosenMethod = MEMORY_METHOD;
                methodName.setText(getResources().getString(R.string.linear));
                break;
            case R.id.item3:
                chosenMethod = BINE_METHOD;
                methodName.setText(getResources().getString(R.string.bine));
                break;
        }
        addButtonListener(chosenMethod);
        return true;
    }

    private void init() {
        input = findViewById(R.id.input);
        calculate = findViewById(R.id.calculate);
        result = findViewById(R.id.result);
        methodName = findViewById(R.id.description);
    }


}
