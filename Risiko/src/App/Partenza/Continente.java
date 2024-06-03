package App.Partenza;

import java.io.Serializable;
import java.util.ArrayList;

public class Continente implements Serializable {
    private final ArrayList<Territorio> TerritoriRichiesti;
    private final String NomeContinente;

    public Continente(ArrayList<Territorio> Territori, String nome){
        TerritoriRichiesti = Territori;
        NomeContinente = nome;
    }


    public ArrayList<Territorio> getTerritoriRichiesti() {
        return TerritoriRichiesti;
    }

    public String getNomeContinente() {
        return NomeContinente;
    }
}
