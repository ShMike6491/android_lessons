package geekbrains.android;

import android.os.Parcel;
import android.os.Parcelable;

class CalculatorServiceImp implements CalculatorService, Parcelable{
    private double x;
    private double y;
    private double result;
    private char operator;
    private boolean isOperatorClicked;

    private double screenSaver;

    CalculatorServiceImp() {
        reset();
    }

    protected CalculatorServiceImp(Parcel in) {
        x = in.readDouble();
        y = in.readDouble();
        result = in.readDouble();
        operator = (char) in.readInt();
        isOperatorClicked = in.readByte() != 0;
        screenSaver = in.readDouble();
    }

    public static final Creator<CalculatorServiceImp> CREATOR = new Creator<CalculatorServiceImp>() {
        @Override
        public CalculatorServiceImp createFromParcel(Parcel in) {
            return new CalculatorServiceImp(in);
        }

        @Override
        public CalculatorServiceImp[] newArray(int size) {
            return new CalculatorServiceImp[size];
        }
    };

    @Override
    public double operate(String number, char operator) {
        double num = Double.parseDouble(number);

        if (operator == '=') {
            if (isOperatorClicked) {
                y = num;
                calculate();
            } else {
                result = num;
            }
        } else {
            if (isOperatorClicked) {
                y = num;
                calculate();
                this.operator = operator;
                x = result;
                y = 0;
                result = 0;
                return x;
            } else {
                x = num;
                this.operator = operator;
                isOperatorClicked = true;
            }
        }
        return result;
    }

    private void calculate() {
        switch(operator) {
            case('+'):
                result = x + y;
                break;
            case('-'):
                result = x - y;
                break;
            case('*'):
                result = x * y;
                break;
            case('/'):
                if(y!=0)
                    result = x / y;
                break;
        }
        x = 0;
        y = 0;
    }

    void reset() {
        x = 0;
        y = 0;
        result = 0;
        operator = ' ';
        isOperatorClicked = false;
    }

    public boolean errorPrevent (String num) {
        double number = Double.parseDouble(num);
        return operator == '/' && number == 0;
    }

    public double getScreenSaver() {
        return screenSaver;
    }

    public void setScreenSaver (String num) {
        screenSaver = Double.parseDouble(num);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(x);
        dest.writeDouble(y);
        dest.writeDouble(result);
        dest.writeInt((int) operator);
        dest.writeByte((byte) (isOperatorClicked ? 1 : 0));
        dest.writeDouble(screenSaver);
    }
}
