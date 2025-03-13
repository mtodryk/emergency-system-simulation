package SystemRatunkowy;

import JednostkiRatownicze.JednostkaEnum;
import JednostkiRatownicze.JednostkaRatownicza;

import java.util.*;

public class Zgloszenie extends Wypadek{
    private int idWypadku;
    private Map<JednostkaEnum, Integer> wymaganeJednostki;
    private List<JednostkaRatownicza> przypisaneJednostki;
    private int liczbaTurWykonanych;
    private int priorytet;

    public Zgloszenie(Lokalizacja lokalizacja, TypWypadkuEnum typ, int liczbaPoszkodowanych, int liczbaWymagajacychHospitalizacji) {
        super(lokalizacja,typ,liczbaPoszkodowanych,liczbaWymagajacychHospitalizacji);
        this.wymaganeJednostki=new HashMap<>();
        this.przypisaneJednostki=new ArrayList<>();
    }

    public void monitorujStatusDzialan() {
        System.out.println("Zgłoszenie " + getTyp()+ " o id #" + getId() + " - tura " + liczbaTurWykonanych);
    }

    public void aktualizujDane() {
        liczbaTurWykonanych++;
        monitorujStatusDzialan();
    }

    public void okreslWymaganeJednostki() {
        if (getLiczbaPoszkodowanych() > 0 || getLiczbaWymagajacychHospitalizacji() > 0) {
            dodajWymJednostke(JednostkaEnum.J_POGOTOWIE, getLiczbaPoszkodowanych() + getLiczbaWymagajacychHospitalizacji());
        }

        for (JednostkaEnum jednostka : getTyp().getWymaganeJednostki()) {
            dodajWymJednostke(jednostka, 1);
        }
    }

    private void dodajWymJednostke(JednostkaEnum jednostka, int liczba) {
        wymaganeJednostki.merge(jednostka, liczba, Integer::sum);
    }

    public void okreslPriorytet() {
        if (getLiczbaWymagajacychHospitalizacji() > 0){
            setPriorytet(1);
        }
        else if(getLiczbaPoszkodowanych()>0){
            setPriorytet(2);
            }
        else  {setPriorytet(getTyp().getPriorytet());}
    }

    public void przypiszJednostke(JednostkaRatownicza jednostka) {
        przypisaneJednostki.add(jednostka);
    }

    public void usunJednostkePrzypisana(JednostkaRatownicza jednostka) {
        przypisaneJednostki.remove(jednostka);
    }

    public void usunJednostkeWymagana(JednostkaRatownicza jednostka) {
        JednostkaEnum typJednostki = jednostka.getTyp();

        if (wymaganeJednostki.containsKey(typJednostki)) {
            int aktualnaLiczba = wymaganeJednostki.get(typJednostki);
            if (aktualnaLiczba > 1) {
                wymaganeJednostki.put(typJednostki, aktualnaLiczba - 1);
            } else {
                wymaganeJednostki.remove(typJednostki);
            }
        }
    }

    public void setIdWypadku(int idWypadku) {
        this.idWypadku = idWypadku;
    }

    public int getIdWypadku() {
        return idWypadku;
    }

    public Map<JednostkaEnum, Integer> getWymaganeJednostki(){
        return this.wymaganeJednostki;
    }
    public List<JednostkaRatownicza> getPrzypisaneJednostki(){
        return this.przypisaneJednostki;
    }

    public void setPrzypisaneJednostki(List<JednostkaRatownicza> przypisaneJednostki) {
        this.przypisaneJednostki = przypisaneJednostki;
    }

    public int getLiczbaTurWykonanych() {
        return liczbaTurWykonanych;
    }

    public void setLiczbaTurWykonanych(int liczbaTurWykonanych) {
        this.liczbaTurWykonanych = liczbaTurWykonanych;
    }

    public int getPriorytet() {
        return priorytet;
    }

    public void setPriorytet(int priorytet) {
        this.priorytet = priorytet;
    }

    public void zmniejszLiczbePoszkodowanych() {
        setLiczbaPoszkodowanych(getLiczbaPoszkodowanych()-1);
    }

    public void zmniejszLiczbeWymHospitalizacji() {
        setLiczbaWymagajacychHospitalizacji(getLiczbaWymagajacychHospitalizacji()-1);
    }
}
