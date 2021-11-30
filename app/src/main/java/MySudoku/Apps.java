package MySudoku;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import MySudoku.userinterface.IUserInterface;
import MySudoku.userinterface.UserInterfaceImpl;
import MySudoku.buildlogic.SudokuBuildLogic;
import MySudoku.problemdomain.SudokuGame;

import java.util.Arrays;
import java.util.List;

public class Apps extends Application {
  private IUserInterface.View uiImpl;

  @Override
  public void start(Stage primaryStage) throws Exception {
    uiImpl = new UserInterfaceImpl(primaryStage);
    List<String> args = getParameters().getRaw();

    if(!args.isEmpty()){
      SudokuGame.difficulty = String.valueOf(args.get(0));
    }

    try {
        SudokuBuildLogic.build(uiImpl);
    } catch (IOException e) {
        e.printStackTrace();
        throw e;
    } 
  }

  public static void main(String[] args) {
    launch(args);
  }

}