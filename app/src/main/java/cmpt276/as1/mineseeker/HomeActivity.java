//the Main menu page
//Allow user to go to either play game, options or help
package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import cmpt276.as1.mineseeker.model.GameLogic;

public class HomeActivity extends AppCompatActivity {
    private static final String EXTRA_ROW="the row";
    private static final String EXTRA_COLUMN="the column";
    private static final String EXTRA_MINES="the mines";
    private GameLogic gameLogic=GameLogic.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        setupOptionsButton();
        setupGameButton();
        setupHelpButton();
        refresh();
    }
    public static Intent makeIntent(Context context){
        return new Intent(context,HomeActivity.class);
    }
    private void setupOptionsButton() {
        Button optionsBtn=findViewById(R.id.buttonOpt);
        optionsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=OptionActivity.makeIntent(HomeActivity.this);
                startActivity(intent);
            }
        });
    }
    private void setupGameButton() {
        Button gameBtn=findViewById(R.id.buttonPlay);
        gameBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=GameActivity.makeIntent(HomeActivity.this,gameLogic);
                startActivity(intent);
            }
        });
    }
    private void setupHelpButton(){
        Button helpBtn=(Button)findViewById((R.id.buttonHelp));
        helpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=HelpActivtiy.makeIntent(HomeActivity.this);
                startActivity(intent);
            }
        });
    }
    private void refresh(){
        int mines=OptionActivity.getNumberMines(this);
        int rows=OptionActivity.getBoardSize(this);
        int cols;
        if(rows==4){
            cols=6;
        }
        else if(rows==5){
            cols=10;
        }
        else{
            cols=15;
        }
        gameLogic=new GameLogic(rows,cols,mines);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}