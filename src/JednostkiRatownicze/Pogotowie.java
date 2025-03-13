package JednostkiRatownicze;
import SystemRatunkowy.*;

import java.util.ArrayList;
import java.util.List;

public class Pogotowie extends JednostkaRatownicza {
    Szpital docelowySzpital;
    public Pogotowie(int id, Lokalizacja lokalizacja, Dyspozytor dyspozytor) {
        super(id, lokalizacja, dyspozytor);
        this.docelowySzpital = null;
    }

    @Override
    public void ustawParametry(){
        this.setStatus("dostepny");
        this.setTyp(JednostkaEnum.J_POGOTOWIE);
        this.setLiczbaTurPozostalych(0);
        this.setMaxDrogaZaTure(250);
        this.setDlugoscDzialan(JednostkaEnum.J_POGOTOWIE.getDlugoscDzialan());
    }

    @Override
    public List<Action> dzilaniaNaMiejscu(){
        List<Action> result = new ArrayList<>();
        if(docelowySzpital!=null){
            result.add(new Action(ActionEnum.A_TAKE_PATIENT,getAktualnaLokalizacja(),this, getZgloszenie()));
            docelowySzpital=null;
        }else {
            if(getZgloszenie().getLiczbaWymagajacychHospitalizacji() > 0){
                setLiczbaTurPozostalych(getLiczbaTurPozostalych() + 1);
                result.add(new Action(ActionEnum.A_DELIVER,getAktualnaLokalizacja(),this, getZgloszenie()));

                docelowySzpital = getDyspozytor().getNajblizszySzpital(getAktualnaLokalizacja());
                setDocelowaLokalizacja(docelowySzpital.getLokalizacja());
            }
            else if(getZgloszenie().getLiczbaPoszkodowanych() > 0){
                result.add(new Action(ActionEnum.A_HEAL,getAktualnaLokalizacja(),this, getZgloszenie()));
            }
        }

        setLiczbaTurPozostalych(getLiczbaTurPozostalych() - 1);
        if(getLiczbaTurPozostalych() == 0 && docelowySzpital == null) {
            result.addAll(powrotDoBazy());
        }

        return result;
    }
}
