package com.example.d.dinislamtestandroid;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;



public class ChoiceLang extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private TextView ru;
    private TextView en;
    private static int zamok;


    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.choice_lang);
        ru = (TextView) findViewById(R.id.id_ru);
        en = (TextView) findViewById(R.id.id_en);

        if (zamok == 0) {
            if (savedInstanceState != null) {
                ru.setText(savedInstanceState.getString("ru"));
                en.setText(savedInstanceState.getString("en"));
            }
        }

        zamok = 0;


//        Сначала запоминаем выбранный язык в шаредПреференс, потом пересоздаем МейнАктивити, потом пересоздаем текущую активити
        ru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zamok = 1;
                sharedPreferences = getSharedPreferences("MyLang", MODE_PRIVATE);
                String thisLanf = getResources().getConfiguration().locale.toString();
                if (!thisLanf.startsWith("ru")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lang", "ru");
                    editor.apply();
                    SupportChoiceLang.changeLangRu();

                    recreate();
                }
            }
        });

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zamok = 1;
                sharedPreferences = getSharedPreferences("MyLang", MODE_PRIVATE);
                String thisLanf = getResources().getConfiguration().locale.toString();
                if (!thisLanf.startsWith("en")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("lang", "en");
                    editor.apply();

                    SupportChoiceLang.changeLang("en");
                    recreate();
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (zamok == 0) {
            outState.putString("ru", ru.getText().toString());
            outState.putString("en", en.getText().toString());
        }
    }
}
