package MySudoku.problemdomain;

import MySudoku.computationlogic.SudokuUtilities;
import MySudoku.konstanta.GameState;
import java.io.Serializable;

import java.util.List;

public class SudokuGame implements Serializable {
  private final GameState gameState;
  private final int[][] gridState;

  public static String difficulty;
  public static final int BATAS_GRID = 9;

  public SudokuGame(GameState gameState, int[][] gridState) {
    this.gameState = gameState;
    this.gridState = gridState;
  }

  public GameState getGameState() {
    return gameState;
  }

  public int[][] getCopyOfGridState() {
    return SudokuUtilities.copyToNewArray(gridState);
  }
}