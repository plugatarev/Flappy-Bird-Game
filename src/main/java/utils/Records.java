package utils;

import java.io.*;
import java.util.*;

public class Records {
    private static final String RECORDS_FILE = "records";
    private final List<Record> records;
    private static Records instance = null;


    private Records(List<Record> records){
        this.records = records;
    }

    public static Records getInstance() {
        if (instance != null) return instance;
        instance = loadScores();
        return instance;
    }

    public static Records loadScores(){
        List<Record> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null){
                String[] tmp = line.split(" ");
                records.add(new Record(tmp[0], Integer.parseInt(tmp[1])));
            }
        } catch (IOException e) {
            // log
            return new Records(new ArrayList<>());
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

    private void sortRecords(){
        records.sort((o1, o2) -> {
            if (o1.scores() == o2.scores()) return 0;
            return o1.scores() < o2.scores() ? 1 : -1;
        });
    }


    public Record getMinScore(){
        if (records.isEmpty()) return null;
        sortRecords();
        return records.get(0);
    }

    public void createRecordTable(StringBuilder s){
        sortRecords();
        //Collectors.joining ?
        records.forEach(c -> s.append(c.userName()).append(" ").append(c.scores()).append("\n"));}

    public void addNewScore(String name, int score){
        // score == 0 -> throw IllegalStateException
        if (score != 0) {
            if (records.isEmpty()){
                records.add(new Record(name, score));
                return;
            }
            Record result = getMinScore();
            if (records.size() < 10){
                records.add(new Record(name, score));
                return;
            }
            if (records.size() == 10 && score > result.scores()){
                records.remove(9);
                records.add(new Record(name, score));
            }
        }
    }
}
