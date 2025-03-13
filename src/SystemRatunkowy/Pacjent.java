package SystemRatunkowy;

public class Pacjent extends Osoba {
    private int liczbaTurDoWyjscia;

    public Pacjent(Osoba osoba) {
        setPesel(osoba.getPesel());
    }

    public int getLiczbaTurDoWyjscia() {
        return liczbaTurDoWyjscia;
    }

    public void setLiczbaTurDoWyjscia(int liczbaTurDoWyjscia) {
        this.liczbaTurDoWyjscia = liczbaTurDoWyjscia;
    }

    public void zmniejszLiczbaTurDoWyjscia() {
        setLiczbaTurDoWyjscia(getLiczbaTurDoWyjscia()-1);
    }
}