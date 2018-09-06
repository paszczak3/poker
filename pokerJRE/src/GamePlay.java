import java.util.*;
import java.util.stream.Collectors;


public class GamePlay {
    private int round  = 1;
    private int thePot = 0;
    private Players players;
    private final static int SMALLBLIND = 100;
    private final static int BIGBLIND = 200;
    private int id;
    private int theBiggestBet = BIGBLIND;
    private Scanner sc = new Scanner(System.in);
    private List<Card> cardsOnTheMid;
    private List<Card> playerAndMidCards;



    public GamePlay() {
        players = new Players();
        players.addPlayer(new Player("Bartek",0,true,false));
        players.addPlayer(new Player("Oskar",1,false,true));
        players.addPlayer(new Player("Maciek",2,false,false));
        players.addPlayer(new Player("Wiktor",3,false,false));
        cardsOnTheMid = new ArrayList<>();
        playerAndMidCards = new ArrayList<>();
    }

    public void game() {
        int counter;
        boolean wholeGame = true;
        boolean oneRound;
        boolean blinding;

        while (wholeGame) {
            oneRound = true;
            counter = 0;
            players.dealCards();
            dealsBlinds();

            System.out.println("Round numer: " + round);
            round++;
            while(oneRound) {
                id = findBigBlind() + 1;
                if (id == players.getPlayers().size())
                    id = 0;

                blinding = true;

                //skonczone blindowanie
                while (blinding) {
                    move(id);
                    blinding = checkingPlayersInGame();
                    if (blinding)
                        blinding = checkingForWaitingPlayers();
                    else {
                        reset();
                        oneRound = false;
                        break;
                    }
                }
                players.getPlayers().stream().filter(p -> p.getInDeal()).forEach(p -> p.setWait(false));
                //tutaj ma byc roztrzygniecie kto wygrywa
                if (counter == 3) {
                    determineWinner();
                    reset();
                    break;
                }

                //rozdanie kart
                dealsCards(counter);
                counter++;
            }
        }
    }

    private void determineWinner(){
        playerAndMidCards.addAll(cardsOnTheMid);
        System.out.println("Karty na sordku");
        System.out.println(cardsOnTheMid);

        players.getPlayers().stream().filter(p -> p.getInDeal()).forEach(m -> {
            playerAndMidCards.add(m.getFirstCard());
            playerAndMidCards.add(m.getSecondCard());

            determineHand(m);

            playerAndMidCards.remove(m.getFirstCard());
            playerAndMidCards.remove(m.getSecondCard());

        });

    }

    private void determineHand(Player player) {
        Card card = null;
        int counter = 0;
        playerAndMidCards.sort(Comparator.comparing(Card::getFigure));
        System.out.println(" ");
        System.out.println(player.getFirstCard() + " " + player.getSecondCard());

        //poker
        for (int i = 0; i < 6; i++) {

        }


        //kareta
        for (int i = 0; i < 4; i++) {
            if (playerAndMidCards.get(i).getFigure() == playerAndMidCards.get(i + 1).getFigure() &&
                    playerAndMidCards.get(i + 1 ).getFigure() == playerAndMidCards.get(i + 2 ).getFigure() &&
                    playerAndMidCards.get(i + 2).getFigure() == playerAndMidCards.get(i + 3).getFigure()) {

                System.out.println("kareta"); player.setHand("kareta");
                setPointForPlayer(player,playerAndMidCards.get(i));
                return;
            }
        }



        //ful

        //kolor
        for(int i = 6; i > 0; i-- ) {
            if (playerAndMidCards.get(i).getColor().equals(playerAndMidCards.get(i - 1))) {
                if (counter == 0)
                    card = playerAndMidCards.get(i);
                counter++;
            }

            if (counter == 5) {
                System.out.println("kolor"); player.setHand("kolor");
                setPointForPlayer(player,card);
                return;
            }

        }


        counter = 0;


        //street
        for (int i = 6; i > 0; i--) {
            if (playerAndMidCards.get(i).getLevel() - 1 == playerAndMidCards.get(i - 1).getLevel()) {
                if (counter == 0)
                    card = playerAndMidCards.get(i);
                counter++;
            }

            if (counter == 5){
                System.out.println("street"); player.setHand("street");
                setPointForPlayer(player,card);
                return;
            }
        }

        counter = 0;


        //sprawdzanie trojki
        for (int i = 0; i < 5; i++) {
            if (playerAndMidCards.get(i).getFigure() == playerAndMidCards.get(i + 1).getFigure() &&
                    playerAndMidCards.get(i + 1 ).getFigure() == playerAndMidCards.get(i + 2 ).getFigure()) {

                System.out.println("trojka"); player.setHand("trojka");
                setPointForPlayer(player,playerAndMidCards.get(i));
                return;
            }
        }



        //sprawdzanie dwoch par
        for (int i = 6; i > 0; i--) {
            if (playerAndMidCards.get(i).getFigure() == playerAndMidCards.get(i - 1).getFigure())
                counter++;
            if (counter == 1)
                card = playerAndMidCards.get(i);
            if (counter == 2) {
                System.out.println("dwie pary"); player.setHand("dwiePary");
                setPointForPlayer(player,card);
                return;
            }


        }

        coupleOfCard(player);
        if (player.getHand() != null)
            return;

        //jedna karta
        System.out.println("jedna karta"); player.setHand("nic");
        setPointForPlayer(player,playerAndMidCards.get(6));




    }

    private void setPointForPlayer(Player player, Card card) {
        switch (player.getHand()){
            case "nic":
                player.setPoints(card.getLevel());
                break;
            case "para":
                player.setPoints(card.getLevel() + 15);
                break;
            case "dwiePary":
                player.setPoints(card.getLevel() + 30);
                break;
            case "trojka":
                player.setPoints(card.getLevel() + 45);
                break;
            case "street":
                player.setPoints(card.getLevel() + 60);
                break;
            case "kolor":
                player.setPoints(card.getLevel() + 75);
                break;

            case "ful":
                break;

            case "kareta":
                player.setPoints(card.getLevel() + 90);
                break;

        }
    }

    private boolean checkingPlayersInGame() {
        //sprawdzam czy tylko jeden jest w grze
        long temp2 =  players.getPlayers().stream().filter(Player::getInDeal).count();

        if (temp2 == 1) {
            players.getPlayers().stream().filter(Player::getInDeal).forEach(p -> {
                p.setCash(p.getCash() + thePot);
            });
            thePot = 0;
            theBiggestBet = 0;
            return false;
        }
        return true;
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

    private boolean checkingForWaitingPlayers(){
        //sprawdzam czy zawdonicy czekaja
        boolean temp = !players.getPlayers().stream().filter(Player::getInDeal).allMatch(Player::isWait);

        return temp;
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

    private void move(int id)  {

        if (!players.getPlayers().get(id).isInDeal()) {
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
                    System.out.println("blad");
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
            player.setCash(0); thePot += bet;
            return;
        } else if (bet > player.getCash()) {
            System.out.println("wchodzisz all in");
            player.setCash(0); thePot += bet;
            if (theBiggestBet < bet)
                theBiggestBet = bet;
            return;
        } else if (player.getBet() >= theBiggestBet && player.getCash() > bet) {
            player.setCash(player.getCash() - bet);
            thePot += bet;
            theBiggestBet = bet;
        }
        abc();
    }


    //musze zwrocic nastepna osobe ktora jest inDeal
    private void abc() {
        id++;
        if (players.getPlayers().size() == id)
            id = 0;
        if (players.getPlayers().get(id).isInDeal())
            return;
        else abc();

    }

    private void reset(){
        if (players.getPlayers().size() > 2) {
            for (int i = 0; i < players.getPlayers().size(); i++) {
                if (players.getPlayers().get(i).isBigBlind()) {
                    if (i + 1 < players.getPlayers().size()) {
                        players.getPlayers().get(i + 1).setBigBlind(true);
                        players.getPlayers().get(i).setBigBlind(false);
                    } else {
                        players.getPlayers().get(0).setBigBlind(true);
                        players.getPlayers().get(players.getPlayers().size() - 1).setBigBlind(false);
                    }
                    break;

                }
            }

            for (int i = 0; i < players.getPlayers().size(); i++) {
                if (players.getPlayers().get(i).isSmallBlind()) {
                    if (i + 1 < players.getPlayers().size()) {
                        players.getPlayers().get(i + 1).setSmallBlind(true);
                        players.getPlayers().get(i).setSmallBlind(false);
                    } else {
                        players.getPlayers().get(0).setSmallBlind(true);
                        players.getPlayers().get(players.getPlayers().size() - 1).setSmallBlind(false);
                    }
                    break;

                }
            }



        } else {
            if (players.getPlayers().get(0).isSmallBlind()) {
                players.getPlayers().get(0).setSmallBlind(false);
                players.getPlayers().get(0).setBigBlind(true);
                players.getPlayers().get(1).setBigBlind(false);
                players.getPlayers().get(1).setSmallBlind(true);
            } else {
                players.getPlayers().get(0).setSmallBlind(true);
                players.getPlayers().get(0).setBigBlind(false);

                players.getPlayers().get(1).setBigBlind(true);
                players.getPlayers().get(1).setSmallBlind(false);
            }
        }


        players.getPlayers().forEach(p -> {
            if (p.getCash() == 0)
                p.setInDeal(false);
            else
                p.setInDeal(true);

            p.resetCard();
            p.setWait(false);
            p.setBet(0);
        });

        //zerwoanie kard na stole
        cardsOnTheMid = new ArrayList<>();
        playerAndMidCards = new ArrayList<>();
    }

    private void coupleOfCard(Player player) {
        for (int i = 0; i < 6; i++) {
            if (playerAndMidCards.get(i).getFigure() == playerAndMidCards.get(i + 1).getFigure()){
                System.out.println("para"); player.setHand("para");
                setPointForPlayer(player,playerAndMidCards.get(i));
                return;
            }
        }
    }




}
