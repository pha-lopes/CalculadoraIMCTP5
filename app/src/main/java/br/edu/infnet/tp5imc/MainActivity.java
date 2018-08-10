package br.edu.infnet.tp5imc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText mEdtTxtPeso, mEdtTxtAltura;
    private TextView mTxtAlert;
    private Button mBtnCalcular;
    private TextView mTxtImcNum, mTxtImcText;

    private final String EMPTY_FIELD = "Campos não podem estar em branco.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdtTxtPeso = findViewById(R.id.edit_text_peso);
        mEdtTxtAltura = findViewById(R.id.edit_text_altura);
        mTxtAlert = findViewById(R.id.text_alert);

        mBtnCalcular = findViewById(R.id.btn_calcular);

        mTxtImcNum = findViewById(R.id.resultado_imc_num);
        mTxtImcText = findViewById(R.id.resultado_imc_text);

        mBtnCalcular.setOnClickListener(calcularIMC);

    }

    private View.OnClickListener calcularIMC = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            mTxtAlert.setText(null);

            hideKeyboard(v);

            float peso = 0f;
            float altura = 0f;
            boolean camposValidos;

            try{
                peso = Float.parseFloat(mEdtTxtPeso.getText().toString().trim());
                altura = Integer.parseInt(mEdtTxtAltura.getText().toString().trim());
                camposValidos = true;
            } catch (NumberFormatException ex){
                mTxtAlert.setText(EMPTY_FIELD);
                camposValidos = false;
            }

            if(camposValidos){
                altura = altura/100; //centimetros para metros
                float imc = peso / (altura * altura);
                String imcDesc = grauIMC(imc);

                mTxtImcNum.setText(String.format(Locale.US, "%.1f", imc));
                mTxtImcText.setText(imcDesc);

            }

        }
    };

    private String grauIMC(float imc){
        String imcResult = "";

        if(imc < 16){
            imcResult = "Magreza grave";
        } else if (imc == 16 || imc < 17){
            imcResult = "Magreza moderada";
        } else if (imc == 17 || imc < 18.5){
            imcResult = "Magreza leve";
        } else if (imc == 18.5 || imc < 25){
            imcResult = "Saudável";
        } else if (imc == 25 || imc < 30){
            imcResult = "Sobrepeso";
        } else if (imc == 30 || imc < 35){
            imcResult = "Obesidade Grau I";
        } else if (imc == 35 || imc < 40){
            imcResult = "Obesidade Grau II (severa)";
        } else if (imc >= 40){
            imcResult = "Obesidade Grau III (mórbida)";
        }

        return imcResult;
    }

    protected void hideKeyboard(View view)
    {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try{
            in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (NullPointerException ex){
            Log.e("FECHAR_TECLADO: ", ex.getMessage());
        }

    }
}
