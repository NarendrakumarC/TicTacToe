package models;

public class Symbol {
    private char symbolChar;
    //url of the avatars
    private String avatarURL;

    public Symbol(char symbolChar, String avatarURL) {
        this.symbolChar = symbolChar;
        this.avatarURL = avatarURL;
    }

    public char getSymbolChar() {
        return symbolChar;
    }

    public void setSymbolChar(char symbolChar) {
        this.symbolChar = symbolChar;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
