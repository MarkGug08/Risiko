package App.Gestione_Turno;

import App.FasePreliminare.InizializzazioneGiocatori;
import App.Partenza.Continente;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import java.util.ArrayList;
import java.util.HashSet;




public class GestioneControlloObiettivi {


    /**
     * Permette di controllare l'obiettivo del giocatore, tramite un controllo dei parametri si ottiene
     * la tipologia e si andrà a verifica che i requisiti sono stati rispettati
     * @param giocatore giocatore che controlla il proprio obiettivo
     * @return ritorna true o false in base all'esito del controllo
     */
    protected boolean ControlloObiettivi(Giocatore giocatore) {

        //obiettivo armata da distruggere
        String Controllo_Armata = giocatore.getObiettivo().getColoreArmataAvversariaDaDistruggere();                 //riceve l'armata che il giocatore deve aver sconfitto

        //controllo della conquista di determinati continenti
        ArrayList<Continente> Controllo_Continenti = giocatore.getObiettivo().getContinentiDaConquistare();        //Riceve un arraylist con i continenti che deve aver conquistato



        if (Controllo_Armata != null) {             //obiettivo di distruggere una determinata armata

            return ControlloObiettivoArmata(Controllo_Armata, giocatore);

        } else if (Controllo_Continenti.get(0) != null) {                     // Obiettivo di conquistare i continenti

            return ControlloObiettivoContinenti(Controllo_Continenti, giocatore);

        } else {                  //Obiettivo di conquistare i territori

            return ControlloTerritori(giocatore);

        }//controllo degli obiettivi

    }

    /**
     * Permette di controllare se il giocatore ha sconfitto l'armata assegnata o in caso di eliminazione
     * di un altro giocatore o dell'assenza di quel colore dell'armata controllerà il numero di territori
     * @param Armata_da_distruggere rappresenta il colore dell'armata da sconfiggere e si verifica se il giocatore la possiede
     * @param giocatore giocatore possessore di questo obiettivo
     * @return ritorna true o false in base al controllo dell'obiettivo
     */
    protected boolean ControlloObiettivoArmata(String Armata_da_distruggere, Giocatore giocatore) {

        HashSet<String> Giocatori_eliminati = giocatore.getArmateEliminate();                //ritorna elenco di tutte le armate eliminate
        int territori_giocatore = giocatore.getTerritoriInPossessoGiocatore().size();         //numero dei territori del giocatore
        String colore_armata_giocatore = giocatore.getColoreArmataGiocatore();              //colore dell'armata del giocatore

        int controllo_presenza_armata_da_distruggere = 0;                           //controllo se l'armata è presente nel gioco e che non sia del giocatore che ha l'obiettivo
        for (Giocatore i : InizializzazioneGiocatori.getGiocatori()) {
            if (i.getColoreArmataGiocatore().contains(Armata_da_distruggere) && !(colore_armata_giocatore.equals(Armata_da_distruggere))) {
                controllo_presenza_armata_da_distruggere++;
            }
        }
        if (Giocatori_eliminati.contains(Armata_da_distruggere)) {       //se il giocatore ha eliminato il giocatore sull'obiettivo ritorna true
            return true;
        }

        if (controllo_presenza_armata_da_distruggere == 0) {      //nel caso il giocatore con l'obiettivo ha quella stessa armata o non è presente nel gioco bisogna conquistare 24 territori
            return territori_giocatore >= 24;
        }

        return false;
    }


    /**
     * Permette se il giocatore possiede i continenti richiesti (obbligatori e a scelta)
     * @param Continenti_richiesti continenti in possesso del giocatore
     * @param giocatore possessore dell'obiettivo
     * @return esito del controllo
     */
    protected boolean ControlloObiettivoContinenti(ArrayList<Continente> Continenti_richiesti, Giocatore giocatore) {
        int contatore = 0;
        HashSet<Continente> Continenti_giocatore = giocatore.getContinentiInTuoPossesso();        //scarica in un hashset i continenti del giocatore

        String ContinenteRichiesto1 = Continenti_richiesti.get(0).getNomeContinente();
        String ContinenteRichiesto2 = Continenti_richiesti.get(1).getNomeContinente();

        for(Continente i: Continenti_giocatore){
            if(i.getNomeContinente().equals(ContinenteRichiesto1)){
                contatore++;
            }

            if(i.getNomeContinente().equals(ContinenteRichiesto2)){
                contatore++;
            }
        }


        int Continente_scelta = giocatore.getObiettivo().getContinenteScelta();                                   //valore per vedere se il giocatore deve conquistare un terzo continente

        //gli obiettivi hanno due continenti precisi da conquistare
        if (contatore == 2) {           //se il giocatore li ha entrambi va avanti

            if (Continente_scelta == 0) {     //se il giocatore non ha il continente a scelta oltre quelli obbligatori avrà vinto
                return true;
            } else {
                //se il giocatore deve conquistare anche un terzo continente ovvero quello a scelta
                return Continenti_giocatore.size() > 2;
            }
        }

        return false;
    }


    /**
     * Permette se il giocatore ha conquistato 24 territori o 18 con almeno due armate sopra
     * @param giocatore possessore dell'obiettivo
     * @return esito
     */
    protected boolean ControlloTerritori(Giocatore giocatore) {
        int Territori_da_conquistare = giocatore.getObiettivo().getTerritoriObbligatoriDaConquistare();        //numero di territori che il giocatore deve conquistare

        int Territori_giocatore_totali = giocatore.getTerritoriInPossessoGiocatore().size();       //contatore di tutti i territori posseduti
        int contatore_armate_territorio = 0;        //contatore sul territorio del giocatore

        //esistono solo due obiettivi che richiedono tot territori che sono o 18 o 24

        if (Territori_da_conquistare == 18 && Territori_giocatore_totali >= 18) {         //obiettivo che richiede due armate su almeno 18 territori

            for (Territorio Territorio_giocatore : giocatore.getTerritoriInPossessoGiocatore()) {
                if (Territorio_giocatore.getNumeroArmate() > 1) {
                    contatore_armate_territorio++;          //conta quanti territori hanno almeno due armate
                } else {
                    contatore_armate_territorio--;
                }
            }

            if (contatore_armate_territorio >= 18) {          //se ci sono almeno 18 territori con due armate l'obiettivo è raggiunto
                return true;
            }

        }

        //obiettivo che richiede solo 24 territori
        return Territori_da_conquistare == 24 && Territori_giocatore_totali >= 24;
    }
}
