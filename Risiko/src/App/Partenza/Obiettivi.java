package App.Partenza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Obiettivi implements Serializable {

    private final String DescrizioneObiettivo;
    private final int ContinenteScelta;
    private final int TerritoriObbligatoriDaConquistare;
    private final String ColoreArmataAvversariaDaDistruggere;
    private final ArrayList<Continente> ContinentiDaConquistare = new ArrayList<>();


    public Obiettivi(String descrizione, Integer scelta , String Armata, Integer Territori, Continente...Continenti){
        this.DescrizioneObiettivo = descrizione;
        this.ContinentiDaConquistare.addAll(Arrays.asList(Continenti));
        this.ContinenteScelta = scelta;
        this.ColoreArmataAvversariaDaDistruggere = Armata;
        this.TerritoriObbligatoriDaConquistare = Territori;
    }

    //Descrizione dell'obiettivo
    public String getDescrizione() {
        return DescrizioneObiettivo;
    }

    //Continente a scelta che può variare se esserci o meno
    public int getContinenteScelta() {
        return ContinenteScelta;
    }

    //Continenti da conquistare
    public ArrayList<Continente> getContinentiDaConquistare(){
        return ContinentiDaConquistare;
    }

    //armata nemica da distruggere
    public String getColoreArmataAvversariaDaDistruggere() {
        return ColoreArmataAvversariaDaDistruggere;
    }

    //Territori da conquistare
    public int getTerritoriObbligatoriDaConquistare() {
        return TerritoriObbligatoriDaConquistare;
    }




}
