//Option activity class
//To change board size, number of mines, and can erase the history saved(times played, and high score for each configuration
package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import cmpt276.as1.mineseeker.model.Scanned;

public class OptionActivity extends AppCompatActivity {
    private ArrayList<Integer> highest=new ArrayList<Integer>();
    public static Intent makeIntent(Context context){
        return new Intent(context,OptionActivity.class);
    }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_option);
            createRadioButton();
            createRadioButton2();
            setupDeleteBtn();
        }

        private void createRadioButton() {
            RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_minesize);

            int[] numPanels = getResources().getIntArray(R.array.mine_num);
            for (int i = 0; i < numPanels.length; i++) {
                final int numPanel = numPanels[i];

                RadioButton button = new RadioButton(this);
                if(numPanel==6)
                    button.setText("0"+numPanel + " Earths  ");
                else
                    button.setText(numPanel + " Earths  ");
                int whiteColorValue= Color.WHITE;
                int blackColorValue=Color.BLACK;
                button.setTextColor(blackColorValue);
                button.setBackgroundColor(whiteColorValue);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveNumberMines(numPanel);
                    }
                });

                group.addView(button);
                if (numPanel == getNumberMines(this)) {
                    button.setChecked(true);
                }
            }
        }
        private void saveNumberMines(int numMine){
            SharedPreferences prefs=this.getSharedPreferences("AppPrefs",MODE_PRIVATE);
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt("Num of Mines",numMine);
            editor.apply();
        }
        static public int getNumberMines(Context context){
        SharedPreferences prefs=context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        int defaultValue=context.getResources().getInteger(R.integer.default_mine);
        return prefs.getInt("Num of Mines",defaultValue);
        }
    private void createRadioButton2() {
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_board);

        int[] numPanels = getResources().getIntArray(R.array.board_num);
        for (int i = 0; i < numPanels.length; i++) {
            final int numPanel = numPanels[i];

            RadioButton button = new RadioButton(this);
            if(numPanel==4){
                button.setText(numPanel+" x 06  ");
            }
            else if(numPanel==5){
                button.setText(numPanel+" x 10  ");
            }
            else{
                button.setText(numPanel+" x 15  ");}
            int whiteColorValue= Color.WHITE;
            button.setTextColor(Color.BLACK);
            button.setBackgroundColor(whiteColorValue);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveBoardSize(numPanel);
                }
            });

            group.addView(button);
            if (numPanel == getBoardSize(this)) {
                button.setChecked(true);
            }
        }
    }
    private void saveBoardSize(int boardSize){
        SharedPreferences prefs=this.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("Board Size",boardSize);
        editor.apply();
    }
    static public int getBoardSize(Context context){
        SharedPreferences prefs=context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        int defaultValue=context.getResources().getInteger(R.integer.default_board_size);
        return prefs.getInt("Board Size",defaultValue);
    }
    private void setupDeleteBtn(){
        Button delBtn=(Button)findViewById(R.id.btnEraseHist);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=GameActivity.makeIntent2(OptionActivity.this);
                startActivity(intent);
                finish();
            }
        });
    }

}