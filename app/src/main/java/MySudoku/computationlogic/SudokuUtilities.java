package MySudoku.computationlogic;

import MySudoku.problemdomain.SudokuGame;

public class SudokuUtilities {
    public static void copySudokuArrayValues(int[][] oldArray, int[][] newArray) {
        for (int xIndex = 0; xIndex < SudokuGame.BATAS_GRID; xIndex++){
            for (int yIndex = 0; yIndex < SudokuGame.BATAS_GRID; yIndex++ ){
                newArray[xIndex][yIndex] = oldArray[xIndex][yIndex];
            }
        }
    }

    public static int[][] copyToNewArray(int[][] oldArray) {
        int[][] newArray = new int[SudokuGame.BATAS_GRID][SudokuGame.BATAS_GRID];
         copySudokuArrayValues(oldArray,newArray);
        return newArray;
    }
}
