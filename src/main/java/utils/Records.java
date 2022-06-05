package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Records {

    private record Record(String userName, int scores) {
        @Override
        public String toString() {
            return userName + " " + scores;
        }
    }

    private static final String RECORDS_FILE = "records";
    private static Records instance = null;
    private final static int LENGTH = 10;
    private final Record[] records;
    private int pos;

    Records(Record[] records, int pos){
        this.records = records;
        this.pos = pos;
    }

    public static Records getInstance() {
        if (instance != null) return instance;
        instance = loadScores();
        return instance;
    }

    public static Records loadScores() {
        Record[] records = new Record[LENGTH];
        int pos = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tmp = line.split(" ");
                add(tmp[0], Integer.parseInt(tmp[1]), records, pos++);
            }
        } catch (IOException e) {
            System.out.println("Couldn't load old records");
            return new Records(new Record[10], 0);
        }
        return new Records(records, pos);
    }

    public void saveScores() {
        String table = createRecordTable();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECORDS_FILE))) {
            writer.write(table);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save the records");
        }
    }

    public String createRecordTable() {
        ArrayList<Record> recordList = new ArrayList<>(List.of(records));
        return recordList.stream().map(java.lang.Record::toString).collect(Collectors.joining("\n"));
    }

    public boolean addNewRecord(String name, int score) {
        if (score == 0) throw new IllegalStateException("Zero result cannot be added to the record table");
        if (!isCorrect(name)) return false;
        if (add(name, score, records, pos)) pos++;
        return true;
    }

    private static boolean add(String name, int score, Record[] array, int numberElements){
        Record curRecord = new Record(name, score);
        if (numberElements == 10 && score < array[9].scores) return false;
        for (int i = 0; i < numberElements; i++){
            if (score == array[i].scores && name.equals(array[i].userName)) continue;
            if (score >= array[i].scores()){
                if (numberElements == 10) numberElements--;
                System.arraycopy(array, i, array, i + 1, numberElements - i);
                array[i] = curRecord;
                return true;
            }
        }
        array[numberElements] = curRecord;
        return true;
    }

    private boolean isCorrect(String res) {
        if (res.length() == 0) return false;
        int i = 0;
        while (i < res.length()) {
            if (res.charAt(i) == ' ') return false;
            i++;
        }
        return true;
    }
}
