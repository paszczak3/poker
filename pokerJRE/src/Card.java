import java.util.Objects;

public class Card {
    private Colors color;
    private Figures figure;

    public Card(Colors color, Figures figure) {
        this.color = color;
        this.figure = figure;
    }

    public Colors getColor() {
        return color;
    }

    public Figures getFigure() {
        return figure;
    }

    @Override
    public String toString() {
        return "Card{" +
                "color=" + color +
                ", figure=" + figure +
                '}';
    }

}
