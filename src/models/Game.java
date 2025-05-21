package models;

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
    private List<Move> moves;
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

    public Player checkWinner(){
        //algo to check winner along the rows, cols, diagonal
        for (WinningStrategy winningStrategy : winningStrategies){
            winningStrategy.checkWinner();
        }
        return null;
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
