import java.util.ArrayList;
import java.util.Collection;
import java.util.*;
public class Player {
    private int numChips;
    private String name;
    private int score;
    private ArrayList<Integer> cards = new ArrayList<>();
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setChips(int numChips) {
        this.numChips = numChips;
    }
    public void decrementChips() {
        numChips = numChips - 1;
    }
    public int getChips() {
        return numChips;
    }
    public void addChips(int numChipsToAdd) {
        numChips = numChips + numChipsToAdd;
    }
    public void addScore(int numToAdd) {
        score = score + numToAdd;
    }
    public void subtractScore(int numToSubtract) {
        score = score - numToSubtract;
    }
    public int getScore() {
        return calcScore(cards, numChips);
    }
    public void addCard(int card) {
        cards.add(card);
    }
    public ArrayList<Integer> getCards() {
        return cards;
    }
    public int calcScore(ArrayList<Integer> cards, int chips) {
        int finalScore = 0;
        Collections.sort(cards);
        for (int i = cards.size() - 1; i >= 0; i--) {
            if (cards.get(i) != cards.get(0)) {
                if (cards.get(i) - 1 == cards.get(i - 1)) {
                    cards.remove(cards.get(i));
                }
            }
        }
        for (int i = 0; i < cards.size(); i++) {
            finalScore += cards.get(i);
        }
        finalScore = finalScore - chips;
        return finalScore;
    }
    @Override
    public String toString() {
        return this.name + " " + this.numChips + " ";
    }
}