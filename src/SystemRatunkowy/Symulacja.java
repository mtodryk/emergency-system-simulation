package SystemRatunkowy;

import JednostkiRatownicze.Policja;
import JednostkiRatownicze.Pogotowie;
import JednostkiRatownicze.StrazPozarna;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Symulacja {

    public static void main(String[] args) {
        Dyspozytor dyspozytor = new Dyspozytor();

        dodajJednostki(dyspozytor);
        dodajSzpitale(dyspozytor);
        dyspozytor.generujLosoweWypadki(5);

        uruchomGUI(dyspozytor);
    }

    private static void dodajJednostki(Dyspozytor dyspozytor) {
        dyspozytor.dodajJednostke(new StrazPozarna(1, new Lokalizacja(100, 250), dyspozytor));
        dyspozytor.dodajJednostke(new Pogotowie(2, new Lokalizacja(200, 250), dyspozytor));
        dyspozytor.dodajJednostke(new Pogotowie(3, new Lokalizacja(300, 250), dyspozytor));
        dyspozytor.dodajJednostke(new Policja(4, new Lokalizacja(400, 250), dyspozytor));
    }

    private static void dodajSzpitale(Dyspozytor dyspozytor) {
        dyspozytor.dodajSzpital(new Szpital(5, new Lokalizacja(50, 50)));
        dyspozytor.dodajSzpital(new Szpital(5, new Lokalizacja(450, 450)));
    }

    private static void uruchomGUI(Dyspozytor dyspozytor) {
        JFrame mapa = new JFrame("System Ratunkowy");
        mapa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mapa.setLayout(null);

        PoleGraficzne pole = new PoleGraficzne(500, 500);
        pole.setBounds(0, 0, 500, 500);
        mapa.add(pole);

        JButton nextTurnButton = new JButton("Nastepna tura");
        nextTurnButton.setBounds(200, 520, 150, 30);
        dyspozytor.zaktualizujPole(pole);

        nextTurnButton.addActionListener((ActionEvent e) -> {
            System.out.println("======NOWA TURA========");
            dyspozytor.symulujTure();
            dyspozytor.zaktualizujPole(pole);
            System.out.println("=======================\n");
        });

        mapa.add(nextTurnButton);
        mapa.setSize(600, 600);
        mapa.setLocationRelativeTo(null);
        mapa.setVisible(true);
    }
}