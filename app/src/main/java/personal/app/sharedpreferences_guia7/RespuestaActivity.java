package personal.app.sharedpreferences_guia7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class RespuestaActivity extends AppCompatActivity {

    TextView txtAnswer;
    public static SharedPreferences sharedPreferences;
    public static String NAME_FILE = "configuration";
    int n = 10; //  n es el número hasta que quieres que llegue (1-10)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_respuesta);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        txtAnswer = (TextView) findViewById(R.id.txtAnswer);

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


        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        String answer = sharedPreferences.getString("ANSWER", "");

        txtAnswer.setText(answer);
    }

    @Override
    protected void onPause() {
        super.onPause();

        sharedPreferences = getSharedPreferences(NAME_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editorConfig = sharedPreferences.edit();

        editorConfig.apply();
    }

}