package App.Gestione_file;

import App.FasePreliminare.InizializzazioneCarte;
import App.FasePreliminare.InizializzazioneGiocatori;
import GUI.AzioneChiusura;
import GUI.AzioniIcone;
import GUI.DatiGiocatore;
import GUI.Interfaccia;
import App.Gestione_Turno.GestioneTurno;


import static App.FasePreliminare.InizializzazioneTerritori.setContinenti;
import static App.FasePreliminare.InizializzazioneTerritori.setTerritori;

public class RipristinoGioco {
    Interfaccia interfaccia = new Interfaccia();
    Salvataggio salvataggio = new Salvataggio();

    public void Inizio(){


        salvataggio.CaricaPartita();

        GestioneTurno.setNumeroGiocatore(Salvataggio.getTurno());

        DatiGiocatore.setTurno(Salvataggio.getTurno());

        InizializzazioneGiocatori.setGiocatori(Salvataggio.getGiocatori());

        setTerritori(Salvataggio.getTerritori());

        InizializzazioneCarte.setMazzoCarte(Salvataggio.getMazzoCarte());

        setContinenti(Salvataggio.getContinenti());

        AzioneChiusura.setControllore(Salvataggio.getGiocatori().size());

        interfaccia.Inizializzazione();

       AzioniIcone.setControlloreAzioneBottone(1);


    }
}
