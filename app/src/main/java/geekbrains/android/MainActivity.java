package geekbrains.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String KEY_CALCULATOR = "key_calculator";

    private Calculator calculator;

    private boolean newLine;

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculator = new Calculator();
        initView();
    }

    private void initView() {
        display = findViewById(R.id.tv_display);

        initBtn0();
        initBtn1();
        initBtn2();
        initBtn3();
        initBtn4();
        initBtn5();
        initBtn6();
        initBtn7();
        initBtn8();
        initBtn9();
        initBtnCancel();
        initBtnDiv();
        initBtnEquals();
        initBtnMinus();
        initBtnSum();
        initBtnRemove();
        initBtnDot();
        initBtnMulti();
    }

    private void initBtn0() {
        Button btn1 = findViewById(R.id.btn_0);
        btn1.setOnClickListener(this);
    }
    private void initBtn1() {
        Button btn1 = findViewById(R.id.btn_1);
        btn1.setOnClickListener(this);
    }
    private void initBtn2() {
        Button btn2 = findViewById(R.id.btn_2);
        btn2.setOnClickListener(this);
    }
    private void initBtn3() {
        Button btn3 = findViewById(R.id.btn_3);
        btn3.setOnClickListener(this);
    }
    private void initBtn4() {
        Button btn4 = findViewById(R.id.btn_4);
        btn4.setOnClickListener(this);
    }
    private void initBtn5() {
        Button btn5 = findViewById(R.id.btn_5);
        btn5.setOnClickListener(this);
    }
    private void initBtn6() {
        Button btn6 = findViewById(R.id.btn_6);
        btn6.setOnClickListener(this);
    }
    private void initBtn7() {
        Button btn7 = findViewById(R.id.btn_7);
        btn7.setOnClickListener(this);
    }
    private void initBtn8() {
        Button btn8 = findViewById(R.id.btn_8);
        btn8.setOnClickListener(this);
    }
    private void initBtn9() {
        Button btn9 = findViewById(R.id.btn_9);
        btn9.setOnClickListener(this);
    }
    private void initBtnDot() {
        Button btnDot = findViewById(R.id.btn_dot);
        btnDot.setOnClickListener(this);
    }
    private void initBtnCancel() {
        Button btnCancel = findViewById(R.id.btn_clear);
        btnCancel.setOnClickListener(this);
    }
    private void initBtnRemove() {
        Button btnRemove = findViewById(R.id.btn_remove);
        btnRemove.setOnClickListener(this);
    }
    private void initBtnEquals() {
        Button btnEquals = findViewById(R.id.btn_equals);
        btnEquals.setOnClickListener(this);
    }
    private void initBtnSum() {
        Button btnSum = findViewById(R.id.btn_plus);
        btnSum.setOnClickListener(this);
    }
    private void initBtnMinus() {
        Button btnMinus = findViewById(R.id.btn_minus);
        btnMinus.setOnClickListener(this);
    }
    private void initBtnMulti() {
        Button btnMulti = findViewById(R.id.btn_multi);
        btnMulti.setOnClickListener(this);
    }
    private void initBtnDiv() {
        Button btnDiv = findViewById(R.id.btn_div);
        btnDiv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                calculator.reset();
                displayClear();
                break;
            case R.id.btn_remove:
                displayRemove();
                break;
            case R.id.btn_equals:
                sendData('=');
                calculator.reset();
                break;
            case R.id.btn_plus:
                sendData('+');
                break;
            case R.id.btn_minus:
                sendData('-');
                break;
            case R.id.btn_multi:
                sendData('*');
                break;
            case R.id.btn_div:
                sendData('/');
                break;
            case R.id.btn_dot:
                String text = display.getText().toString();
                if (!text.contains("."))
                    displayAppend(v);
                break;
            default:
                displayAppend(v);
                break;
        }
    }

    @SuppressLint("DefaultLocale")
    private  void setDisplay(double number) {
        display.setText(String.format("%.2f", number));
        calculator.setScreenSaver(String.format("%f", number));
    }
    private void displayAppend(View v) {
        Button b = (Button) v;
        String text = b.getText().toString();
        displayAppend(text);
    }
    private void displayAppend(String text) {
        if (!newLine) {
            displayClear();
            newLine = true;
        }

        if (display.getText().toString().equals("0")) {
            display.setText(text);
        } else {
            display.append(text);
        }

        calculator.setScreenSaver(display.getText().toString());
    }
    private void displayClear() {
        display.setText("0");
        calculator.setScreenSaver("0");
    }
    private void displayRemove() {
        String text = display.getText().toString();
        if (text.length() == 1) {
            displayClear();
        } else {
            display.setText(text.substring(0, text.length() - 1));
        }
        calculator.setScreenSaver(display.getText().toString());
    }

    @SuppressLint("SetTextI18n")
    private void sendData(char operator) {
        String num = display.getText().toString();

        if(calculator.errorPrevent(num)) {
            display.setText("Error");
            calculator.reset();
            return;
        }

        double data = calculator.operate(num, operator);
        if(data != 0){
            setDisplay(data);
            newLine = false;
            return;
        }

        displayClear();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle instanceState) {
        super.onSaveInstanceState(instanceState);
        instanceState.putParcelable(KEY_CALCULATOR, calculator);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle instanceState) {
        super.onRestoreInstanceState(instanceState);
        calculator = instanceState.getParcelable(KEY_CALCULATOR);
        if (calculator != null) {
            setDisplay(calculator.getScreenSaver());
        }
    }
}
