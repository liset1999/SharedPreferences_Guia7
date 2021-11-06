package personal.app.sharedpreferences_guia7;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import personal.app.sharedpreferences_guia7.utils.Utils;

public class JugarActivity extends AppCompatActivity {

    TextView txtUser, txtAttempts, txtAnswer;
    TextInputEditText edtEnter;
    MaterialButton btnDone;
    public static SharedPreferences sharedPreferences;
    public static String NAME_FILE = "configuration";
    Integer attempts = 10;
    boolean esIgual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txtUser = (TextView) findViewById(R.id.txtUser);
        txtAttempts = (TextView) findViewById(R.id.txtAttempts);
        txtAnswer = (TextView) findViewById(R.id.txtAnswer);
        edtEnter = (TextInputEditText) findViewById(R.id.edtEnter);
        btnDone = (MaterialButton) findViewById(R.id.btnDone);

        txtAttempts.setText("Numero de intentos permitidos: " + attempts);

        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        String user = sharedPreferences.getString("USER", "");

        if ( user == null){
            Intent intent = new Intent(JugarActivity.this, LoginActivity.class);
            startActivity(intent); // Inicia Activity
            Toast.makeText(JugarActivity.this, "Login first.",Toast.LENGTH_SHORT).show();

        }else{
            txtUser.setText("User: " + user);
        }

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
    }

    private void done(){
        if(!isValid(edtEnter.getText() ) ){
            edtEnter.setError("Required");
        }else{
            if(Utils.verifyEditTextNumber(edtEnter) ){
                sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
                SharedPreferences.Editor editorConfig = sharedPreferences.edit();
                editorConfig.putString("ENTER", edtEnter.getText().toString() );
                editorConfig.commit();

                String enter = sharedPreferences.getString("ENTER", "");
                String answer = sharedPreferences.getString("ANSWER", "");

                Integer in = Integer.parseInt(enter);
                Integer ans = Integer.parseInt(answer);


                if ( enter.equals(answer)  ){
                    Intent intent = new Intent(JugarActivity.this, MainActivity.class);
                    startActivity(intent); // Inicia Activity
                    String user = sharedPreferences.getString("USER", "");
                    Toast.makeText(
                           JugarActivity.this,
                            "Felicidades " + user+", Adivinaste!",
                            Toast.LENGTH_SHORT
                    ).show();
                }else{
                    edtEnter.setText("");
                    if(in < ans){
                        Toast.makeText(
                                JugarActivity.this,
                                "El numero correcto es mayor",
                                Toast.LENGTH_SHORT
                        ).show();
                    }else if(in > ans){
                        Toast.makeText(
                                JugarActivity.this,
                                "el numero correcto es menor",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
                attempts = attempts - 1;
                txtAttempts.setText("Numero de intentos permitidos: " + attempts);

                editorConfig.putString("ATT", attempts.toString() );
                editorConfig.commit();
            }

        }
    }

    private boolean isValid(@Nullable Editable text) {
        return text != null;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();

        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editorConfig = sharedPreferences.edit();

        editorConfig.putString("ENTER", edtEnter.getText().toString() );
        editorConfig.putString("ATT", attempts.toString() );
        editorConfig.apply();
    }
}