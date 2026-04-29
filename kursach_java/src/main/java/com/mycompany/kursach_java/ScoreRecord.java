package com.mycompany.kursach_java;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScoreRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private final String playerName;
    private final int score;
    private final LocalDateTime timestamp;
    
    public ScoreRecord(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public int getScore() {
        return score;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    @Override
    public String toString() {
        String time = timestamp.format(DateTimeFormatter.ofPattern("dd.MM HH:mm"));
        return String.format("%-10s %6d очков [%s]", playerName, score, time);
    }
}