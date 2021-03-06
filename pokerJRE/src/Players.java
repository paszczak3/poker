import java.util.ArrayList;
import java.util.List;

public class Players implements PlayerHandle{
    private final static int MAX_PLAYERS = 6;
    private List<Player> players;
    private Deck deck;

    public Players() {
        players = new ArrayList<>();
    }

    @Override
    public void dealCards() {
        deck = new Deck();
        int cards = 0;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).addCard(deck.getDeck().get(cards));
                cards++;
            }
        }

    }

    @Override
    public void addPlayer(Player player) {
        if (players.size() == MAX_PLAYERS)
            System.out.println("cant add new player, there is maximum amount");
        else
            players.add(player);
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
