package strategy;

import models.Board;
import models.Move;
import models.Player;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class DiagonalWinningStrategy implements WinningStrategy{
    private Map<Symbol, Integer> leftDiagonal = new HashMap<>();
    private Map<Symbol, Integer> rightDiagonal = new HashMap<>();
    @Override
    public boolean checkWinner(Move move, int dimension) {
        int row = move.getCell().getRow();
        int col= move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        // check if row, col is part of left diagonal
        if(row == col){
            leftDiagonal.put(symbol,leftDiagonal.getOrDefault(symbol,0)+1);
            if(leftDiagonal.get(symbol)==dimension) {
                return true;
            }
        }
        if(row+col==dimension-1){
            rightDiagonal.put(symbol,rightDiagonal.getOrDefault(symbol,0)+1);
            if(rightDiagonal.get(symbol) == dimension){
                return true;
            }
        }
        return false;
    }

    @Override
    public void handleUndo(Move move, int dimension) {
        int row = move.getCell().getRow();
        int col= move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        if(row == col){
            leftDiagonal.put(symbol,leftDiagonal.getOrDefault(symbol,0)+1);
        }
        if(row+col == dimension-1){
            rightDiagonal.put(symbol,rightDiagonal.getOrDefault(symbol,0)+1);
        }

    }


}
