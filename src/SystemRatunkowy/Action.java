package SystemRatunkowy;

import JednostkiRatownicze.JednostkaRatownicza;

public class Action {
    private ActionEnum action;
    private Lokalizacja lokalizacja;
    private JednostkaRatownicza jednostka;
    private Zgloszenie zgloszenie;

    public Action(ActionEnum action, Lokalizacja lokalizacja,JednostkaRatownicza jednostka) {
        this.action = action;
        this.jednostka = jednostka;
        this.lokalizacja = lokalizacja;
    }

    public Action(ActionEnum action, Lokalizacja lokalizacja,JednostkaRatownicza jednostka, Zgloszenie zgloszenie) {
        this(action, lokalizacja, jednostka);
        this.zgloszenie = zgloszenie;
    }

    public ActionEnum getAction() {
        return action;
    }

    public Lokalizacja getLokalizacja() {
        return lokalizacja;
    }

    public JednostkaRatownicza getJednostkaRatownicza() {
        return jednostka;
    }

    public Zgloszenie getZgloszenie() {
        return zgloszenie;
    }

    @Override
    public String toString() {
        String className = jednostka.getClass().getSimpleName();
        switch (action) {
            case A_MOVE:
                return String.format("%s o id #%d moves from: %s to: %s", className, jednostka.getId(), lokalizacja, jednostka.getAktualnaLokalizacja());
            case A_HEAL:
                return String.format("%s o id #%d heals 1 person", className, jednostka.getId());
            case A_RETURN:
                return String.format("%s o id #%d will return to the parking spot", className, jednostka.getId());
            case A_DELIVER:
                return String.format("%s o id #%d will deliver the victim  of the #%d incident to the hospital", className,jednostka.getId(), zgloszenie.getId());
            case A_TAKE_PATIENT:
                return String.format("%s o id #%d took the victim of the #%d incident to the hospital", className,jednostka.getId(), zgloszenie.getId());
                default:
            return "UNKNOWN DATA";
        }
    }
}

