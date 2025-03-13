package JednostkiRatownicze;

import SystemRatunkowy.*;

public class Policja extends JednostkaRatownicza {
    public Policja(int id, Lokalizacja lokalizacja, Dyspozytor dyspozytor) {
        super(id, lokalizacja, dyspozytor);
    }

    @Override
    public void ustawParametry(){
        this.setStatus("dostepny");
        this.setTyp(JednostkaEnum.J_POLICJA);
        this.setLiczbaTurPozostalych(0);
        this.setMaxDrogaZaTure(250);
        this.setDlugoscDzialan(JednostkaEnum.J_POLICJA.getDlugoscDzialan());
    }

}