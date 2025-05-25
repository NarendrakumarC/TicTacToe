package models;

import enums.CellState;
import enums.GameState;
import enums.PlayerType;
import exceptions.BotCountInvalidException;
import exceptions.PlayerCountNotValidException;
import exceptions.PlayerUniqueSymbolNotValidException;
import strategy.WinningStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Move> moves; // helps in undo features
    private Player winner;
    private int nextPlayerIndex;
    private GameState gameState;
    private List<WinningStrategy> winningStrategies;


    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies){
        this.board = new Board(dimension);
        this.players = players;
        this.moves = new ArrayList<>();
        this.winner = null;
        this.nextPlayerIndex =0;
        this.gameState =GameState.IN_PROGRESS;
        this.winningStrategies = winningStrategies;
    }

    public void undo(Game game){
        if(moves.size()==0){
            System.out.println("There are no moves on the board, hence undo not possible ");
            return;
        }

        // Remove the last move from the list
        // Remove the move from the board, make the cell empty
     Move lastMove = moves.get(moves.size()-1);
        moves.remove(lastMove);
        Cell cell = lastMove.getCell();
        cell.setCellState(CellState.EMPTY);
        cell.setPlayer(null);

        // applicable only to human player
      if(lastMove.getPlayer().getPlayerType().equals(PlayerType.BOT)){
          System.out.println("Cannot Undo for Bot player");
          return;
        }

//(a-b)% m = (a%m-b%m +m) %m
        nextPlayerIndex = (nextPlayerIndex-1 + players.size()) % players.size();

        //[0,1,2]
        //update the hashmaps

        for(WinningStrategy winningStrategy: winningStrategies){
            winningStrategy.handleUndo(lastMove, board.getDimension());
        }
    }

    public void makeMove(){
        // who is next player to play
        // ask that player to play the game
        Player currentplayer = players.get(nextPlayerIndex);
        System.out.println("It's "+currentplayer.getName()+"'s turn");
        Cell dummyCell =currentplayer.chooseCellToPlay(board);
        //Before player make move validate the move
        if(!validateMove(dummyCell.getRow(),dummyCell.getCol())){
            System.out.println("It's Invalid Move, Can you choose again ?");
            return;
        }
        //Execute the move on the board
        Cell cell = board.getBoard().get(dummyCell.getRow()).get(dummyCell.getCol());
        cell.setCellState(CellState.FILLED);
        cell.setPlayer(currentplayer);
        Move move = new Move(currentplayer,cell);
        moves.add(move);
        // update Next player turn index
        nextPlayerIndex = (nextPlayerIndex+1) % players.size();
        //check weather this move is winning move ?
        if(checkWinner(move)){
            gameState =GameState.GAMEOVER;
            winner = currentplayer;
        } else if (moves.size()==board.getDimension()*board.getDimension()) { //Draw, all the cells are filled
            gameState =GameState.DRAW;
        }

    }

    private boolean validateMove(int row, int col){
        // Boundary checks
        if(row <0 || col <0 || row >=board.getDimension() || col >=board.getDimension()){
            return false;
        }
        // Do you need to extract cell object from the board corresponding to this row or col
        if(board.getBoard().get(row).get(col).getCellState().equals(CellState.FILLED)){
            return  false;
        }
        return  true;
    }

    public void printBoard(){
        board.print();
    }

    private boolean checkWinner(Move move){
        //algo to check winner along the rows, cols, diagonal
        for (WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(move, getBoard().getDimension())){
                return true;
            }
        }
        return false;
    }
    public static Builder getBuilder(){
        return new Builder();
    }

    public static class Builder {
        private int dimension;
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder setDimension(int dimension) {
            this.dimension = dimension;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        private void validatePlayerCount() throws PlayerCountNotValidException {
            if (players.size() != dimension - 1) {
                throw new PlayerCountNotValidException("Player's count is not equivalent to dimension -1");
            }
        }

        private void validateUniqueSymbols() throws PlayerUniqueSymbolNotValidException {
            //validate if all player have unique symbols
           /* HashSet<Character> symbols = new HashSet<Character>();
            for(Player player : players){
                Character sym = player.getSymbol().getSymbolChar();
                if(symbols.contains(sym)){
                    throw new RuntimeException("Symbols for all players are not unique");
                }
                symbols.add(player.getSymbol().getSymbolChar());
            }*/
            Set<Character> uniqueSymbol = players.stream().map(s -> s.getSymbol().getSymbolChar()).collect(Collectors.toSet());
            int uniqueSymbolCount = uniqueSymbol.size();
            if (uniqueSymbolCount != players.size()) {
                throw new PlayerUniqueSymbolNotValidException("Symbols for all player are not unique!");
            }
        }

        private void validateBotCount() throws BotCountInvalidException {
            int botCount = 0;
            for (Player player : players) {
                if (player.getPlayerType().equals(PlayerType.BOT)) {
                    botCount++;
                }
            }
            //Total Player =d-1;
            //then bots = d-2
            if (botCount > dimension - 2) {
                throw new BotCountInvalidException("BOT's count invalid, should have only one bot");
            }
        }

        private void validate() throws BotCountInvalidException, PlayerCountNotValidException, PlayerUniqueSymbolNotValidException {
            validateBotCount();
            validatePlayerCount();
            validateUniqueSymbols();
        }

        public Game build() throws PlayerUniqueSymbolNotValidException, BotCountInvalidException, PlayerCountNotValidException {
            //perform all the validation
            validate();
            return new Game(dimension, players,winningStrategies);
        }

    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getNextPlayerIndex() {
        return nextPlayerIndex;
    }

    public void setNextPlayerIndex(int nextPlayerIndex) {
        this.nextPlayerIndex = nextPlayerIndex;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
