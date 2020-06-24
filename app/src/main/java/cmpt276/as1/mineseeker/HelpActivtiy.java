//Help activity. For the Help screen
//Consists of about the author, how to play the game, and link to resources used in this game.
package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.WindowManager;
import android.widget.TextView;

public class HelpActivtiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_help_activtiy);
        TextView textSFU=(TextView)findViewById(R.id.textSFUWEb);
        textSFU.setMovementMethod(LinkMovementMethod.getInstance());
        TextView textPic=(TextView)findViewById(R.id.textView12);
        textPic.setMovementMethod(LinkMovementMethod.getInstance());
        TextView textBack=(TextView)findViewById(R.id.textbackweb);
        textBack.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public static Intent makeIntent(Context context){
        return new Intent(context,HelpActivtiy.class);
    }
}