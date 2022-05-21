package utils;

import java.io.*;
import java.util.*;

public class Records {
    private static final String RECORDS_FILE = "records";
    private final Map<String, Integer> records;

    public Records(Map<String, Integer> records){
        this.records = records;
//        loadScores();
    }

    private static Records instance = null;

    public static Records getInstance() {
        if (instance != null) return instance;
        instance = loadScores();
        return instance;
    }

    public static Records loadScores(){
        Map<String, Integer> records = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] tmp = line.split(" ");
                records.put(tmp[0], Integer.valueOf(tmp[1]));
            }
        } catch (IOException e) {
            // log
            return new Records(new HashMap<>());
        }
        return new Records(records);
    }

    public void saveScores(){
        StringBuilder table = new StringBuilder();
        createRecordTable(table);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECORDS_FILE))){
            writer.write(table.toString());
        } catch (IOException e){
            System.err.println(e.getMessage());
        }
    }

    public Map.Entry<String, Integer> getMinScore(){
        if (records.isEmpty()) return null;
        return records.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).get();
    }

    public void createRecordTable(StringBuilder s){
        records.entrySet()
               .stream()
               .sorted(Map.Entry.comparingByValue())
                // Collectors.joining
               .forEach(c -> s.append(c.getKey()).append(" ").append(c.getValue()).append("\n"));
    }

    public void addNewScore(String name, int score){
        // score == 0 -> throw IllegalStateException
        if (score != 0) {
            if (records.isEmpty()){
                records.put(name, score);
                return;
            }
            Map.Entry<String, Integer> result = getMinScore();
            if (records.size() < 10){
                records.put(name, score);
                return;
            }
            if (records.size() == 10 && score > result.getValue()){
                records.remove(result.getKey());
                records.put(name, score);
            }
        }
    }
}
