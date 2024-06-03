package App.FasePreliminare;

import App.Partenza.Continente;
import App.Partenza.Giocatore;
import App.Partenza.Obiettivi;

import java.util.ArrayList;
import java.util.Collections;

import static App.FasePreliminare.InizializzazioneTerritori.getContinente;


public class InizializzazioneObiettivi {

    private final ArrayList<Obiettivi> Obiettivi = new ArrayList<>();       //Obiettivi del gioco

    /**
     * Permette la creazione degli obiettivi passando i parametri per la realizzazione e la descrizione per la comprensione
     * dell'obiettivo al giocatore
     */
    protected void CreaObiettivi(){
        this.Obiettivi.add(new Obiettivi("Devi distruggere le armate Bianche, se impossibile conquista 24 territori", 0 , "Bianche", 24, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi distruggere le armate Arancioni, se impossibile conquista 24 territori", 0, "Arancioni", 24, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi distruggere le armate Lilla, se impossibile conquista 24 territori", 0, "Lilla", 24, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi distruggere le armate Nere, se impossibile conquista 24 territori", 0, "Nere", 24, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi distruggere le armate Blu, se impossibile conquista 24 territori", 0, "Blu", 24, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi distruggere le armate Verdi, se impossibile conquista 24 territori", 0, "Verdi", 24, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi conquistare la totalità dell' Europa e del Sud America più un terzo continente a tua scelta", 1, null, 0, getContinente(2), getContinente(1) ));
        this.Obiettivi.add(new Obiettivi("Devi conquistare la totalità dell' Asia e del Sud america", 0, null, 0, getContinente(4), getContinente(1)));
        this.Obiettivi.add(new Obiettivi("Devi conquistare 24 territori", 0, null, 24, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi conquistare la totalità del Nord America e dell' Africa", 0, null, 0, getContinente(0), getContinente(3)));
        this.Obiettivi.add(new Obiettivi("Devi conquistare la totalità dell' Europa e dell' Oceania più un terzo continente a tua scelta", 1, null, 0, getContinente(2), getContinente(5)));
        this.Obiettivi.add(new Obiettivi("Devi conquistare la totalità dell' Asia e dell' Africa", 0, null, 0, getContinente(4), getContinente(3)));
        this.Obiettivi.add(new Obiettivi("Devi conquistare 18 territori ed occuparne ciascuno di essi con almeno 2 armate", 0, null, 18, (Continente) null));
        this.Obiettivi.add(new Obiettivi("Devi conquistare la totalità del Nord America e dell' Oceania", 0, null, 0, getContinente(0), getContinente(5)));
    }


    protected void Assegnazione_Obiettivi(){

        Collections.shuffle(Obiettivi);
        int x = 0;
        for(Giocatore i: InizializzazioneGiocatori.getGiocatori()){
            i.setObiettivo(Obiettivi.get(x));
            x++;
        }
    }


}
