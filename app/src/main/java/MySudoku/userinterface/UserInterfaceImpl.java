package MySudoku.userinterface;

import MySudoku.konstanta.GameState;
import MySudoku.problemdomain.Coordinates;
import MySudoku.problemdomain.SudokuGame;

import MySudoku.persistence.LocalStorageImpl;
import MySudoku.problemdomain.IStorage;

import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Optional;
import java.util.HashMap;

public class UserInterfaceImpl implements IUserInterface.View,
        EventHandler<KeyEvent>{
    private final Stage stage;
    private final Group root;

    private HashMap<Coordinates, SudokuTextField> textFieldCoordinates;

    private IUserInterface.EventListener listener;

    private static final double WINDOW_Y = 732;
    private static final double WINDOW_X = 668;
    private static final double BOARD_PADDING = 50;

    private static final double BOARD_X_AND_Y = 576;
    private static final Color WINDOW_BACKGROUND_COLOR = Color.rgb(0, 0, 0);
    private static final Color BOARD_BACKGROUND_COLOR = Color.rgb(229, 204, 255);
    private static final String SUDOKU = "Sudoku";

    public UserInterfaceImpl(Stage stage) {
        this.stage = stage;
        this.root = new Group();
        this.textFieldCoordinates = new HashMap<>();
        initializeUserInterface();
    }


    @Override
    public void setListener(IUserInterface.EventListener listener) {
        this.listener = listener;
    }

    public void initializeUserInterface() {
        drawBackground(root);
        drawButtons(root);
        drawSudokuBoard(root);
        drawTextFields(root);
        drawGridLines(root);
        drawButtonsclue(root);
        stage.show();
    }

    private void drawTextFields(Group root) {
        final int xOrigin = 50;
        final int yOrigin = 50;
        final int xAndYDelta = 64;


        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                int x = xOrigin + xIndex * xAndYDelta;
                int y = yOrigin + yIndex * xAndYDelta;
                SudokuTextField tile = new SudokuTextField(xIndex, yIndex);

                styleSudokuTile(tile, x, y);

                tile.setOnKeyPressed(this);

                textFieldCoordinates.put(new Coordinates(xIndex, yIndex), tile);

                root.getChildren().add(tile);
            }
        }
    }

    private void styleSudokuTile(SudokuTextField tile, double x, double y) {
        Font numberFont = new Font(32);
        tile.setFont(numberFont);
        tile.setAlignment(Pos.CENTER);

        tile.setLayoutX(x);
        tile.setLayoutY(y);
        tile.setPrefHeight(64);
        tile.setPrefWidth(64);

        tile.setBackground(Background.EMPTY);
    }

    private void drawGridLines(Group root) {
        int xAndY = 114;
        int index = 0;
        while (index < 8) {
            int thickness;
            if (index == 2 || index == 5) {
                thickness = 3;
            } else {
                thickness = 2;
            }

            Rectangle verticalLine = getLine(
                    xAndY + 64 * index,
                    BOARD_PADDING,
                    BOARD_X_AND_Y,
                    thickness
                    );

            Rectangle horizontalLine = getLine(
                    BOARD_PADDING,
                    xAndY + 64 * index,
                    thickness,
                    BOARD_X_AND_Y
            );

            root.getChildren().addAll(
                    verticalLine,
                    horizontalLine
            );

            index++;
        }
    }

    public Rectangle getLine(double x, double y, double height, double width){
        Rectangle line = new Rectangle();

        line.setX(x);
        line.setY(y);

        line.setHeight(height);
        line.setWidth(width);

        line.setFill(Color.BLACK);
        return line;

    }

    private void drawBackground(Group root) {
        Scene scene = new Scene(root, WINDOW_X, WINDOW_Y);
        scene.setFill(WINDOW_BACKGROUND_COLOR);
        stage.setScene(scene);
    }

    private void drawSudokuBoard(Group root) {
        Rectangle boardBackground = new Rectangle();
        boardBackground.setX(BOARD_PADDING);
        boardBackground.setY(BOARD_PADDING);
        boardBackground.setWidth(BOARD_X_AND_Y);
        boardBackground.setHeight(BOARD_X_AND_Y);
        boardBackground.setFill(BOARD_BACKGROUND_COLOR);
        root.getChildren().add(boardBackground);
    }

    private void drawButtons(Group root) {
        Button restartBtn = drawButton(235, 670, "Restart");
        restartBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                levelDialog();
            };
        });

        root.getChildren().add(restartBtn);
    }

    private void drawButtonsclue(Group root) {
        Button clueBtn = drawButton(400, 670, "Clue");
        clueBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clueDialog();
            };
        });

        root.getChildren().add(clueBtn);
    }

    private Button drawButton(int x, int y, String text){
        Button btn = new Button(text);

        btn.setLayoutX(x);
        btn.setLayoutY(y);

        return btn;
    }

    private void levelDialog(){
        ButtonType easy = new ButtonType("Mudah");
        ButtonType medium = new ButtonType("Sedang");
        ButtonType hard = new ButtonType("Sulit");

        Alert newGame = new Alert(Alert.AlertType.NONE);
        newGame.setTitle("New Game");
        newGame.setHeaderText("Pilih tingkat kesulitan.");
        newGame.getButtonTypes().addAll(easy, medium, hard);

        Optional<ButtonType> option = newGame.showAndWait();

		if (option.get() == easy) {
            SudokuGame.difficulty = "easy";
            listener.onButtonClick();
		} else if (option.get() == medium) {
            SudokuGame.difficulty = "medium";
			listener.onButtonClick();
		} else if (option.get() == hard) {
            SudokuGame.difficulty = "hard";
			listener.onButtonClick();
		}
    }

    private void clueDialog(){
        Alert clue = new Alert(Alert.AlertType.NONE);
        clue.setTitle("Clue");
        clue.setHeaderText("Tujuan permainan Sudoku adalah mengisi sel-sel yang kosong dengan angka antara 1 dan 9 (setiak sel hanya 1 angka) sesuai dengan petunjuk berikut:\n1. Angka hanya dapat muncul sekali dalam setiap baris\n2. Angka hanya dapat muncul sekali dalam setiap kolom\n3. Angka hanya dapat muncul sekali dalam setiap area");
        Window window = clue.getDialogPane().getScene().getWindow();
        
        clue.show();
        window.setOnCloseRequest(e -> clue.close());
    }

    @Override
    public void updateSquare(int x, int y, int input) {
        SudokuTextField tile = textFieldCoordinates.get(new Coordinates(x, y));
        String value = Integer.toString(
                input
        );

        if (value.equals("0")) value = "";

        tile.textProperty().setValue(value);
    }

    @Override
    public void updateBoard(SudokuGame game) {
        for (int xIndex = 0; xIndex < 9; xIndex++) {
            for (int yIndex = 0; yIndex < 9; yIndex++) {
                TextField tile = textFieldCoordinates.get(new Coordinates(xIndex, yIndex));

                String value = Integer.toString(
                        game.getCopyOfGridState()[xIndex][yIndex]
                );

                if (value.equals("0")) value = "";
                tile.setText(
                        value
                );

                if (game.getGameState() == GameState.NEW){
                    if (value.equals("")) {
                        tile.setStyle("-fx-opacity: 1;");
                        tile.setDisable(false);
                    } else {
                        tile.setStyle("-fx-opacity: 0.8;");
                        tile.setDisable(true);
                    }
                }
            }
        }
    }

    @Override
    public void showDialog(String message) {
        Alert dialog = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        dialog.showAndWait();

        if (dialog.getResult() == ButtonType.OK) levelDialog();
    }

    @Override
    public void showError(String message) {
        Alert dialog = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        dialog.showAndWait();
    }


    @Override
    public void handle(KeyEvent event) {
        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            if (event.getText().equals("0")
                    || event.getText().equals("1")
                    || event.getText().equals("2")
                    || event.getText().equals("3")
                    || event.getText().equals("4")
                    || event.getText().equals("5")
                    || event.getText().equals("6")
                    || event.getText().equals("7")
                    || event.getText().equals("8")
                    || event.getText().equals("9")
            ) {
                int value = Integer.parseInt(event.getText());
                handleInput(value, event.getSource());
            } else if (event.getCode() == KeyCode.BACK_SPACE) {
                handleInput(0, event.getSource());
            } else {
                ((TextField)event.getSource()).setText("");
            }
        }

        event.consume();
    }

    private void handleInput(int value, Object source) {
        listener.onSudokuInput(
                ((SudokuTextField) source).getX(),
                ((SudokuTextField) source).getY(),
                value
        );
    }
}