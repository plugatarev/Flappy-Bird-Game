package utils;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

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
            System.out.println("Couldn't load old records");
            return new Records(new ArrayList<>());
        }
        return new Records(records);
    }

    public void saveScores(){
        String table = createRecordTable();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECORDS_FILE))){
            writer.write(table);
        } catch (IOException e){
            throw new RuntimeException("Failed to save the records");
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
        return records.get(records.size() - 1);
    }

    public String createRecordTable() {
        sortRecords();
        return records.stream().map(Record::toString).collect(Collectors.joining("\n"));
    }

    public boolean isFull(){
        return records.size() == 10;
    }

    public void addNewScore(String name, int score){
        if (score == 0) throw new IllegalStateException("Zero result cannot be added to the record table");
        if (records.isEmpty()){
            records.add(new Record(name, score));
            return;
        }
        Record result = getMinScore();
        if (!isFull()){
            records.add(new Record(name, score));
            return;
        }
        if (isFull() && score > result.scores()){
            records.remove(9);
            records.add(new Record(name, score));
        }
    }
}
