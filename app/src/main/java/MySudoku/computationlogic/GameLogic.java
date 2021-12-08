package MySudoku.computationlogic;

import MySudoku.konstanta.GameState;
import MySudoku.konstanta.Rows;
import MySudoku.problemdomain.SudokuGame;

import java.util.*;

import static MySudoku.problemdomain.SudokuGame.BATAS_GRID;;

public class GameLogic {

    public static SudokuGame getNewGame() {
        return new SudokuGame(
                GameState.NEW,
                GameGenerator.getNewGameGrid()
        );
    }

    //Memeriksa kondisi game saat ini (aktif atau selesai)
    public static GameState checkForCompletion(int[][] grid) {
        if (MySudokuIsInvalid(grid)) return GameState.ACTIVE;
        if (tilesAreNotFilled(grid)) return GameState.ACTIVE;
        return GameState.COMPLETE;
    }

    //Memeriksa apabila masih terdapat petak kosong
    public static boolean tilesAreNotFilled(int[][] grid) {
        for (int xIndex = 0; xIndex < BATAS_GRID; xIndex++) {
            for (int yIndex = 0; yIndex < BATAS_GRID; yIndex++) {
                if (grid[xIndex][yIndex] == 0) return true;
            }
        }
        return false;
    }

    //Memeriksa apakah kondisi game valid (sesuai syarat MySudoku) atau tidak setelah petak kosong diisi
    public static boolean MySudokuIsInvalid(int[][] grid) {
        if (rowsAreInvalid(grid)) return true;
        if (columnsAreInvalid(grid)) return true;
        if (squaresAreInvalid(grid)) return true;
        else return false;
    }

    public static boolean squaresAreInvalid(int[][] grid) {
        //3 "square" atas
        if (rowOfSquaresIsInvalid(Rows.TOP, grid)) return true;

        //3 "square" tengah
        if (rowOfSquaresIsInvalid(Rows.MIDDLE, grid)) return true;

        //3 "square" bawah
        if (rowOfSquaresIsInvalid(Rows.BOTTOM, grid)) return true;

        return false;
    }

    private static boolean rowOfSquaresIsInvalid(Rows value, int[][] grid) {
        switch (value) {
            case TOP:
                if (squareIsInvalid(0, 0, grid)) return true;
                if (squareIsInvalid(0, 3, grid)) return true;
                if (squareIsInvalid(0, 6, grid)) return true;

                //Otherwise squares appear to be valid
                return false;

            case MIDDLE:
                if (squareIsInvalid(3, 0, grid)) return true;
                if (squareIsInvalid(3, 3, grid)) return true;
                if (squareIsInvalid(3, 6, grid)) return true;
                return false;

            case BOTTOM:
                if (squareIsInvalid(6, 0, grid)) return true;
                if (squareIsInvalid(6, 3, grid)) return true;
                if (squareIsInvalid(6, 6, grid)) return true;
                return false;

            default:
                return false;
        }
    }

    public static boolean squareIsInvalid(int yIndex, int xIndex, int[][] grid) {
        int yIndexEnd = yIndex + 3;
        int xIndexEnd = xIndex + 3;

        List<Integer> square = new ArrayList<>();

        while (yIndex < yIndexEnd) {

            while (xIndex < xIndexEnd) {
                square.add(
                        grid[xIndex][yIndex]
                );
                xIndex++;
            }
            xIndex -= 3;

            yIndex++;
        }

        if (collectionHasRepeats(square)) return true;
        return false;
    }

    public static boolean columnsAreInvalid(int[][] grid) {
        for (int xIndex = 0; xIndex < BATAS_GRID; xIndex++) {
            List<Integer> column = new ArrayList<>();
            for (int yIndex = 0; yIndex < BATAS_GRID; yIndex++) {
                column.add(grid[xIndex][yIndex]);
            }

            if (collectionHasRepeats(column)) return true;
        }

        return false;
    }

    public static boolean rowsAreInvalid(int[][] grid) {
        for (int yIndex = 0; yIndex < BATAS_GRID; yIndex++) {
            List<Integer> row = new ArrayList<>();
            for (int xIndex = 0; xIndex < BATAS_GRID; xIndex++) {
                row.add(grid[xIndex][yIndex]);
            }

            if (collectionHasRepeats(row)) return true;
        }

        return false;
    }

    public static boolean collectionHasRepeats(List<Integer> collection) {
        //method frequency akan mengembalikan jumlah elemen yang sama pada list
        for (int index = 1; index <= BATAS_GRID; index++) {
            if (Collections.frequency(collection, index) > 1) return true;
        }

        return false;
    }
}
