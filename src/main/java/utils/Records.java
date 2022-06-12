package utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Records {

    private record Record(String userName, int scores) {
        @Override
        public String toString() {
            return userName + " " + scores;
        }
    }

    private static final String RECORDS_FILE = "records";
    private static Records INSTANCE = null;
    private final static int LENGTH = 10;
    private final Record[] records;
    private int pos;

    private Records(Record[] records, int pos){
        this.records = records;
        this.pos = pos;
    }

    public static Records getInstance() {
        if (INSTANCE != null) return INSTANCE;
        INSTANCE = loadRecords();
        return INSTANCE;
    }

    public static Records loadRecords() {
        Record[] records = new Record[LENGTH];
        int pos = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tmp = line.split(" ");
                if (tmp.length == 1) throw new RuntimeException("record not contains scores");
                int record = Integer.parseInt(tmp[1]);
                if (pos == LENGTH || (pos != 0 && record > records[pos - 1].scores)){
                    throw new RuntimeException("number of records exceeds exceeds the limit or the order is incorrect\n");
                }
                add(tmp[0], record, records, pos++);
            }
        } catch (IOException | RuntimeException e) {
            System.out.println("Couldn't load old records: " + e.getMessage());
            return new Records(new Record[10], 0);
        }
        return new Records(records, pos);
    }

    public void saveScores() {
        String table = createRecordTable();
        try{
            Files.writeString(Path.of(RECORDS_FILE), table);
        } catch (IOException e){
            throw new RuntimeException("Failed to save the records: " + e.getMessage());
        }
    }

    public String createRecordTable() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++){
            Record r = records[i];
            s.append(r.userName).append(" ").append(r.scores).append("\n");
        }
        return s.toString();
    }

    public boolean addNewRecord(String name, int score) {
        if (score < 0) throw new IllegalStateException("Negative result cannot be added to the record table");
        if (!isCorrect(name)) return false;
        if (add(name, score, records, pos)) pos++;
        return true;
    }

    /**
     * returns true when number of elements increase, in otherwise returns false
     */
    private static boolean add(String name, int score, Record[] array, int numberElements){
        Record curRecord = new Record(name, score);
        if (numberElements == 10 && score < array[9].scores) return false;
        for (int i = 0; i < numberElements; i++){
            if (score == array[i].scores && name.equals(array[i].userName)) return false;
            if (score >= array[i].scores()){
                boolean isFull = false;
                if (numberElements == 10){
                    isFull = true;
                    numberElements--;
                }
                System.arraycopy(array, i, array, i + 1, numberElements - i);
                array[i] = curRecord;
                return !isFull;
            }
        }
        array[numberElements] = curRecord;
        return true;
    }

    private boolean isCorrect(String res) {
        if (res == null || res.isBlank()) return false;
        int i = 0;
        while (i < res.length()) {
            if (res.charAt(i) == ' ') return false;
            i++;
        }
        return true;
    }
}
