package cmpt276.as1.mineseeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import cmpt276.as1.mineseeker.model.GameLogic;

public class GameActivity extends AppCompatActivity {

    private static DecimalFormat df2=new DecimalFormat("#");
    private static final String EXTRA_ROW="the row";
    private static final String EXTRA_COLUMN="the column";
    private static final String EXTRA_MINES="the mines";

    //public static final int NUM_ROWS = 4;
    //public static final int NUM_COLS=6;
    //public GameLogic player=new GameLogic(NUM_ROWS,NUM_COLS,4);
    private GameLogic player;

    Button buttons[][];
    //int[] keepRow=new int[player.getNUM_ROWS()];
    //int[] keepCol=new int[player.getNUM_COLUMNS()];
    int mineCounter=0;
    int scanCounter=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        extractDataFromIntent();
        TextView textFoundMine=(TextView)findViewById(R.id.textFound);
        textFoundMine.setText("Found " +mineCounter+ " of "+player.getMines()+" mines.");
        populateButtons();
    }
    private void extractDataFromIntent() {
        Intent intent=getIntent();
        int rowI=intent.getIntExtra(EXTRA_ROW,4);
        int colI=intent.getIntExtra(EXTRA_COLUMN,6);
        int minesI=intent.getIntExtra(EXTRA_MINES,6);
        player=new GameLogic(rowI,colI,minesI);
        buttons=new Button[rowI][colI];
    }
    public static Intent makeIntent(Context context,GameLogic gameLogic){
        Intent intent=new Intent(context,GameActivity.class);
        intent.putExtra(EXTRA_ROW,gameLogic.getNUM_ROWS());
        intent.putExtra(EXTRA_COLUMN,gameLogic.getNUM_COLUMNS());
        intent.putExtra(EXTRA_MINES,gameLogic.getMines());
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
            textFoundMine.setText("Found " +mineCounter+ " of "+player.getMines()+" mines.");
            Button button=buttons[row][col];
            lockButtonSizes();
            int newWidth=button.getWidth();
            int newHeight=button.getHeight();
            Bitmap originalBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.crystal_clear_app_linneighborhood);
            Bitmap scaledBitmap=Bitmap.createScaledBitmap(originalBitmap,newWidth,newHeight,true);
            Resources resource=getResources();
            button.setBackground(new BitmapDrawable(resource,scaledBitmap));
            player.setGameBoard(row,col);
            if(mineCounter==player.getMines()){
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
            TextView textScanUsed=(TextView)findViewById(R.id.textScanUsed);
            textScanUsed.setText("# Scans used: " +scanCounter);}
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
}