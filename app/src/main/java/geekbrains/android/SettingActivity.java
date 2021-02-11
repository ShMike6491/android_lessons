package geekbrains.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.radiobutton.MaterialRadioButton;

public class SettingActivity extends AppCompatActivity {

    private static final String NameSharedPreference = "LOGIN";

    private static final String AppTheme = "APP_THEME";

    private static final int LIGHT_THEME = 0;
    private static final int GREEN_THEME = 1;
    private static final int PINK_THEME = 2;
    private static final int DARK_THEME = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(R.style.DarkTheme));
        setContentView(R.layout.activity_setting);

        initThemeChooser();
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.rb_light),
                LIGHT_THEME);
        initRadioButton(findViewById(R.id.rb_dark),
                DARK_THEME);
        initRadioButton(findViewById(R.id.rb_green),
                GREEN_THEME);
        initRadioButton(findViewById(R.id.rb_pink),
                PINK_THEME);
        RadioGroup rg = findViewById(R.id.radioButtons);
        ((MaterialRadioButton)rg.getChildAt(getCodeStyle(LIGHT_THEME))).setChecked(true);
    }

    private void initRadioButton(View button, final int codeStyle){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAppTheme(codeStyle);
                recreate();
            }
        });
    }

    private int getAppTheme(int codeStyle) {
        return codeStyleToStyleId(getCodeStyle(codeStyle));
    }

    private int getCodeStyle(int codeStyle){
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        return sharedPref.getInt(AppTheme, codeStyle);
    }

    private void setAppTheme(int codeStyle) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(AppTheme, codeStyle);
        editor.apply();
    }

    private int codeStyleToStyleId(int codeStyle){
        switch(codeStyle){
            case PINK_THEME:
                return R.style.PinkTheme;
            case GREEN_THEME:
                return R.style.GreenTheme;
            case DARK_THEME:
                return R.style.DarkTheme;
            default:
                return R.style.LightTheme;
        }
    }
}
