package utils;

import java.util.ArrayList;
import java.util.List;

class SortRecordsArray {
    private final static int LENGTH = 10;
    private final Record[] records = new Record[LENGTH];
    private int pos = 0;

    public void add(Record r){
        int score = r.scores();
        for (int i = 0; i < pos; i++){
            if (score >= records[i].scores()){
                shift(i);
                records[i] = r;
                pos++;
                return;
            }
        }
        if (pos == 10) return;
        records[pos] = r;
        pos++;
    }

    public boolean contains(Record r){
        for (int i = 0; i < pos; i++){
            if (records[i].equals(r)) return true;
        }
        return false;
    }

    public ArrayList<Record> getArrayList(){
        return new ArrayList<>(List.of(records));
    }

    private void shift(int start){
        if (pos == 10) pos--;
        if (pos - start >= 0) System.arraycopy(records, start, records, start + 1, pos - start);
    }
}
