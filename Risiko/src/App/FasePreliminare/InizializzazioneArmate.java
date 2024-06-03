package App.FasePreliminare;

import App.Partenza.Continente;
import GUI.AzioniIcone;
import GUI.DatiGiocatore;
import GUI.MessaggiErrore;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import java.util.concurrent.Semaphore;

import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatori;
import static App.FasePreliminare.InizializzazioneTerritori.getContinenti;
import static App.Gestione_Turno.GestioneSceltaGiocatore.RicercaTerritorio;


public class InizializzazioneArmate {

    private static final Semaphore SemaforoInizializzaArmate = new Semaphore(0);
    //Permette di bloccare il thread dello scorrimento del programma nel mentre che il giocatore inserisce le armate


    /**
     * Permette ai giocatori di inserire per la prima volta le armate sui rispettivi territori evitando iterazioni continue e aggiornando continuamente i dati del giocatore
     */
    protected void InserimentoIniziale(){
        AzioniIcone.Armata();
        for(Giocatore i: getGiocatori()){       //scorrimento dei giocatori

            Controllo_continente(i, 0);     //Controllo dei continenti in modo da aggiungere armate in più rispetto a quelle di default

            int x = DatiGiocatore.getTurno();       //ottengo il turno del giocatore

            while (DatiGiocatore.getTurno() == x){  //quando un giocatore finisce di inserire, il turno andrà avanti e uscirà dal ciclo while per passare al giocatore successivo

                new DatiGiocatore().BarraInformazioni(i);   //aggiorna i dati dell'utente da mostrare a video
                try {

                    SemaforoInizializzaArmate.acquire();    //per evitare iterazioni inutili del ciclo, tramite un semaforo blocco il thread in modo che il giocatore
                                                            //possa inserire le armate
                } catch (InterruptedException e) {

                }
            }
        }
        AzioniIcone.setControlloreAzioneBottone(1);     //permette al giocatore di passare alla fase del turno
        DatiGiocatore.setTurno(0);
    }

    /**
     * Permette di inserire le armate sul corrispettivo territorio controllando il player non inserisca sul territorio di altri giocatori e che non inserisca
     * più armate di quante non ne possegga
     * @param nomeTerritorio: Viene preso il nome del territorio che dopo una ricerca permetterà di ottenere il territorio con cui si andrà ad operare
     * @param giocatore: Permette di confrontare con il proprietario del territorio se coincide e poter operare sulle armate in modo da rimuoverle o eventualmente aggiungerle
     * @return ritorna il valore delle armate del territorio in modo tale da poter aggiornare il testo del territorio nell'interfaccia grafica
     */
    public String Inserisci_armate_Territorio(String nomeTerritorio, Giocatore giocatore) {

        Territorio TerritorioSelezionato = RicercaTerritorio(nomeTerritorio);

        //Si effettua il controllo che il giocatore attuale sia l'effettivo proprietario del territorio in modo da poter aggiornare le armate su esso
        if (TerritorioSelezionato.getProprietario().getNomeGiocatore().equals(giocatore.getNomeGiocatore())) {

            int numeroArmata = AzioniIcone.DialogoInserimentoArmate(TerritorioSelezionato);

            //dopo aver ottenuto il numero di armate da voler inserire si controlla che il giocatore le disponfa
            numeroArmata -= TerritorioSelezionato.getNumeroArmate();
            if(giocatore.getArmate() - numeroArmata >= 0) {

                //si controlla che il numero inserito sia positivo in modo tale da non avere territori negativi
                if(TerritorioSelezionato.getNumeroArmate() + numeroArmata >= 1) {

                    //operazione su giocatore e territorio
                    TerritorioSelezionato.AggiungiArmate(numeroArmata);
                    giocatore.RimuoviArmate(numeroArmata);

                    RisvegliaSemaforoInserimentoInizialeArmate();   //risveglio il semaforo in modo tale da controllare se il giocatore ha terminato di inserire
                                                                    //o in alternativa di poter sapere quante armate rimangono

                    return String.valueOf(TerritorioSelezionato.getNumeroArmate()); //aggiorno graficamente il numero di armate sul territorio
                }else{
                    MessaggiErrore.TerritorioSenzaArmate();
                }
            }else {
                MessaggiErrore.MancanzaDiArmate();
            }
        } else {
            MessaggiErrore.TerritorioNonProprietario();
        }



        return String.valueOf(TerritorioSelezionato.getNumeroArmate()); //nel caso l'operazione non vada a buon fine, il territorio rimarrà con lo stesso numero di armate


    }


    /**
     * Assegna le armate ai giocatori con cui iniziare decidendo la quantità in base al numero di giocatori
     */
    protected void Assegnazione_Armate() {
        switch (getGiocatori().size()) {
            case 3 -> {
                for (Giocatore giocatore : getGiocatori()) {
                    giocatore.setArmate(35);
                }
            }
            case 4 -> {
                for (Giocatore giocatore : getGiocatori()) {
                    giocatore.setArmate(30);
                }
            }
            case 5 -> {
                for (Giocatore giocatore : getGiocatori()) {
                    giocatore.setArmate(25);
                }
            }
            case 6 -> {
                for (Giocatore giocatore : getGiocatori()) {
                    giocatore.setArmate(20);
                }

            }
        }
    }


    /**
     * Permette di controllare se il giocatore attuale possiede dei territori che formano un determinato continente
     * Tramite esso può ottenere delle armate aggiuntive da inserire fin quando rimane il proprietario dei territori
     * @param Giocatore: permette di ottenere i suoi territori per la verifica facciano parte del continente ed eventualmente assegnare le armate
     * @param controllore: Il controllore permette che il giocatore non abbia già ricevuto le armate prestabilite nel secondo controllo che avviene
     */
    public void Controllo_continente(Giocatore Giocatore, int controllore) {

        boolean controllo_possibile_null = Giocatore.getContinentiInTuoPossesso().isEmpty();
        // permette di controllare se il giocatore è sprovvisto di continenti dato che nel caso ne disponga il programma andrà a eliminare il continente
        //nel caso in cui non sia riuscito a mantenere i territori, in questo modo evita la rottura del programma

        int contatore = 0;          //Contatore dei territori per il controllo dei continenti

            for (Continente i : getContinenti()) {        //Ottengo i continenti

                for(Territorio x : i.getTerritoriRichiesti()){

                    for(Territorio y : Giocatore.getTerritoriInPossessoGiocatore()) {

                        if (y.getNomeTerritorio().equals(x.getNomeTerritorio())) {  //se un territorio corrisponde al territorio del continente aumenta il contatore
                            contatore++;
                            break;
                        }
                    }
                }

                //se alla fine del ciclo il giocatore possiede tutti i territori di un determinato continente si andrà ad assegnare quel continente
                if (contatore == i.getTerritoriRichiesti().size()) {
                    int cont = 0;
                    for(Continente x: Giocatore.getContinentiInTuoPossesso()){  //se cont viene incrementato il giocatore possiede già quel continente quindi in questo modo si eviteranno duplicati
                        if(i.getNomeContinente().equals(x.getNomeContinente())){
                            cont++;
                        }
                    }

                    if(cont == 0) {
                        Giocatore.setContinentiInTuoPossesso(i);
                    }
                    
                    if (controllore == 0) {      //evita il riassegno delle armate
                        Armate_continente(Giocatore, i);    //giocatore e il continente conquistato
                    }

                } else {
                    if (!controllo_possibile_null) {
                        Giocatore.DeleteContinente(i);  //elimina il continente in caso i territori non corrispondano
                    }
                }
                contatore = 0;      //rinizializzo il contatore per poter ripartire e contare i territori
            }
        }

    /**
     * Permette di assegnare le armate supplementari al giocatore
     * @param giocatore giocatore attuale che possiede il continente
     * @param continente continente conquistato
     */
    protected void Armate_continente(Giocatore giocatore, Continente continente) {

        switch (continente.getNomeContinente()) {          //controllo con i continenti e le loro posizioni
            case "AmericaDelNord", "Europa" -> giocatore.setArmate(5);
            case "AmericaDelSud", "Oceania" -> giocatore.setArmate(2);
            case "Africa" -> giocatore.setArmate(3);
            case "Asia" -> giocatore.setArmate(7);
        }
    }       //in base dal continente si ottengono delle armate


    /**
     * Risveglia il semaforo in modo da aggiornare i dati e passare eventualmente a un altro giocatore
     */
    public static void RisvegliaSemaforoInserimentoInizialeArmate(){
        SemaforoInizializzaArmate.release();
    }
}
