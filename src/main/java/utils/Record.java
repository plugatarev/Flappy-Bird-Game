package utils;
public record Record(String userName, int scores) {
    @Override
    public String toString(){
        return userName + " " + scores;
    }
}
