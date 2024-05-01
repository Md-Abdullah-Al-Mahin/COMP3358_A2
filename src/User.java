import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private int games, won, rank;
    private float avg;
    public User(String name, int games, int won, float avg, int rank){
       this.name = name;
       this.games = games;
       this.won = won;
       this.avg = avg;
       this.rank = rank;
    }
    public String getName(){
        return this.name;
    }
    public int getGamesPlayed(){
        return this.games;
    }
    public int getGamesWon(){
        return this.won;
    }
    public float getAvgTimeToGame(){
        return this.avg;
    }
    public int getRank(){
        return this.rank;
    }
}
