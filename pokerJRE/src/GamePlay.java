import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GamePlay {
    private int thePot = 0;
    private Players players;
    private final static int SMALLBLIND = 100;
    private final static int BIGBLIND = 200;
    private int id;
    private int theBiggestBet = BIGBLIND;
    private Scanner sc = new Scanner(System.in);
    private List<Card> cardsOnTheMid = new ArrayList<>();
    private boolean betting = true;
    private boolean play = true;

    public GamePlay() {
        players = new Players();
        players.addPlayer(new Player("Bartek",0,true,false));
        players.addPlayer(new Player("Oskar",1,false,true));
        players.addPlayer(new Player("Maciek",2,false,false));
        players.addPlayer(new Player("Wiktor",3,false,false));
    }

    public void game() {
        int counter = 0;
        players.dealCards();
        dealsBlinds();
        id = findBigBlind() + 1;

        while (play) {

            while (betting) {
                move(id);
                betting = checkBets();
            }

            //musze sprawdzic czy zostal tylko jeden zawodnik ktory nie spasowal

            if (counter <= 0) {
                dealsCards(counter);
                counter++;
            } else {
                //roztrzygniecie
            }

            betting = true;
        }

    }

    private void dealsCards(int counter) {

        switch (counter) {
            case 0:
                cardsOnTheMid.add(players.getDeck().getDeck().get(9));
                cardsOnTheMid.add(players.getDeck().getDeck().get(10));
                cardsOnTheMid.add(players.getDeck().getDeck().get(11));
                break;

            case 1:
                cardsOnTheMid.add(players.getDeck().getDeck().get(13));
                break;

            case 2:
                cardsOnTheMid.add(players.getDeck().getDeck().get(15));
                break;

                default:
                    System.out.println("blad");
                    break;
        }

    }

    private boolean checkBets(){
        return !players.getPlayers().stream().filter(Player::getInDeal).allMatch(Player::isWait);
    }

    private void dealsBlinds() {
        Player player;
        for (int i = 0; i < players.getPlayers().size(); i++) {
            player = players.getPlayers().get(i);

            if (player.isSmallBlind()){
                player.setCash(player.getCash() - SMALLBLIND);
                thePot += SMALLBLIND;
                player.setBet(SMALLBLIND);
            } else if (player.isBigBlind()) {
                player.setCash(player.getCash() - BIGBLIND);
                thePot += BIGBLIND;
                player.setBet(BIGBLIND);
            }
        }
    }

    private int findBigBlind(){
        Player player;
        for (int i = 0; i < players.getPlayers().size(); i++) {
            player = players.getPlayers().get(i);
            if (player.isBigBlind()) {
                return player.getId();
            }
        }

        return -1;
    }

    private void move(int id) {

        if (!players.getPlayers().get(id).getInDeal()) {
            abc();
            return;
        }

        switch (id) {
            case 0:
                action(players.getPlayers().get(id));
                break;

            case 1:
                action(players.getPlayers().get(id));
                break;

            case 2:
                action(players.getPlayers().get(id));
                break;

            case 3:
                action(players.getPlayers().get(id));
                break;

            case 4:
                action(players.getPlayers().get(id));
                break;

                default:
                    System.out.println("zle id");
                    break;
        }
    }


    private void action(Player player){
        int bet = 0;
        System.out.println(player.getName() + " twoj stack " + player.getCash());
        System.out.println("Podaj za ile wchodzisz, jesli chcesz poczekac podaj - 1");
        bet = sc.nextInt(); sc.nextLine();

        if (bet == -1) {
            player.setWait(true);
            abc();
            return;
        }

        player.setBet(player.getBet() + bet);

        if (bet == 0) {
            player.setInDeal(false);
            abc();
            return;
        }

        if (player.getBet() < theBiggestBet && player.getCash() >= theBiggestBet) {
            System.out.println(player.getBet());
            System.out.println("bet musi byc wikeszy lub rowny najwiekszemu betowi");
        } else if (bet < theBiggestBet && player.getCash() < theBiggestBet) {
            System.out.println("wchodzisz all in");
            return;
        } else if (bet > player.getCash()) {
            System.out.println("wchodzisz all in");
            return;
        } else if (player.getBet() >= theBiggestBet && player.getCash() > bet) {
            System.out.println("test");
            player.setCash(player.getCash() - bet);
            thePot += bet;
            theBiggestBet = bet;
        }
        abc();
    }

    private void abc() {
        id++;
        if (id == players.getPlayers().size())
            id = 0;
    }
    

}
