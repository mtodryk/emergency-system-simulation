package SystemRatunkowy;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PoleGraficzne extends JPanel {
    private List<Obrazek> obrazki;
    private static class Obrazek {
        int x, y;
        Image image;
        public Obrazek(int x, int y, Image image) {
            this.x = x;
            this.y = y;
            this.image = image;
        }
    }

    public PoleGraficzne(int x, int y) {
        this.setPreferredSize(new Dimension(x, y));
        this.obrazki = new ArrayList<>();
    }

    public void dodajObrazek(Lokalizacja lokalizacja, String path) {
        ImageIcon ogimage = new ImageIcon(path);
        Image image = ogimage.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        obrazki.add(new Obrazek(lokalizacja.getX(), lokalizacja.getY(), image));
        repaint();
    }

    public void wyczyscPole() {
        obrazki.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Obrazek obrazek : obrazki) {
            g.drawImage(obrazek.image, obrazek.x, obrazek.y, this);
        }
    }
}
