package controller;

import exceptions.BotCountInvalidException;
import exceptions.PlayerCountNotValidException;
import exceptions.PlayerUniqueSymbolNotValidException;
import models.Game;
import models.Player;
import strategy.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int dimension,List<Player> players, List<WinningStrategy> winningStrategies) throws PlayerUniqueSymbolNotValidException, BotCountInvalidException, PlayerCountNotValidException {
        Game game = Game.getBuilder()
                .setDimension(dimension)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies)
                .build();
        return game;
    }

    public void makeMove(){

    }
    public void undo(){

    }
}
