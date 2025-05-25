import controller.GameController;
import enums.BotDifficultyLevel;
import enums.GameState;
import enums.PlayerType;
import exceptions.BotCountInvalidException;
import exceptions.PlayerCountNotValidException;
import exceptions.PlayerUniqueSymbolNotValidException;
import models.Bot;
import models.Game;
import models.Player;
import models.Symbol;
import strategy.ColumnWinningStrategy;
import strategy.DiagonalWinningStrategy;
import strategy.RowWinningStrategy;
import strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Client {
    public static void main(String[] args) throws PlayerUniqueSymbolNotValidException, BotCountInvalidException, PlayerCountNotValidException {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Narendra",new Symbol('N',""), PlayerType.HUMAN));
        players.add(new Bot("BOT",new Symbol('B',""), BotDifficultyLevel.EASY));
     //    players.add(new Player("Harish",new Symbol('H',""), PlayerType.HUMAN));

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy());
        winningStrategies.add(new ColumnWinningStrategy());
        winningStrategies.add(new DiagonalWinningStrategy());
        GameController gameController = new GameController();
        Game game =gameController.startGame(3,players,winningStrategies);
        Scanner scanner = new Scanner(System.in);

        while (game.getGameState().equals(GameState.IN_PROGRESS)){
            // show the board (print the current board)
            //Tell whose player turn it is and ask him to choose his row, col, where he as to play
            // execute the move on the board if its a valid
            // ask the user if any undo its last move
            gameController.printBoard(game);
            gameController.makeMove(game);
            System.out.println("Do you want to undo Y/N ?");
            String undoAnswer = scanner.next();

            //ask this unDo option only for humans
            //
            if(undoAnswer.equalsIgnoreCase("y")){ //Y
                gameController.undo(game);
            }
        }

        if(game.getGameState().equals(GameState.GAMEOVER)){
            gameController.printBoard(game);
            System.out.println("Game has a winner and winner is "+ game.getWinner().getName());
        }else{
            System.out.println("Game is a draw");
        }


    }
}