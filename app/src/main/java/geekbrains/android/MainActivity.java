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

    private CalculatorServiceImp calculator;

    private boolean newLine;

    private TextView display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculator = new CalculatorServiceImp();
        initView();
    }

    private void initView() {
        display = findViewById(R.id.tv_display);

        initButtons();
    }

    private void initButtons() {
        int[] buttonIds = {R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
                R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7, R.id.btn_8, R.id.btn_9,
                R.id.btn_dot, R.id.btn_clear, R.id.btn_remove, R.id.btn_equals, R.id.btn_plus,
                R.id.btn_minus, R.id.btn_multi, R.id.btn_div};
        for (int buttonId : buttonIds) {
            Button btn = findViewById(buttonId);
            btn.setOnClickListener(this);
        }
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
