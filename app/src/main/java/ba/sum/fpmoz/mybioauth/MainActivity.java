package ba.sum.fpmoz.mybioauth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {

    private TextView authStatusTv;
    private Button authBtn;

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        authStatusTv=findViewById(R.id.authStatusTv);
        authBtn=findViewById(R.id.authBtn);

        executor= ContextCompat.getMainExecutor(this);
        biometricPrompt=new BiometricPrompt(  MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                authStatusTv.setText("Authentication error "+ errString);
                Toast.makeText(MainActivity.this,"Authentication error "+ errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                authStatusTv.setText("Authentication succeed");
                Toast.makeText(MainActivity.this,"Authentication succeed ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                authStatusTv.setText("Authentication failed");
                Toast.makeText(MainActivity.this,"Authentication failed",Toast.LENGTH_SHORT).show();
            }
        });
            promptInfo = new BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Auth")
            .setSubtitle("Login using fingerprint auth")
            .setNegativeButtonText("User app password")
            .build();
        authBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                biometricPrompt.authenticate(promptInfo);
            }
        });

    }
}