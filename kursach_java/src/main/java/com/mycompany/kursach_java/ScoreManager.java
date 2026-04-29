package com.mycompany.kursach_java;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreManager {
    private static final String SCORES_FILE = "scores.dat";
    private static final int MAX_RECORDS = 10;
    
    private final List<ScoreRecord> records = new ArrayList<>();
    
    public ScoreManager() {
        loadScores();
    }
    
    @SuppressWarnings("unchecked")
    public void loadScores() {
        File file = new File(SCORES_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                records.clear();
                for (Object item : (List<?>) obj) {
                    if (item instanceof ScoreRecord) {
                        records.add((ScoreRecord) item);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("⚠️ Ошибка загрузки счётов: " + e.getMessage());
        }
    }
    
    public void saveScores() throws TetrisException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SCORES_FILE))) {
            oos.writeObject(records);
            oos.flush();
        } catch (IOException e) {
            throw new TetrisException("Не удалось сохранить рекорды", e);
        }
    }
    
    public void addRecord(String playerName, int score) throws TetrisException {
        records.add(new ScoreRecord(playerName, score));
        
        List<ScoreRecord> sorted = records.stream()
                .sorted(Comparator.comparingInt(ScoreRecord::getScore).reversed())
                .limit(MAX_RECORDS)
                .collect(Collectors.toList());
        
        records.clear();
        records.addAll(sorted);
        
        saveScores();
    }
    
    public List<ScoreRecord> getTopRecords(int limit) {
        return records.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    public Optional<ScoreRecord> getPlayerBestRecord(String playerName) {
        return records.stream()
                .filter(r -> r.getPlayerName().equalsIgnoreCase(playerName))
                .findFirst();
    }
}