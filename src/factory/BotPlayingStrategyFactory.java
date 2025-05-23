package factory;

import enums.BotDifficultyLevel;
import strategy.BotPlayingStrategy;
import strategy.EasyBotPlayingStrategy;
import strategy.HardBotPlayingStrategy;
import strategy.MediumBotPlayingStrategy;

public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotDifficultyLevel botDifficultyLevel){
        if(botDifficultyLevel.equals(BotDifficultyLevel.EASY)){
            return  new EasyBotPlayingStrategy();
        } else if (botDifficultyLevel.equals(BotDifficultyLevel.MEDIUM)) {
            return  new MediumBotPlayingStrategy();
        } else if (botDifficultyLevel.equals(BotDifficultyLevel.HARD)) {
            return  new HardBotPlayingStrategy();
        }
        return new EasyBotPlayingStrategy();
    }
}
