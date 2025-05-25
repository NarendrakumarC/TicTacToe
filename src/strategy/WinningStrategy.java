package strategy;

import models.Board;
import models.Move;
import models.Player;

public interface WinningStrategy {
    //checkWinnerAlgo in O(1) - HM
    boolean checkWinner(Move move, int dimension);

    void handleUndo(Move move, int dimension);
}
