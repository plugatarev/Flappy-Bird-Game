package utils;

import java.io.*;
import java.util.stream.Collectors;

public record Records(SortRecordsArray records) {
    private static final String RECORDS_FILE = "records";
    private static Records instance = null;

    public static Records getInstance() {
        if (instance != null) return instance;
        instance = loadScores();
        return instance;
    }

    public static Records loadScores() {
        SortRecordsArray records = new SortRecordsArray();
        try (BufferedReader reader = new BufferedReader(new FileReader(RECORDS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tmp = line.split(" ");
                records.add(new Record(tmp[0], Integer.parseInt(tmp[1])));
            }
        } catch (IOException e) {
            System.out.println("Couldn't load old records");
            return new Records(new SortRecordsArray());
        }
        return new Records(records);
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
        return records.getArrayList().stream().map(java.lang.Record::toString).collect(Collectors.joining("\n"));
    }

    public boolean addNewRecord(String name, int score) {
        if (score == 0) throw new IllegalStateException("Zero result cannot be added to the record table");
        if (!isCorrect(name)) return false;
        Record cur = new Record(name, score);
        if (records.contains(cur)) return true;
        records.add(cur);
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
