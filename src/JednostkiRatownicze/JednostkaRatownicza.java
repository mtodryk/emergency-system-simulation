package JednostkiRatownicze;
import SystemRatunkowy.*;

import java.util.ArrayList;
import java.util.List;

public class JednostkaRatownicza {
    private int id;
    private Lokalizacja miejsceStacjonowania;
    private Lokalizacja aktualnaLokalizacja;
    private String status;
    private JednostkaEnum typ;
    private int liczbaTurPozostalych;
    private int dlugoscDzialan;
    private Lokalizacja docelowaLokalizacja;
    private int maxDrogaZaTure;
    private Dyspozytor dyspozytor;
    private Zgloszenie zgloszenie;

    public JednostkaRatownicza(int id, Lokalizacja lokalizacja, Dyspozytor dyspozytor) {
        this.id = id;
        this.miejsceStacjonowania = lokalizacja;
        this.docelowaLokalizacja = lokalizacja;
        this.aktualnaLokalizacja = lokalizacja;
        this.dyspozytor = dyspozytor;
        this.ustawParametry();
    }

    public void ustawParametry(){
    }

    public void przypiszDoZdarzenia(Zgloszenie zgloszenie) {
        setStatus("zadysponowany");
        setZgloszenie(zgloszenie);
        setDocelowaLokalizacja(zgloszenie.getLokalizacja());
        setLiczbaTurPozostalych(this.getDlugoscDzialan());
    }

    public List<Action> powrotDoBazy() {
        List<Action> result = new ArrayList<>();

        result.add(new Action(ActionEnum.A_RETURN,getAktualnaLokalizacja(),this, getZgloszenie()));

        setStatus("dostepny");
        setZgloszenie(null);
        this.docelowaLokalizacja = miejsceStacjonowania;

        return result;
    }

    public List<Action> dzialaniaPodczasTury(){
        List<Action> result = new ArrayList<>();
        if(!aktualnaLokalizacja.equals(docelowaLokalizacja)){
            result.addAll(jedzDoMiejscaZgloszenia());
        }else{
            if (getZgloszenie()!=null){
                result.addAll(dzilaniaNaMiejscu());
            }
        }
        return result;
    }

    public List<Action> dzilaniaNaMiejscu(){
        List<Action> result = new ArrayList<>();

        if(--liczbaTurPozostalych==0) {
            result.addAll(powrotDoBazy());
        }

        return result;
    }

    public List<Action> jedzDoMiejscaZgloszenia() {
        List<Action> result = new ArrayList<>();

        double maxOdlegloscZaTure = getMaxDrogaZaTure();
        double odlegloscFaktyczna = aktualnaLokalizacja.odleglosc(docelowaLokalizacja);

        if (odlegloscFaktyczna <= maxOdlegloscZaTure) {
            aktualnaLokalizacja = docelowaLokalizacja;
        } else {
            double ratio = maxOdlegloscZaTure / odlegloscFaktyczna;
            int newX = (int) Math.round(aktualnaLokalizacja.getX() + (docelowaLokalizacja.getX() - aktualnaLokalizacja.getX()) * ratio);
            int newY = (int) Math.round(aktualnaLokalizacja.getY() + (docelowaLokalizacja.getY() - aktualnaLokalizacja.getY()) * ratio);

            aktualnaLokalizacja = new Lokalizacja(newX, newY);
        }

        result.add(new Action(ActionEnum.A_MOVE, aktualnaLokalizacja, this));

        return result;
    }

    //getters setters
    public JednostkaEnum getTyp() {
        return typ;
    }

    public Zgloszenie getZgloszenie() {
        return zgloszenie;
    }

    public String getStatus() {
        return status;
    }

    public Lokalizacja getAktualnaLokalizacja() {
        return aktualnaLokalizacja;
    }

    public int getId() {
        return id;
    }

    public Dyspozytor getDyspozytor() {
        return dyspozytor;
    }

    public int getLiczbaTurPozostalych() {
        return liczbaTurPozostalych;
    }

    public void setZgloszenie(Zgloszenie zgloszenie) {
        this.zgloszenie = zgloszenie;
    }

    public void setDocelowaLokalizacja(Lokalizacja docelowaLokalizacja) {
        this.docelowaLokalizacja = docelowaLokalizacja;
    }

    public void setLiczbaTurPozostalych(int liczbaTurPozostalych) {
        this.liczbaTurPozostalych = liczbaTurPozostalych;
    }

    public int getDlugoscDzialan() {
        return dlugoscDzialan;
    }

    public int getMaxDrogaZaTure() {
        return maxDrogaZaTure;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTyp(JednostkaEnum typ) {
        this.typ = typ;
    }

    public void setDlugoscDzialan(int dlugoscDzialan) {
        this.dlugoscDzialan = dlugoscDzialan;
    }

    public void setMaxDrogaZaTure(int maxDrogaZaTure) {
        this.maxDrogaZaTure = maxDrogaZaTure;
    }
}
