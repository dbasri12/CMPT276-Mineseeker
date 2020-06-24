//the splash screen, or welcome screen class
//for the splash screen when user open the game, can choose to skip it or not
package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private String TAG="Orientation";
    private static int SPLASH_TIME_OUT=6000;
    Animation topAnim,bottomAnim;
    ImageView astronaut;
    TextView title,subtitle;
    Intent homeIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topAnim= AnimationUtils.loadAnimation(this,R.anim.top_anim);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_anim);

        title=findViewById(R.id.textTitle);
        subtitle=findViewById(R.id.textView3);
        astronaut=findViewById(R.id.imageAst);


        title.setAnimation(bottomAnim);
        subtitle.setAnimation(bottomAnim);
        astronaut.setAnimation(topAnim);


        setupSkipButton();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(homeIntent!=null)
                    finish();
                else{
                homeIntent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(homeIntent);
                finish();}
            }
        },SPLASH_TIME_OUT);


        Log.e(TAG,"Running onCreate()!");
    }
    private void setupSkipButton () {
        Button skipBtn=findViewById(R.id.buttonSkip);
        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIntent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }
}