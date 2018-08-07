import java.util.*;


public class Deck {
    private final static int AMOUNT_OF_CARDS = 52;
    private List<Card> deck;


    public Deck() {
        deck = new ArrayList<>(AMOUNT_OF_CARDS);
        for (Colors color: Colors.values()) {
            for (Figures figure: Figures.values()) {
                deck.add(new Card(color,figure));
            }
        }
        Collections.shuffle(deck);
    }

    public List<Card> getDeck() {
        return deck;
    }
}
