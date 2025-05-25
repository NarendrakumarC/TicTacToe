package strategy;

import models.Board;
import models.Cell;
import models.Move;

public interface BotPlayingStrategy {
    public Move makeMove();
    public Cell chooseCellToPlay(Board board);
}
