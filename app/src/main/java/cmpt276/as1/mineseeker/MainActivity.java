package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private String TAG="Orientation";
    private static int SPLASH_TIME_OUT=6000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupSkipButton();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent homeIntent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);


        Log.e(TAG,"Running onCreate()!");
    }
    private void setupSkipButton () {
        Button skipBtn=findViewById(R.id.buttonSkip);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentHome=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intentHome);

            }
        });
        //finish();
    }
}