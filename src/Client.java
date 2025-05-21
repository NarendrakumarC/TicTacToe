import controller.GameController;
import enums.PlayerType;
import exceptions.BotCountInvalidException;
import exceptions.PlayerCountNotValidException;
import exceptions.PlayerUniqueSymbolNotValidException;
import models.Game;
import models.Player;
import models.Symbol;
import strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Client {
    public static void main(String[] args) throws PlayerUniqueSymbolNotValidException, BotCountInvalidException, PlayerCountNotValidException {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Narendra",new Symbol('N',""), PlayerType.HUMAN));
        players.add(new Player("Rama",new Symbol('R',""), PlayerType.HUMAN));
        players.add(new Player("Harish",new Symbol('H',""), PlayerType.HUMAN));

        List<WinningStrategy> winningStrategies = new ArrayList<>();
        GameController gameController = new GameController();
        gameController.startGame(4,players,winningStrategies);


        System.out.println();
    }
}