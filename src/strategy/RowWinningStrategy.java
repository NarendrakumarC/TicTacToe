package strategy;

import models.Move;
import models.Player;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class RowWinningStrategy implements WinningStrategy{
    private Map<Integer, HashMap<Symbol, Integer>> rowHm = new HashMap<>();

    @Override
    public boolean checkWinner(Move move, int dimension) {
        int row= move.getCell().getRow();
        Symbol symbol = move.getPlayer().getSymbol();
        if(!rowHm.containsKey(row)){
            rowHm.put(row,new HashMap<>());
        }
        Map<Symbol, Integer> hm =rowHm.get(row);
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
            int row = move.getCell().getRow();
            Symbol symbol = move.getPlayer().getSymbol();
            Map<Symbol, Integer> hm = rowHm.get(row);
            hm.put(symbol, hm.get(symbol)-1);
    }
}
