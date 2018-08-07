import java.util.Arrays;

public class Player {
    private final static int AMOUNTOFCARDS = 2;
    private String name;
    private Card cards[] = new Card[AMOUNTOFCARDS];
    private int id;
    private boolean smallBlind;
    private boolean bigBlind;
    private int cash = 6600;
    private boolean inDeal = true;
    private int bet;

    public boolean getInDeal() {
        return inDeal;
    }

    public void setInDeal(boolean inDeal) {
        this.inDeal = inDeal;
    }

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int id, boolean smallBlind, boolean bigBlind) {
        this.name = name;
        this.id = id;
        this.smallBlind = smallBlind;
        this.bigBlind = bigBlind;
    }

    public String getName() {
        return name;
    }

    public Card[] getCards() {
        return cards;
    }

    public void addCard(Card card){
        if (cards[1] != null){
            System.out.println("nie mozna dodac karty");
            return;
        }

        if (cards[0] == null)
            cards[0] = card;
        else
            cards[1] = card;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public void smallBlindOff(){
        smallBlind = false;
    }

    public void smallBlindOn(){
        smallBlind = true;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", cards=" + Arrays.toString(cards) +
                '}';
    }

    public boolean isSmallBlind() {
        return smallBlind;
    }

    public boolean isBigBlind() {
        return bigBlind;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }

    public int getId() {
        return id;
    }
}
