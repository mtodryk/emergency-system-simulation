package SystemRatunkowy;

import JednostkiRatownicze.*;

import java.util.*;

public class Dyspozytor {
    private List<Wypadek> wypadkiAktywne;
    private List<Wypadek> wypadkiHistoryczne;
    private List<Zgloszenie> zgloszeniaAktywne;
    private List<Zgloszenie> zgloszeniaZakonczone;
    private List<JednostkaRatownicza> jednostkiRatownicze;
    private List<Szpital> szpitale;
    private Random random = new Random();
    
    public Dyspozytor() {
        wypadkiAktywne = new ArrayList<>();
        wypadkiHistoryczne = new ArrayList<>();
        zgloszeniaAktywne = new ArrayList<>();
        zgloszeniaZakonczone = new ArrayList<>();
        jednostkiRatownicze = new ArrayList<>();
        szpitale = new ArrayList<>();
    }

    public void generujLosoweWypadki(int liczba) {
        TypWypadkuEnum[] typy = TypWypadkuEnum.values();

        for (int i = 0; i < liczba; i++) {
            Lokalizacja lokalizacja = generujLosowaLokalizacje();
            TypWypadkuEnum typWypadku = typy[random.nextInt(typy.length)];
            int liczbaOsobPoszkodowanych = random.nextInt(3);
            int liczbaOsobWymagHosp = random.nextInt(2);

            dodajWypadek(new Wypadek(lokalizacja, typWypadku, liczbaOsobPoszkodowanych, liczbaOsobWymagHosp));
        }
    }

    private Lokalizacja generujLosowaLokalizacje() {
        return new Lokalizacja(random.nextInt(480), random.nextInt(480));
    }

    public void dodajJednostke(JednostkaRatownicza jednostka) {
        jednostkiRatownicze.add(jednostka);
    }

    public void dodajWypadek(Wypadek wypadek) {
        wypadek.setId(1+wypadkiAktywne.size()+wypadkiHistoryczne.size());
        wypadek.setStatus("aktywny");
        wypadkiAktywne.add(wypadek);
    }

    public void dodajSzpital(Szpital szpital) {
        szpitale.add(szpital);
    }

    public void generujZgloszenie(Wypadek wypadek) {
        Zgloszenie zgloszenie = new Zgloszenie(
                wypadek.getLokalizacja(),
                wypadek.getTyp(),
                wypadek.getLiczbaPoszkodowanych(),
                wypadek.getLiczbaWymagajacychHospitalizacji());
        zgloszenie.setIdWypadku(wypadek.getId());
        zgloszenie.setId(1+zgloszeniaAktywne.size()+zgloszeniaZakonczone.size());
        zgloszenie.setCzas(zgloszenie.getCurrentTime());
        zgloszenie.setStatus("aktywny");
        zgloszenie.okreslWymaganeJednostki();
        zgloszenie.okreslPriorytet();
        zgloszeniaAktywne.add(zgloszenie);
        System.out.println("Wypadek " + wypadek.getTyp() + " o ID: "+ wypadek.getId() + " zostal zgenerowany jako zgloszenie o id: " + zgloszenie.getId());
    }

    public void symulujTure() {
        wypadkiAktywne.forEach(wypadek -> {
            if (!czyWypadekZostalZgenerowany(wypadek)) {
                generujZgloszenie(wypadek);
            }
        });
        sortujZgloszeniaAktywne();
        przypiszJednostki();
        monitorujZgloszenia();
    }

    public void sortujZgloszeniaAktywne() {
        zgloszeniaAktywne.sort((z1, z2) -> Integer.compare(z1.getPriorytet(), z2.getPriorytet()));
    }

    public void przypiszJednostki() {
        List<JednostkaRatownicza> dostepneJednostki = sprawdzDostepnoscJednostek();

        for (Zgloszenie zgloszenie : zgloszeniaAktywne) {
            for (Map.Entry<JednostkaEnum, Integer> entry : zgloszenie.getWymaganeJednostki().entrySet()) {
                JednostkaEnum typJednostki = entry.getKey();
                Integer liczbaJednostek = entry.getValue();

                if (liczbaJednostek <= 0) {
                    continue;
                }

                for (int i = 0; i < liczbaJednostek; i++) {
                    JednostkaRatownicza najblizsza = znajdzNajblizszaJednostke(dostepneJednostki, typJednostki, zgloszenie.getLokalizacja());
                    if (najblizsza != null) {
                        najblizsza.przypiszDoZdarzenia(zgloszenie);
                        zgloszenie.przypiszJednostke(najblizsza);
                        dostepneJednostki.remove(najblizsza);
                    }
                }
            }
        }
    }

    private JednostkaRatownicza znajdzNajblizszaJednostke(List<JednostkaRatownicza> jednostki, JednostkaEnum typJednostki, Lokalizacja lokalizacja) {
        return jednostki.stream()
                .filter(j -> j.getTyp().equals(typJednostki))
                .min(Comparator.comparingDouble(j -> j.getAktualnaLokalizacja().odleglosc(lokalizacja)))
                .orElse(null);
    }

    public void monitorujZgloszenia() {
        List<Action> actions = new ArrayList<>();

        //zmiana danych zgloszenia
        for (Zgloszenie zgloszenie : zgloszeniaAktywne) {
            zgloszenie.aktualizujDane();
        }

        //dzialania jednostek
        for (JednostkaRatownicza jednostka : jednostkiRatownicze) {
            actions.addAll(jednostka.dzialaniaPodczasTury());
            for (Action a : actions) {
                makeAction(a);
            }
            actions.clear();
        }

        //sprawdzamy status zgloszen
        for (Zgloszenie zgloszenie : List.copyOf(zgloszeniaAktywne)) {
            if (zgloszenie.getWymaganeJednostki().isEmpty() && zgloszenie.getPrzypisaneJednostki().isEmpty()) {
                zmienStatusZgloszenia(zgloszenie);
                zmienStatusWypadku(getWypadekById(zgloszenie.getIdWypadku()));
            }
        }

        //sprawdzamy pacjentow w szpitalach
        for (Szpital szpital : szpitale){
            List<Pacjent> przypisaniPacjenci = List.copyOf(szpital.getPrzypisaniPacjenci());
            for (Pacjent pacjent : przypisaniPacjenci){
                pacjent.zmniejszLiczbaTurDoWyjscia();
                if (pacjent.getLiczbaTurDoWyjscia()==0){
                    szpital.wypiszPacjenta(pacjent);
                }
            }
        }

    }

    public void makeAction(Action action) {
        System.out.println(action);
        if (action.getAction() == ActionEnum.A_RETURN){
            action.getZgloszenie().usunJednostkePrzypisana(action.getJednostkaRatownicza());
            action.getZgloszenie().usunJednostkeWymagana(action.getJednostkaRatownicza());
        }
        if (action.getAction() == ActionEnum.A_HEAL){
            action.getZgloszenie().zmniejszLiczbePoszkodowanych();
        }
        if (action.getAction() == ActionEnum.A_DELIVER){
            action.getZgloszenie().zmniejszLiczbeWymHospitalizacji();
        }
        if (action.getAction() == ActionEnum.A_TAKE_PATIENT){
            przypiszPacjentaDoSzpitala(action.getLokalizacja());
        }
    }

    public void przypiszPacjentaDoSzpitala(Lokalizacja lokalizacja){
        Szpital szpital = getNajblizszySzpital(lokalizacja);
        Osoba poszkodowanaOsoba = new Osoba();
        szpital.przypiszPacjenta(poszkodowanaOsoba);
    }

    public Szpital getNajblizszySzpital(Lokalizacja lokalizacja) {
        return szpitale.stream()
                .filter(szpital -> szpital.getDostepneMiejsca() > 0)
                .min(Comparator.comparingDouble(szpital -> szpital.getLokalizacja().odleglosc(lokalizacja)))
                .orElse(null);
    }

    public Wypadek getWypadekById(int id){
        for (Wypadek wypadek : wypadkiAktywne){
            if (wypadek.getId() == id){
                return wypadek;
            }
        }
        return null;
    }

    public void zmienStatusWypadku(Wypadek wypadek) {
        if (wypadek != null){
            wypadek.setStatus("historyczny");
            wypadkiAktywne.remove(wypadek);
            wypadkiHistoryczne.add(wypadek);
        }
    }

    public void zmienStatusZgloszenia(Zgloszenie zgloszenie) {
        zgloszenie.setStatus("zakonczony");
        zgloszeniaAktywne.remove(zgloszenie);
        zgloszeniaZakonczone.add(zgloszenie);
    }

    public List<JednostkaRatownicza> sprawdzDostepnoscJednostek() {
        List<JednostkaRatownicza> dostepneJednostki = new ArrayList<>();
        for (JednostkaRatownicza jednostka : jednostkiRatownicze) {
            if (jednostka.getStatus().equals("dostepny")) {
                dostepneJednostki.add(jednostka);
            }
        }
        return dostepneJednostki;
    }

    public void zaktualizujPole(PoleGraficzne pole) {
        pole.wyczyscPole();

        for (Zgloszenie zgloszenie : zgloszeniaAktywne) {
            Lokalizacja lokalizacja = zgloszenie.getLokalizacja();
            String obrazek = switch (zgloszenie.getTyp()) {
                case TypWypadkuEnum.POZAR -> "content/fire.png";
                case TypWypadkuEnum.OMDLENIE -> "content/health.png";
                case TypWypadkuEnum.KRADZIEZ -> "content/crime.png";
                case TypWypadkuEnum.WYPADEK -> "content/accident.png";
                default -> "content/warning.png";
            };
            pole.dodajObrazek(lokalizacja, obrazek);
        }

        for (JednostkaRatownicza jednostka : jednostkiRatownicze) {
            Lokalizacja lokalizacja = jednostka.getAktualnaLokalizacja();
            String obrazek = switch (jednostka.getTyp()) {
                case J_POGOTOWIE-> "content/ambulance.png";
                case J_POLICJA -> "content/police.png";
                case J_STRAZ_POZARNA -> "content/firetruck.png";
                default -> "content/police.png";
            };
            pole.dodajObrazek(lokalizacja, obrazek);
        }

        for (Szpital szpital : szpitale) {
            pole.dodajObrazek(szpital.getLokalizacja(), "content/hospital.png");
        }
    }

    public boolean czyWypadekZostalZgenerowany(Wypadek wypadek){
        for (Zgloszenie zgloszenie :zgloszeniaAktywne){
            if (zgloszenie.getIdWypadku() == wypadek.getId()){
                return true;
            }
        }
        return false;
    }
}
