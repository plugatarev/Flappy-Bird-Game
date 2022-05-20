package utils;

import java.io.*;
import java.util.*;

public class Records {
    public final static String fileName = GameConfig.getInstance().getRECORDS_FILE();
    private final Map<String, Integer> records = new HashMap<>();

    public Records(){
        loadScores();
    }

    public void loadScores(){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] tmp = line.split(" ");
                records.put(tmp[0], Integer.valueOf(tmp[1]));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveScores(){
        StringBuilder table = new StringBuilder();
        createRecordTable(table);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))){
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
               .forEach(c -> s.append(c.getKey()).append(" ").append(c.getValue()).append("\n"));
    }

    public void addNewScore(String name, int score){
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
