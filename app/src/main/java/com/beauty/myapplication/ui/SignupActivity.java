package com.beauty.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beauty.myapplication.Model.UserResponse;
import com.beauty.myapplication.R;
import com.beauty.myapplication.api.NetworkClient;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    @BindView(R.id.btn_signup)
    Button btn_signup;
    @BindView(R.id.et_name)
    TextInputEditText et_name;

    @BindView(R.id.et_phone)
    TextInputEditText et_phone;

    @BindView(R.id.et_email)
    TextInputEditText et_email;

    @BindView(R.id.et_password)
    TextInputEditText et_password;
    @BindView(R.id.signin)
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        initView();
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void initView() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = et_name.getText().toString().trim();
                String Phone = et_phone.getText().toString().trim();
                String Email = et_email.getText().toString().trim();
                String Password = et_password.getText().toString();
                String DeviceType = "Android";  //
                String DeviceToken = "businesslist123";
                if (Name.isEmpty()) {
                    et_name.setError("Name is required");
                    et_name.requestFocus();
                    return;
                }
                if (Email.isEmpty()) {
                    et_email.setError("Email is required");
                    et_email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    et_email.setError("Enter a valid email");
                    et_email.requestFocus();
                    return;
                }
                if (Phone.isEmpty()) {
                    et_email.setError("Phone is required");
                    et_email.requestFocus();
                    return;
                }


                if (Password.isEmpty()) {
                    et_password.setError("Password required");
                    et_password.requestFocus();
                    return;
                }

                if (Password.length() < 6) {
                    et_password.setError("Password should be atleast 6 character long");
                    et_password.requestFocus();
                    return;
                }
                final AlertDialog dialog = new SpotsDialog.Builder().setContext(SignupActivity.this).setCancelable(false).setMessage("Please wait....").build();
                dialog.show();
                final Call<UserResponse> userResponseCall = NetworkClient.getInstance().getServiceApin()
                        .registerUser(Name, Phone, Email, Password, DeviceType, DeviceToken);
                userResponseCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        dialog.dismiss();
                        UserResponse userResponse = response.body();
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                        finish();
                        Toast.makeText(getApplicationContext(), userResponse.getMessage(), Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onFailure(Call<UserResponse> call, Throwable throwable) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }


}
