package MySudoku.computationlogic;

import MySudoku.konstanta.GameState;
import MySudoku.problemdomain.Coordinates;
import MySudoku.problemdomain.SudokuGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static MySudoku.problemdomain.SudokuGame.BATAS_GRID;


class GameGenerator{
    public static int[][] getNewGameGrid() {
        return unsolveGame(getSolvedGame());
    }

    /**
     * 1. Membuat array 2D berukuran 9x9
     * 2. Untuk setiap "value" 1-9, akan dialokasikan sebanyak 9 kali dengan syarat:
     * - Mengambil sebuah koordinat random. Jika ternyata kosong, akan diassign nilai "value"
     * - Setelah diassign akan diperiksa dengan method MySudokuIsInvalid
     * - Jika ternyata invalid, maka nilai koordinat menjadi 0 kembali dan akan diambil koordinat baru.
     *
     * @return
     */
    private static int[][] getSolvedGame() {
        Random random = new Random(System.currentTimeMillis());
        int[][] newGrid = new int[BATAS_GRID][BATAS_GRID];

        for (int value = 1; value <= BATAS_GRID; value++) {
            //allocations merupakan penanda sudah berapa kali sebuah value ditempatkan
            int allocations = 0;

            //Interrupt berfungsi apabila alokasi terlalu sering dilakukan.
            //Jika hal tersebut terjadi, maka list allocTracker akan direset.
            int interrupt = 0;

            //Mencatat setiap terjadi alokasi
            List<Coordinates> allocTracker = new ArrayList<>();

            //Terlalu banyak attempts menandakan bahwa pola yang dibuat stuck. Maka board akan di reset sepenuhnya.
            int attempts = 0;

            while (allocations < BATAS_GRID) {

                if (interrupt > 200) {
                    allocTracker.forEach(coord -> {
                        newGrid[coord.getX()][coord.getY()] = 0;
                    });

                    interrupt = 0;
                    allocations = 0;
                    allocTracker.clear();
                    attempts++;

                    if (attempts > 500) {
                        clearArray(newGrid);
                        attempts = 0;
                        value = 1;
                    }
                }

                int xCoordinate = random.nextInt(BATAS_GRID);
                int yCoordinate = random.nextInt(BATAS_GRID);

                if (newGrid[xCoordinate][yCoordinate] == 0) {
                    newGrid[xCoordinate][yCoordinate] = value;

                    //Jika invalid, nilai kembali menjadi 0 dan interrupt bertambah
                    if (GameLogic.MySudokuIsInvalid(newGrid)) {
                        newGrid[xCoordinate][yCoordinate] = 0;
                        interrupt++;
                    }else {
                        allocTracker.add(new Coordinates(xCoordinate, yCoordinate));
                        allocations++;
                    }
                }
            }
        }
        return newGrid;
    }

    /*
        Mengambil array 2D dari solvedGame dan memilih beberapa koordinat untuk diassign 0 (kosong).
     
        1. Copy array dari solvedGame pada array baru
        2. Hilangkan maksimal 40 value dari array
        3. Tes apakah masih dapat diselesaikan
        4a. Jika bisa, return array baru
        4b. return to step 1
    */
    private static int[][] unsolveGame(int[][] solvedGame) {
        Random random = new Random(System.currentTimeMillis());
        boolean solvable = false;
        int emptyCells=45;

        if(SudokuGame.difficulty == "easy"){
            emptyCells = 45;
        }else if(SudokuGame.difficulty == "medium"){
            emptyCells = 63;
        }else if(SudokuGame.difficulty == "hard"){
            emptyCells = 72;
        }

        int[][] solvableArray = new int[BATAS_GRID][BATAS_GRID];

        while (solvable == false){

            SudokuUtilities.copySudokuArrayValues(solvedGame, solvableArray);

            //Menghilangkan nilai
            int count = 0;
            while (count < emptyCells) {
                int xCoordinate = random.nextInt(BATAS_GRID);
                int yCoordinate = random.nextInt(BATAS_GRID);

                if (solvableArray[xCoordinate][yCoordinate] != 0) {
                    solvableArray[xCoordinate][yCoordinate] = 0;
                    count++;
                }
            }

            int[][] toBeSolved = new int[BATAS_GRID][BATAS_GRID];
            SudokuUtilities.copySudokuArrayValues(solvableArray, toBeSolved);
            //periksa apahal hasilnya masih solvable
            solvable = SudokuSolver.puzzleIsSolvable(toBeSolved, emptyCells);

            //TODO Delete after tests
            System.out.println(solvable);
        }

        return solvableArray;
    }

    private static void clearArray(int[][] newGrid) {
        for (int xIndex = 0; xIndex < BATAS_GRID; xIndex++) {
            for (int yIndex = 0; yIndex < BATAS_GRID; yIndex++) {
                newGrid[xIndex][yIndex] = 0;
            }
        }
    }

}