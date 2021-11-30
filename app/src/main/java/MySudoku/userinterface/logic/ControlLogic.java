package MySudoku.userinterface.logic;


import MySudoku.konstanta.GameState;
import MySudoku.konstanta.Message;
import MySudoku.computationlogic.GameLogic;
import MySudoku.problemdomain.IStorage;
import MySudoku.problemdomain.SudokuGame;
import MySudoku.userinterface.IUserInterface;

import java.io.IOException;

public class ControlLogic implements IUserInterface.EventListener {

    private IStorage storage;
    private IUserInterface.View view;

    public ControlLogic(IStorage storage, IUserInterface.View view) {
        this.storage = storage;
        this.view = view;
    }

    @Override
    public void onSudokuInput(int x, int y, int input) {
        try {
            SudokuGame gameData = storage.getGameData();
            int[][] newGridState = gameData.getCopyOfGridState();
            newGridState[x][y] = input;

            gameData = new SudokuGame(
                    GameLogic.checkForCompletion(newGridState),
                    newGridState
            );

            storage.updateGameData(gameData);

            view.updateSquare(x, y, input);

            if (gameData.getGameState() == GameState.COMPLETE) view.showDialog(Message.GAME_COMPLETE);
        } catch (IOException e) {
            e.printStackTrace();
            view.showError(Message.ERROR);
        }
    }

    @Override
    public void onButtonClick() {
        try {
            storage.updateGameData(
                    GameLogic.getNewGame()
            );

            view.updateBoard(storage.getGameData());
        } catch (IOException e) {
            view.showError(Message.ERROR);
        }
    }
}
