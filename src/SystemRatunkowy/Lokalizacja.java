package SystemRatunkowy;

public class Lokalizacja {
    private int x;
    private int y;

    public Lokalizacja(Integer xPosition, Integer yPosition) {
        this.x = (xPosition != null) ? xPosition : 0;
        this.y = (yPosition != null) ? yPosition : 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public double odleglosc(Lokalizacja innaLokalizacja) {
        return Math.sqrt(Math.pow(this.x - innaLokalizacja.x, 2) + Math.pow(this.y - innaLokalizacja.y, 2));
    }
}