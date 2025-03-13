package SystemRatunkowy;

import JednostkiRatownicze.JednostkaEnum;
import java.util.Set;

public enum TypWypadkuEnum {
    WYPADEK("wypadek", 4, Set.of(JednostkaEnum.J_POLICJA)),
    POZAR("pozar", 3, Set.of(JednostkaEnum.J_STRAZ_POZARNA, JednostkaEnum.J_POLICJA)),
    OMDLENIE("omdlenie", 3, Set.of()),
    ZATRUCIE_GAZEM("zatrucie gazem", 2, Set.of(JednostkaEnum.J_STRAZ_POZARNA)),
    KRADZIEZ("kradziez", 5, Set.of(JednostkaEnum.J_POLICJA));

    private final String nazwa;
    private final int priorytet;
    private final Set<JednostkaEnum> wymaganeJednostki;

    TypWypadkuEnum(String nazwa, int priorytet, Set<JednostkaEnum> wymaganeJednostki) {
        this.nazwa = nazwa;
        this.priorytet = priorytet;
        this.wymaganeJednostki = wymaganeJednostki;
    }

    public String getNazwa() {
        return nazwa;
    }

    public int getPriorytet() {
        return priorytet;
    }

    public Set<JednostkaEnum> getWymaganeJednostki() {
        return wymaganeJednostki;
    }

    @Override
    public String toString() {
        return nazwa;
    }
}