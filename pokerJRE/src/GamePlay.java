import java.util.Scanner;

public class GamePlay {
    private int thePot = 0;
    private Players players;
    private final static int SMALLBLIND = 100;
    private final static int BIGBLIND = 200;
    private int id;
    private int theBiggestBet = BIGBLIND;
    private Scanner sc = new Scanner(System.in);

    public GamePlay() {
        players = new Players();
        players.addPlayer(new Player("Bartek",0,true,false));
        players.addPlayer(new Player("Oskar",1,false,true));
        players.addPlayer(new Player("Maciek",2,false,false));
        players.addPlayer(new Player("Wiktor",3,false,false));
    }

    public void game() {
        int counter = 0;
        boolean play = true;
        players.dealCards();
        dealsBlinds();
        id = findBigBlind() + 1;

        while (play) {
            move(id);
            counter++;
            if (counter == players.getPlayers().size()){
                counter = 0;
            }
        }

    }

    private boolean checkbets(){
       for (Player player: players.getPlayers()) {

       }
        return false;
    }

    private void dealsBlinds() {
        Player player;
        for (int i = 0; i < players.getPlayers().size(); i++) {
            player = players.getPlayers().get(i);

            if (player.isSmallBlind()){
                player.setCash(player.getCash() - SMALLBLIND);
                thePot += SMALLBLIND;
            } else if (player.isBigBlind()) {
                player.setCash(player.getCash() - BIGBLIND);
                thePot += BIGBLIND;
            }
        }
    }

    public int getThePot() {
        return thePot;
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

            case 5:
                action(players.getPlayers().get(id));
                break;

                default:
                    System.out.println("zle id");
                    break;

        }
    }


    private void action(Player player){
        int bet = 0;
        System.out.println("Podaj za ile wchodzisz");
        bet = sc.nextInt(); sc.nextLine();

        if (bet == 0 || !player.getInDeal()) {
            player.setInDeal(false);
            return;
        }

        if (bet < theBiggestBet && player.getCash() >= theBiggestBet) {
            System.out.println("bet musi byc wikeszy lub rowny najwiekszemu betowi");
        } else if (bet < theBiggestBet && player.getCash() < theBiggestBet) {
            System.out.println("wchodzisz all in");
            return;
        } else if (bet > player.getCash()) {
            System.out.println("wchodzisz all in");
            return;
        } else if (bet > theBiggestBet && player.getCash() > bet) {
            player.setCash(player.getCash() - bet);
            thePot += bet;
            theBiggestBet = bet;
        }
        id++;
        if (id == players.getPlayers().size())
            id = 0;
    }



}
