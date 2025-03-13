package SystemRatunkowy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Wypadek {
    private int id;
    private String czas;
    private Lokalizacja lokalizacja;
    private String status;
    private TypWypadkuEnum typ;
    private int liczbaPoszkodowanych;
    private int liczbaWymagajacychHospitalizacji;

    public Wypadek(Lokalizacja lokalizacja, TypWypadkuEnum typ, int liczbaPoszkodowanych, int liczbaWymagajacychHospitalizacji) {
        this.czas = getCurrentTime();
        this.lokalizacja = lokalizacja;
        this.typ = typ;
        this.liczbaPoszkodowanych = liczbaPoszkodowanych;
        this.liczbaWymagajacychHospitalizacji = liczbaWymagajacychHospitalizacji;
    }

    public String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yy");
        return LocalDateTime.now().format(formatter);
    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCzas() {
        return czas;
    }

    public void setCzas(String czas) {
        this.czas = czas;
    }

    public Lokalizacja getLokalizacja() {
        return lokalizacja;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        System.out.println(getClass().getSimpleName() +" #" + id + " changes its status to: "+ status);
        this.status = status;
    }

    public TypWypadkuEnum getTyp() {
        return typ;
    }

    public void setTyp(TypWypadkuEnum typ) {
        this.typ = typ;
    }

    public int getLiczbaPoszkodowanych() {
        return liczbaPoszkodowanych;
    }

    public void setLiczbaPoszkodowanych(int liczbaPoszkodowanych) {
        this.liczbaPoszkodowanych = liczbaPoszkodowanych;
    }

    public int getLiczbaWymagajacychHospitalizacji() {
        return liczbaWymagajacychHospitalizacji;
    }

    public void setLiczbaWymagajacychHospitalizacji(int liczbaWymagajacychHospitalizacji) {
        this.liczbaWymagajacychHospitalizacji = liczbaWymagajacychHospitalizacji;
    }
}
