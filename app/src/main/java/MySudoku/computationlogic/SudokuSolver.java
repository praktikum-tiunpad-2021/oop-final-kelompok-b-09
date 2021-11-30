package MySudoku.computationlogic;

import MySudoku.konstanta.GameState;
import MySudoku.problemdomain.Coordinates;
import MySudoku.problemdomain.SudokuGame;

import static MySudoku.problemdomain.SudokuGame.BATAS_GRID;

public class SudokuSolver {
    public static boolean puzzleIsSolvable(int[][] puzzle, int jumlah) {

        //step 1:
        Coordinates[] emptyCells = typeWriterEnumerate(puzzle, jumlah);

        int index = 0;
        int input = 1;
        while (index < 40) {
            Coordinates current = emptyCells[index];
            input = 1;
            while (input < 10) {
                puzzle[current.getX()][current.getY()] = input;
                if (GameLogic.MySudokuIsInvalid(puzzle)) {
                    if (index == 0 && input == BATAS_GRID) {
                        return false;
                    } else if (input == BATAS_GRID) {
                        index--;
                    }

                    input++;
                } else {
                    index++;

                    if (index == (40-1)) {
                        return true;
                    }

                    //break loop
                    input = 10;
                }
            }
        }

        return false;
    }

    private static Coordinates[] typeWriterEnumerate(int[][] puzzle, int jumlah) {
        Coordinates[] emptyCells = new Coordinates[jumlah];
        int iterator = 0;
        for (int y = 0; y < BATAS_GRID; y++) {
            for (int x = 0; x < BATAS_GRID; x++) {
                if (puzzle[x][y] == 0) {
                    emptyCells[iterator] = new Coordinates(x, y);
                    if (iterator == (jumlah-1)) return emptyCells;
                    iterator++;
                }
            }
        }
        return emptyCells;
    }


}