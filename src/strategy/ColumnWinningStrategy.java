package strategy;

import models.Board;
import models.Move;
import models.Player;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColumnWinningStrategy implements WinningStrategy{

    private Map<Integer, HashMap<Symbol, Integer>> colHm = new HashMap<>();
    @Override
    public boolean checkWinner(Move move, int dimension) {
        int col= move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        if(!colHm.containsKey(col)){
            colHm.put(col,new HashMap<>());
        }
        Map<Symbol, Integer> hm =colHm.get(col);
        if(!hm.containsKey(symbol)){
            hm.put(symbol,1);
        }else{
            hm.put(symbol,hm.get(symbol)+1);
        }
        // or hm.put(symbol,hm.getOrDefault(symbol,0)+1);
        int count = hm.get(symbol);
        if(count == dimension){
            return true;
        }else{
            return false;
        }
    }


    @Override
    public void handleUndo(Move move, int dimension) {
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        Map<Symbol, Integer> hm = colHm.get(col);
        hm.put(symbol, hm.get(symbol)-1);
    }


}
