package JednostkiRatownicze;

public enum JednostkaEnum {
    J_POGOTOWIE("pogotowie",1),
    J_POLICJA("policja",2),
    J_STRAZ_POZARNA("straz pozarna",3);

    private final String nazwa;
    private final int dlugoscDzialan;

    JednostkaEnum(String nazwa, int dlugoscDzialan) {
        this.nazwa = nazwa;
        this.dlugoscDzialan = dlugoscDzialan;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getDlugoscDzialan() {
        return dlugoscDzialan;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}

