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
    private boolean isWait = false;
    private int points = 0;
    private String hand = "";

    public String getHand() {
        return hand;
    }

    public void setHand(String hand) {
        this.hand = hand;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean getInDeal() {
        return inDeal;
    }

    public void setInDeal(boolean inDeal) {
        this.inDeal = inDeal;
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


    public Card getFirstCard(){
        return cards[0];
    }

    public Card getSecondCard(){
        return cards[1];
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

    public void resetCard() {
        cards[0] = null;
        cards[1] = null;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
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

    public void setSmallBlind(boolean smallBlind) {
        this.smallBlind = smallBlind;
    }

    public void setBigBlind(boolean bigBlind) {
        this.bigBlind = bigBlind;
    }

    public boolean isInDeal() {
        return inDeal;
    }

    public boolean isWait() {
        return isWait;
    }

    public void setWait(boolean wait) {
        isWait = wait;
    }


}
