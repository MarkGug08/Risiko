package App.FasePreliminare;


import GUI.Interfaccia;


public class FasePreliminare {
    InizializzazioneArmate InizializzaArmate = new InizializzazioneArmate();
    InizializzazioneGiocatori InizializzaGiocatori = new InizializzazioneGiocatori();
    InizializzazioneTerritori InizializzaTerritori = new InizializzazioneTerritori();

    InizializzazioneObiettivi InizializzaObiettivi = new InizializzazioneObiettivi();
    Interfaccia interfaccia = new Interfaccia();


    public void Inizio() {

        //Creazione campo da gioco
        InizializzaTerritori.CreaTerritori();
        InizializzaTerritori.AssegnaTerritoriAdiacenti();
        InizializzaTerritori.CreaContinenti();
        InizializzaObiettivi.CreaObiettivi();
        InizializzazioneCarte.CreaMazzo();

        InizializzaGiocatori.CreaGiocatore();

        InizializzaTerritori.Assegnazione_Territori();

        InizializzaObiettivi.Assegnazione_Obiettivi();
        InizializzaArmate.Assegnazione_Armate();

        InizializzaTerritori.InizializzaTerritori();     //setto un'armata per ogni territorio

        interfaccia.Inizializzazione();     //inizializzazione dell'interfaccia del gioco

        InizializzaArmate.InserimentoIniziale();    //permette ai giocatori di inserire le armate






    }
}
