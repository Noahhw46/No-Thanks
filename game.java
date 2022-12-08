import java.util.ArrayList;
import java.util.*;
import java.util.Scanner;

public class game {

    public static void main(String[] args) {
        gameLoop();
    }

    public static void gameLoop() {

        // get total number of players first.
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of players in the game");
        int numOfPlayers = scan.nextInt();
        String winner = "";
        int highestScore = 100000;

        // get the number of chips we pass to each object based on the num of players
        int numOfChips = 0;
        if (numOfPlayers >= 3 && numOfPlayers <= 5) {
            numOfChips = 11;
        }
        if (numOfPlayers == 6) {
            numOfChips = 9;
        }
        if (numOfPlayers == 7) {
            numOfChips = 7;
        }

        // create a new object for each player and add their name and numOfChips
        Scanner in = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<Player>();
        for (int i = 0; i < numOfPlayers; i++) {
            Player p = new Player();

            System.out.println("Player name: ");
            String name = in.nextLine();

            p.setName(name);
            p.setChips(numOfChips);

            players.add(p);

        }
        // create and shuffle deck
        ArrayList<Integer> deck = new ArrayList<>();
        ArrayList<Integer> removedCards = new ArrayList<Integer>();
        for (int i = 3; i <= 35; i++) {
            deck.add(i);
        }
        // randomly remove 9 cards
        while (removedCards.size() <= 9) {
            int random = (int) ((Math.random() * 33) + 3);
            if (!removedCards.contains(random)) {
                removedCards.add(random);
            }
        }

        Collections.sort(removedCards);

        for (int i = 0; i < deck.size() - 1; i++) {
            for (int j = 0; j < removedCards.size() - 1; j++) {
                if (deck.get(i) == removedCards.get(j)) {
                    deck.remove(i);
                }
            }
        }

        Collections.shuffle(deck);

        // begin actual game
        int countChips = 0; // we can just store the number of chips that have been added by keeping track
                            // of the number of no thanks's per round.
        while (deck.size() > 0) {
            // Start game by showing the first player in the list the pertinent information
            // for making a turn:
            // What card is faceup, how many chips they have left, and what cards they have.
            Scanner input = new Scanner(System.in);
            System.out.println();
            System.out.println("It is your turn: " + players.get(0).getName());
            System.out.println("The card is " + deck.get(0));
            System.out.println("There are " + countChips + " chips on the current card");
            System.out.println("You have " + players.get(0).getChips() + " chips left.");
            System.out.println("You have these cards: " + players.get(0).getCards());
            System.out.println("Number of cards left in deck: " + deck.size()); // Print out this relevant info
            if (players.get(0).getChips() > 0) { // first check is if the player has chips
                System.out.println(
                        "Enter yes if you would like to take the card or no if you would like to pass and add a chip.");
                String ans = scan.nextLine();
                // Logic if they decide they want to take the card:

                if (ans.equalsIgnoreCase("yes")) {
                    players.get(0).addCard(deck.get(0)); // add the card to their collection of cards
                    players.get(0).addChips(countChips); // add whatever chips have been added to the card to theur
                                                         // collection of chips
                    countChips = 0; // set the chip counter back to zero.
                    deck.remove(0); // remove the card from the first index in the deck. allowing the next iteration
                                    // of the loop to show the next card.
                    Collections.rotate(players, 1); // this allows the order to change so a new person
                                                    // will move first.

                }
                // Logic if they decide they don't want to take the card:
                else if (ans.equalsIgnoreCase("no")) {
                    players.get(0).decrementChips(); // take away one of the players chips
                    countChips++; // add chips to the counter
                    System.out.println("You have " + players.get(0).getChips() + " chips left.");
                    Collections.rotate(players, 1); // same thing to make it so a new player goes first

                }
                // logic if they enter something besides yes or no:
                else {
                    System.out.println("Please answer yes or no.");
                }
            } else { // if they don't have chips then we force them to take the card and change
                     // players
                System.out.println("Sorry, you have no chips left and are forced to take this card.");
                players.get(0).addCard(deck.get(0));
                players.get(0).addChips(countChips);
                countChips = 0;
                deck.remove(0);
                Collections.rotate(players, 1);
            }
        }
        // once the while loop terminates we loop through the players in our list and
        // print out their score.
        System.out.println("");
        for (int i = 0; i < players.size(); i++) {
            System.out.println(players.get(i).getName() + "'s score is: "
                    + players.get(i).calcScore(players.get(i).getCards(), players.get(i).getChips()));
        }
        // find and print the winner
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getScore() <= highestScore) {
                highestScore = players.get(i).getScore();
                winner = players.get(i).getName();
            }
        }
        System.out.println("The winner is: " + winner);

    }

    public static void testCalcLogic() {
        int chippers = 5;
        int finalScore = 0;

        ArrayList<Integer> testCards = new ArrayList<>();

        testCards.add(18);
        testCards.add(19);
        testCards.add(21);
        testCards.add(22);
        testCards.add(20);
        testCards.add(10);
        testCards.add(5);
        testCards.add(4);
        testCards.add(2);
        testCards.add(1);

        Collections.sort(testCards);
        System.out.println(testCards);

        for (int i = testCards.size() - 1; i >= 0; i--) {
            if (testCards.get(i) != testCards.get(0)) {
                if (testCards.get(i) - 1 == testCards.get(i - 1)) {
                    testCards.remove(testCards.get(i));
                }
            }
        }
        System.out.println(testCards);

        for (int i = 0; i < testCards.size(); i++) {
            finalScore += testCards.get(i);
        }
        finalScore = finalScore - chippers;
        System.out.println(finalScore);

    }
}
