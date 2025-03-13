package JednostkiRatownicze;

import SystemRatunkowy.*;

public class StrazPozarna extends JednostkaRatownicza {
    public StrazPozarna(int id, Lokalizacja lokalizacja, Dyspozytor dyspozytor) {
        super(id, lokalizacja, dyspozytor);
    }

    @Override
    public void ustawParametry(){
        this.setStatus("dostepny");
        this.setTyp(JednostkaEnum.J_STRAZ_POZARNA);
        this.setLiczbaTurPozostalych(0);
        this.setMaxDrogaZaTure(250);
        this.setDlugoscDzialan(JednostkaEnum.J_STRAZ_POZARNA.getDlugoscDzialan());
    }

}
