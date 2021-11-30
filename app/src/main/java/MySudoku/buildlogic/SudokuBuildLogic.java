package MySudoku.buildlogic;

import com.sun.javafx.iio.ios.IosDescriptor;
import MySudoku.computationlogic.GameLogic;
import MySudoku.persistence.LocalStorageImpl;
import MySudoku.problemdomain.IStorage;
import MySudoku.problemdomain.SudokuGame;
import MySudoku.userinterface.IUserInterface;
import MySudoku.userinterface.logic.ControlLogic;

import java.util.List;

import java.io.IOException;

public class SudokuBuildLogic {
    public static void build(IUserInterface.View userInterface) throws IOException {
        SudokuGame initialState;
        IStorage storage = new LocalStorageImpl();

        try {
            initialState = storage.getGameData();
        } catch (IOException e) {
            //apabila tidak ada data game pada local storage
            initialState = GameLogic.getNewGame();
            storage.updateGameData(initialState);
        }

        IUserInterface.EventListener uiLogic = new ControlLogic(storage, userInterface);
        userInterface.setListener(uiLogic);
        userInterface.updateBoard(initialState);
    }
}
