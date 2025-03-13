package SystemRatunkowy;

import java.util.ArrayList;
import java.util.List;

public class Szpital {
    private int liczbaMiejsc;
    private int dostepneMiejsca;
    private Lokalizacja lokalizacja;
    private List<Pacjent> przypisaniPacjenci;

    public Szpital(int liczbaMiejsc, Lokalizacja lokalizacja) {
        this.liczbaMiejsc = liczbaMiejsc;
        this.dostepneMiejsca = liczbaMiejsc;
        this.lokalizacja = lokalizacja;
        this.przypisaniPacjenci = new ArrayList<>();
    }

    public int getDostepneMiejsca() {
        return dostepneMiejsca;
    }

    public Lokalizacja getLokalizacja() {
        return lokalizacja;
    }

    public void setLokalizacja(Lokalizacja lokalizacja) {
        this.lokalizacja = lokalizacja;
    }

    public List<Pacjent> getPrzypisaniPacjenci() {
        return przypisaniPacjenci;
    }

    public void przypiszPacjenta(Osoba osoba) {
        if (dostepneMiejsca > 0) {
            Pacjent pacjent = new Pacjent(osoba);
            przypisaniPacjenci.add(pacjent);
            pacjent.setLiczbaTurDoWyjscia(2);
            dostepneMiejsca--;
            System.out.println("Pacjent " + pacjent.getPesel() + " został przypisany do szpitala.");
        }
    }

    public void wypiszPacjenta(Pacjent pacjent) {
        if (przypisaniPacjenci.contains(pacjent)) {
            przypisaniPacjenci.remove(pacjent);
            dostepneMiejsca++;
            System.out.println("Pacjent " + pacjent.getPesel() + " został wypisany ze szpitala.");
        }
    }
}