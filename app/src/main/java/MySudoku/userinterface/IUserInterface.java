package MySudoku.userinterface;

import MySudoku.problemdomain.SudokuGame;

public interface IUserInterface {
  interface EventListener {
    void onSudokuInput(int x, int y, int input);
    void onButtonClick();
  }

  interface View {
    void setListener(IUserInterface.EventListener listener);
    void updateSquare(int x, int y, int input);
    void updateBoard(SudokuGame game);
    void showDialog(String message);
    void showError(String message);
  }
}
