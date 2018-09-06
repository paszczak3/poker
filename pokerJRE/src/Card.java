import java.util.Objects;

public class Card {
    private Colors color;
    private Figures figure;
    private int level;

    public Card(Colors color, Figures figure) {
        this.color = color;
        this.figure = figure;
        switch (figure) {
            case ACE:
                level = 14;
                break;
            case KING:
                level = 13;
                break;
            case QUEEN:
                level = 12;
                break;
            case JACK:
                level = 11;
                break;
            case TEN:
                level = 10;
                break;
            case SIX:
                level = 6;
                break;
            case TWO:
                level = 2;
                break;
            case FIVE:
                level = 5;
                break;
            case FOUR:
                level = 4;
                break;
            case NINE:
                level = 9;
                break;
            case EIGHT:
                level = 8;
                break;
            case SEVEN:
                level = 7;
                break;
            case THREE:
                level = 3;
                break;
        }
    }

    public Colors getColor() {
        return color;
    }

    public Figures getFigure() {
        return figure;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return color + " " + figure + ", ";
    }

}
