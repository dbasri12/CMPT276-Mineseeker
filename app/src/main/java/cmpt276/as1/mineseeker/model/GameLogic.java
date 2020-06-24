//the game logic. Keep rows, columns, and number of mines. each button represented as a 2d array/
package cmpt276.as1.mineseeker.model;

import java.util.Random;

public class GameLogic {
    private int NUM_ROWS;
    private int NUM_COLUMNS;
    private int mines;
    private int [][] gameBoard;
    private static GameLogic instance;
    public static GameLogic getInstance(){
        if(instance==null){
            instance=new GameLogic();
        }
        return instance;
    }
    public GameLogic(){
    }
    public GameLogic(int rows,int columns,int mines){
        this.NUM_COLUMNS=columns;
        this.NUM_ROWS=rows;
        this.mines=mines;
        gameBoard=new int[rows][columns];
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                gameBoard[i][j]=1;
            }
        }
        int rowM,colM;
        Random rand=new Random();
        int countMines=mines;
        while(countMines!=0){
            rowM=rand.nextInt(rows);
            colM=rand.nextInt(columns);
            if(gameBoard[rowM][colM]==1){
                gameBoard[rowM][colM]=0;
                countMines--;}
            else
                continue;
        }
    }

    public int scanMine(int row,int col){
        int count=0;
        for(int i=0;i<this.getNUM_COLUMNS();i++){
            if(gameBoard[row][i]==0){
                count++;
            }
        }
        for(int i=0;i<this.getNUM_ROWS();i++){
            if(gameBoard[i][col]==0){
                count++;
            }
        }
        return count;
    };

    public int getGameBoard(int row,int column) {
        return gameBoard[row][column];
    }

    public int getNUM_ROWS() {
        return NUM_ROWS;
    }

    public int getNUM_COLUMNS() {
        return NUM_COLUMNS;
    }
    public void setGameBoard(int row,int column){
        gameBoard[row][column]=1;
    }

    public int getMines() {
        return mines;
    }
}
