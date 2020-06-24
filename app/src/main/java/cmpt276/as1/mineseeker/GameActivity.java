package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import cmpt276.as1.mineseeker.model.GameLogic;
import cmpt276.as1.mineseeker.model.Scanned;

public class GameActivity extends AppCompatActivity {

    private static DecimalFormat df2=new DecimalFormat("#");
    private static final String EXTRA_ROW="the row";
    private static final String EXTRA_COLUMN="the column";
    private static final String EXTRA_MINES="the mines";
    private static final String SHARED_P="shared preferences";
    private static final String task_list="task list";


    private GameLogic player;
    private int numberGamesCounter;

    private ArrayList<Integer> highest=new ArrayList<Integer>(12);
    private ArrayList<Scanned> scanned=new ArrayList<Scanned>();
    private int index;
    private int rowForHigh;
    private int colForHigh;

    Button buttons[][];

    int mineCounter=0;
    int scanCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);
        checkIntentDelete();
        extractDataFromIntent();
        //populateHighest();
        highest=getArrayPrefs(this);
        numberGamesCounter=GameActivity.getGameNumber(this);
        TextView textFoundMine=(TextView)findViewById(R.id.textFound);
        textFoundMine.setText("Found " +mineCounter+ " of "+player.getMines()+" Earths.");
        TextView textTimesPlayed=(TextView)findViewById(R.id.textNumPlayed);
        textTimesPlayed.setText("Times Played : "+numberGamesCounter);
        if(highest.size()!=0){
        TextView textHighScore=(TextView)findViewById(R.id.textHighscore);
        textHighScore.setText("High Score : "+highest.get(index));}
        populateButtons();
    }
    private void populateHighest(){
        for(int i=0;i<12;i++){
            highest.add(-1);
        }
    }
    private void checkIntentDelete(){
        Intent intent=getIntent();
        if(intent.getIntExtra("Delete",0)==-1){
            highest.clear();
            setArrayPrefs(highest);
            numberGamesCounter=0;
            saveGameNumber(numberGamesCounter);
            intent=HomeActivity.makeIntent(GameActivity.this);
            startActivity(intent);
            finish();
        }
    }
    private void extractDataFromIntent() {
        Intent intent=getIntent();
        int rowI=intent.getIntExtra(EXTRA_ROW,4);
        int colI=intent.getIntExtra(EXTRA_COLUMN,6);
        int minesI=intent.getIntExtra(EXTRA_MINES,6);
        player=new GameLogic(rowI,colI,minesI);
        buttons=new Button[rowI][colI];

        if(rowI==4 &&minesI==6){
            index=0;
        }
        else if(rowI==4&&minesI==10){
            index=1;
        }
        else if(rowI==4&&minesI==15){
            index=2;
        }
        else if(rowI==4&&minesI==20){
            index=3;
        }
        else if(rowI==5&&minesI==6){
            index=4;
        }
        else if(rowI==5&&minesI==10){
            index=5;
        }
        else if(rowI==5&&minesI==15){
            index=6;
        }
        else if(rowI==5&&minesI==20){
            index=7;
        }
        else if(rowI==6&&minesI==6){
            index=8;
        }
        else if(rowI==6&&minesI==10){
            index=9;
        }
        else if(rowI==6&&minesI==15){
            index=10;
        }
        else{
            index=11;
        }
    }
    public static Intent makeIntent(Context context,GameLogic gameLogic){
        Intent intent=new Intent(context,GameActivity.class);
        intent.putExtra(EXTRA_ROW,gameLogic.getNUM_ROWS());
        intent.putExtra(EXTRA_COLUMN,gameLogic.getNUM_COLUMNS());
        intent.putExtra(EXTRA_MINES,gameLogic.getMines());
        return intent;
    }
    public static Intent makeIntent2(Context context){
        Intent intent=new Intent(context,GameActivity.class);
        intent.putExtra("Delete",-1);
        return intent;
    }
    private void populateButtons(){
        TableLayout table=(TableLayout)findViewById(R.id.table_button);
        for(int row=0;row< player.getNUM_ROWS();row++) {
            TableRow tableRow=new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);
            for (int col = 0; col < player.getNUM_COLUMNS(); col++) {
                final int FINAL_COL=col;
                final int FINAL_ROW=row;
                Button button=new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f));

                Bitmap originalBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.resize_btngalaxy);
                Resources resource=getResources();
                button.setBackground(new BitmapDrawable(resource,originalBitmap));
                button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        gridButtonClicked(FINAL_ROW,FINAL_COL);
                        }

                });
                tableRow.addView(button);
                buttons[row][col]=button;
            }
        }
    }
    private void gridButtonClicked(int row,int col){
        if(player.getGameBoard(row,col)==0) {
            mineCounter+=1;
            TextView textFoundMine=(TextView)findViewById(R.id.textFound);
            textFoundMine.setText("Found " +mineCounter+ " of "+player.getMines()+" Earths.");
            Button button=buttons[row][col];
            lockButtonSizes();
            int newWidth=button.getWidth();
            int newHeight=button.getHeight();
            Bitmap originalBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.earth3);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(originalBitmap,newWidth,newHeight,true);
            Resources resource=getResources();
            button.setBackground(new BitmapDrawable(resource,scaledBitmap));
            player.setGameBoard(row,col);
            refreshGameBoard();
            if(mineCounter==player.getMines()){
                if(highest.get(index)==0){
                    highest.set(index,scanCounter);
                    setArrayPrefs(highest);
                }
                else if(scanCounter<highest.get(index)){
                    highest.set(index,scanCounter);
                    setArrayPrefs(highest);
                }
                numberGamesCounter+=1;
                saveGameNumber(numberGamesCounter);
                Intent intent=popCongrats.makeIntent(GameActivity.this);
                startActivity(intent);
            }

        }
        else{
            if(mineCounter==player.getMines()){
                Intent intent=popCongrats.makeIntent(GameActivity.this);
                startActivity(intent);
            }
            else{
           scanForMine(row,col);
           scanCounter+=1;
           Scanned temp=new Scanned(row,col);
           scanned.add(temp);
            TextView textScanUsed=(TextView)findViewById(R.id.textScanUsed);
            textScanUsed.setText("# Scans used: " +scanCounter);}
        }

    }
    private void refreshGameBoard(){
        for(int i=0;i<scanned.size();i++){
            scanForMine(scanned.get(i).getRowS(),scanned.get(i).getColS());
        }
    }
    private void scanForMine(int row,int col){
        Button button=buttons[row][col];
        lockButtonSizes();
        button.setText(df2.format(player.scanMine(row,col)));
    }
    private void lockButtonSizes(){
        for(int row=0;row<player.getNUM_ROWS();row++){
            for(int col=0;col<player.getNUM_COLUMNS();col++){
                Button button=buttons[row][col];
                int width=button.getWidth();
                button.setMinWidth(width);
                button.setMaxWidth(width);

                int height=button.getHeight();
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }
    private void saveGameNumber(int gameNumber){
        SharedPreferences prefs=this.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt("Times Played",gameNumber);
        editor.apply();
    }
    static public int getGameNumber(Context context){
        SharedPreferences prefs=context.getSharedPreferences("AppPrefs",MODE_PRIVATE);
        return prefs.getInt("Times Played",0);
    }
    private void setArrayPrefs(ArrayList<Integer> array) {
        SharedPreferences prefs = this.getSharedPreferences(SHARED_P, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        //editor.putInt("_size", array.size());
        if(array.size()==0){
            for(int i=0;i<12;i++)
                editor.putInt("_"+i,0);
        }
        else{for(int i=0;i<12;i++)//array.size();i++)
            editor.putInt("_" + i, array.get(i));}
        editor.apply();
    }
    static public ArrayList<Integer> getArrayPrefs(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences(SHARED_P, MODE_PRIVATE);
        int size = 12;//prefs.getInt("_size", 12);
        ArrayList<Integer> array = new ArrayList<>(size);
        for(int i=0;i<size;i++)
            array.add(prefs.getInt("_" + i, 0));
        return array;
    }
}