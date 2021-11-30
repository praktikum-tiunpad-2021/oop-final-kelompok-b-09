package MySudoku.userinterface;

import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;
import java.util.regex.*;

public class SudokuTextField extends TextField{
    private final int x;
    private final int y;

    public SudokuTextField(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public void replaceText(int i, int i1, String s) {
        if(!Pattern.matches("[0-9]", s)){
            super.replaceText(i, i1, s);
        }
    }

    @Override
    public void replaceSelection(String s) {
        if(!Pattern.matches("[0-9]", s)){
            super.replaceSelection(s);
        }
    }
}
