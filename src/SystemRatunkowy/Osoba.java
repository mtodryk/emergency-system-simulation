package SystemRatunkowy;
import java.util.Random;

public class Osoba {
    private String pesel;

    public Osoba() {
        this.pesel = generatePesel();
    }
    public Osoba(String pesel) {
        this.pesel = pesel;
    }

    public String generatePesel() {
        Random random = new Random();
        StringBuilder peselBuilder = new StringBuilder();
        for (int i = 0; i < 11; i++) {
            peselBuilder.append(random.nextInt(10));
        }
        return peselBuilder.toString();
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return "Osoba{" + pesel + '}';
    }
}