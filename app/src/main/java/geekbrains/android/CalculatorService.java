package geekbrains.android;

public interface CalculatorService {
    double getScreenSaver();

    void setScreenSaver (String num);

    double operate(String number, char operator);

    boolean errorPrevent (String num);
}
