package pablo.myexample.freechat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Register extends AppCompatActivity {

    private ImageView profileImage;
    private EditText email, name, nickname, password;
    private Uri profileUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private String userId;

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
        storageReference = FirebaseStorage.getInstance().getReference("Uploads");

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

                        firebaseUser = firebaseAuth.getCurrentUser();
                        userId = firebaseUser.getUid();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("UserRooms").child(userId);

                        final StorageReference filereference = storageReference.child("images/").child(userId).child(System.currentTimeMillis() + "." + getFileExtension(profileUri));
                        filereference.putFile(profileUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return filereference.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {

                                    Uri downloadUri = task.getResult();

                                    User userObject = new User(userId, downloadUri.toString(), name.getText().toString(), nickname.getText().toString());
                                    databaseReference.setValue(userObject);

                                    finish();

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else {
                        Log.i("CreateError", task.getException().toString());
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

    public String getFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }
}

