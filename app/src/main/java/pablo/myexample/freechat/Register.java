package pablo.myexample.freechat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
    }

    public void Cancel(View view){
        finish();
    }

    public void Register(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
