package com.beauty.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_signin)
    Button btn_signup;
    @BindView(R.id.et_email)
    TextInputEditText et_email;

    @BindView(R.id.et_password)
    TextInputEditText et_password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = et_email.getText().toString().trim();
                String Password = et_password.getText().toString();
                String DeviceType = "Android";  //
                String DeviceToken = "businesslist123";

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
                final AlertDialog dialog = new SpotsDialog.Builder().setContext(LoginActivity.this).setCancelable(false).setMessage("Please wait....").build();
                dialog.show();
                final Call<UserResponse> userResponseCall = NetworkClient.getInstance().getServiceApin()
                        .login( Email, Password, DeviceType, DeviceToken);
                userResponseCall.enqueue(new Callback<UserResponse>() {
                    @Override
                    public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                        dialog.dismiss();
                        UserResponse userResponse = response.body();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        Toast.makeText(getApplicationContext(), userResponse.getStatus(), Toast.LENGTH_SHORT).show();


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
