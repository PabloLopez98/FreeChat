package pablo.myexample.freechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Register extends AppCompatActivity {

    private ImageView profileImage;
    private EditText email, name, nickname, password;
    private FirebaseAuth firebaseAuth;
    private Uri profileUri;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");

        profileImage = findViewById(R.id.profileImage);
        email = findViewById(R.id.inputEmail);
        name = findViewById(R.id.inputUserName);
        nickname = findViewById(R.id.inputNickname);
        password = findViewById(R.id.inputPassword);

        firebaseAuth = FirebaseAuth.getInstance();

    }

    public void Cancel(View view) {
        finish();
    }

    public void Register(View view) {

        if (name.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Input Name", Toast.LENGTH_SHORT).show();
        } else if (email.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Input Email", Toast.LENGTH_SHORT).show();
        } else if (nickname.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Input Nickname", Toast.LENGTH_SHORT).show();
        } else if (password.getText().toString().matches("")) {
            Toast.makeText(getApplicationContext(), "Input Password", Toast.LENGTH_SHORT).show();
        } else if (profileUri == null) {
            Toast.makeText(getApplicationContext(), "Input Profile Image", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        /*
                        set values in firebase database
                         */

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    public void openFileChooser(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            profileUri = data.getData();
            Picasso.with(this).load(profileUri).fit().into(profileImage);

        }
    }
}
