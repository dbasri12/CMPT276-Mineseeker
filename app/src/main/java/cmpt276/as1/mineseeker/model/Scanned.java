//To keep track of scanned button, so we can update every time the user scan
package cmpt276.as1.mineseeker.model;

public class Scanned {
    private int rowS;
    private int colS;

    public Scanned(int row,int col){
        rowS=row;
        colS=col;
    }
    public int getColS() {
        return colS;
    }
    public int getRowS(){
        return rowS;
    }
}
