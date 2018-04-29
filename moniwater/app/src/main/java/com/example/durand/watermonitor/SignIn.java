package com.example.durand.watermonitor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.durand.watermonitor.Common.Common;
import com.example.durand.watermonitor.Database.Data;
import com.example.durand.watermonitor.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {
    EditText edtPhone,edtPassword;
    Button btnSignIn2;
    Data data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        edtPhone =(EditText)findViewById(R.id.edtPhone);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnSignIn2=(Button)findViewById(R.id.btnSignIn2);

        data = new Data(this);
        //Init Firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


         btnSignIn2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                 mDialog.setMessage("Please wait...");
                 mDialog.show();

                 table_user.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         //check if user  not exist in database
                         if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                             //Get user information
                             mDialog.dismiss();
                             User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                             user.setPhone(edtPhone.getText().toString());
                             if (user.getPassword().equals(edtPassword.getText().toString())) {
                               //  Toast.makeText(SignIn.this, "Sign in successfully ! ", Toast.LENGTH_SHORT).show();
                                 {
                                     String token = data.getToken();
                                     Log.d("Token", token);
                                     Intent home = new Intent(SignIn.this,Home.class);
                                     Bundle bundle = new Bundle();
                                     bundle.putString("token", token);
                                     bundle.putString("phone", user.getPhone());
                                     home.putExtras(bundle);
                                     Common.currentUser = user;
                                     startActivity(home);
                                     finish();
                                 }
                             } else {
                                 Toast.makeText(SignIn.this, "Wrong Password !!! ", Toast.LENGTH_SHORT).show();
                             }
                         }
                         else  {
                             mDialog.dismiss();
                             Toast.makeText(SignIn.this, "User Does'nt exists", Toast.LENGTH_SHORT).show();
                         }
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {

                     }
                 });
             }
         });
    }
}
