package models;

import enums.BotDifficultyLevel;
import enums.PlayerType;
import factory.BotPlayingStrategyFactory;
import strategy.BotPlayingStrategy;

public class Bot extends Player{
    private BotDifficultyLevel botDifficultyLevel;

    private BotPlayingStrategy botPlayingStrategy;

    public Bot(String name, Symbol symbol, BotDifficultyLevel botDifficultyLevel) {
        super(name, symbol, PlayerType.BOT);
        this.botDifficultyLevel = botDifficultyLevel;
        this.botPlayingStrategy = BotPlayingStrategyFactory.getBotPlayingStrategy(botDifficultyLevel);
    }

    public Move makeMove(){
        this.botPlayingStrategy.makeMove();
        return  null;
    }

    public Cell chooseCellToPlay(Board board){
        // automated
        return botPlayingStrategy.chooseCellToPlay(board);
    }



}
