package App.Gestione_Turno;


import App.FasePreliminare.InizializzazioneCarte;
import App.FasePreliminare.InizializzazioneGiocatori;
import GUI.*;
import App.Partenza.Carta;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;

import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatori;
import static App.Gestione_Turno.GestioneTurno.setNumeroGiocatore;


public class GestioneGiocatore {

    private static final Semaphore SemaforoOperazioniGestioneTurno = new Semaphore(0);
    //semaforo che rimane in attesa che il giocatore decida che operazione eseguire

    /**
     * Permette la rotazione dei giocatori al termine di ogni turno nel caso permettendo al giro di riniziare
     * @param x numero del giocatore corrispondente alla posizione dell'arraylist del giocatore
     * @return ritorna la posizione e il programma passerà il turno a quel determinato giocatore
     */
    protected int Rotazione(int x) {
        if(x == getGiocatori().size() - 1){ //nel caso in cui si raggiunge l'ultimo giocatore rinizia il giro
            DatiGiocatore.setTurno(0);  //rinizializza il turno

            return 0;
        }

        return x+1;
    }

    /**
     * Permette di assegnare la carta a un giocatore dopo che esso conquista almeno un territorio
     * @param giocatore giocatore attuale del turno
     * @param territoriIniziali territori all'inizio del turno
     */
    protected void Assegnazione_carta(Giocatore giocatore, int territoriIniziali){

        //se i territori sono diversi dai territori iniziali sarà assegnata la carta
        if(giocatore.getTerritoriInPossessoGiocatore().size() - territoriIniziali != 0) {

            giocatore.setMazzoCarteGiocatore(InizializzazioneCarte.getMazzoCarte().get(0));

            InizializzazioneCarte.getMazzoCarte().remove(0);  //rimuovo la carta dal mazzo per evitare duplicati

        }

        if(InizializzazioneCarte.getMazzoCarte().isEmpty()){     //il metodo oltre ad assegnare, rimuove la carta dal mazzo, Quando il mazzo finisce se ne ricrea un altro
            InizializzazioneCarte.CreaMazzo();
        }


    }


    /**
     * Blocca il giocatore nel momento in cui deve inserire le armate nel suo turno
     * @param giocatore giocatore attuale
     */
    protected void Stop(Giocatore giocatore){
        AzioniIcone.setControlloreAzioneBottone(1);     //rappresenta l'azione di inserire le armate
        AzioneChiusura.setConfermaInserimento(0);       //inizializza conferma inserimento un valore di conferma


        while(AzioneChiusura.getConfermaInserimento() == 0){ //se il tasto viene premuto il valore diventa 1 e il ciclo si ferma

            new DatiGiocatore().BarraInformazioni(giocatore);   //aggiornamento dei dati del giocatore

            try {
                SemaforoOperazioniGestioneTurno.acquire();  //fermo il semaforo
            } catch (InterruptedException e) {

            }
        }

        AzioniIcone.setControlloreAzioneBottone(2); //si passa alla seconda fase del turno

    }

    /**
     * Permette di eliminare un giocatore dal gioco ottenendo le carte che il giocatore possedeva e il colore della sua
     * armata nel caso dell'obiettivo
     * @param GiocatoreDaEliminare Giocatore che dovrà essere rimosso dall'elenco dei giocatori e che passerà le carte al giocatore
     *                             che lo ha eliminato
     * @param attaccante giocatore che ha sconfitto il giocatore eliminandogli tutti i territori e che otterrà le sue carte
     */
    protected static void RimozioneGiocatore(Giocatore GiocatoreDaEliminare, Giocatore attaccante) {
        int x = 0;

        if (GiocatoreDaEliminare.getTerritoriInPossessoGiocatore().isEmpty()) { //controllo che il giocatore non ha più territori quindi si procede all'eliminazione

            String Armata_eliminata = GiocatoreDaEliminare.getColoreArmataGiocatore();  //colore dell'armata del giocatore sconfitto
            attaccante.setArmateEliminate(Armata_eliminata);

            if (GiocatoreDaEliminare.getMazzoCarteGiocatore() != null) {    //nel caso il giocatore disponga di carte esse saranno assegnate al giocatore che l'ha sconfitto
                Iterator<Carta> iterator = GiocatoreDaEliminare.getMazzoCarteGiocatore().iterator();
                while (iterator.hasNext()) {
                    Carta carta = iterator.next();
                    attaccante.setMazzoCarteGiocatore(carta);
                    iterator.remove();

                }
            }

            int GiocatoreAttuale = RitornaIndiceGiocatore(attaccante);
            int GiocatoreEliminato = RitornaIndiceGiocatore(GiocatoreDaEliminare);
            
            getGiocatori().remove(GiocatoreDaEliminare);

            if(GiocatoreAttuale > GiocatoreEliminato){
                setNumeroGiocatore(GiocatoreAttuale - 1);
            }

        }
    }

    /**
     * Permette di ritornare la posizione specifica dell'icona corrispondente a quel determinato territorio
     * @param numero territorio da cercare tra le icone
     * @return posizione dell'icona
     */
    protected static int RitornaArmata(Territorio numero){
        ArrayList<Armata_GUI> t = Interfaccia.getArmataGUI();
        for(int x = 0; x < 42; x++){
            if(numero.getNomeTerritorio().equals(t.get(x).getNome())){
                return x;
            }
        }
        return 0;
    }

    private static int RitornaIndiceGiocatore(Giocatore giocatore){
        int cont = 0;
        for(Giocatore i: getGiocatori()){
            if(i.getNomeGiocatore().equals(giocatore.getNomeGiocatore())){
                return cont;
            }
            cont++;
        }

        return -1;
    }

    public static void RisvegliaSemaforoGestioneArmateTurno(){
        SemaforoOperazioniGestioneTurno.release();
    }
}
