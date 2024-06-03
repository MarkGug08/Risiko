package App.Gestione_Turno;

import App.FasePreliminare.InizializzazioneTerritori;
import GUI.*;
import App.Partenza.Dado;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static GUI.AzioneChiusura.MenuGiocatore;
import static GUI.AzioneScelta.MostraCarte;
import static GUI.Interfaccia.AnnullaOperazione;
import static GUI.Interfaccia.ConcludiTurno;
import static App.Gestione_Turno.GestioneGiocatore.RitornaArmata;

public class GestioneSceltaGiocatore {


    private static String PrimoTerritorio = null;   //territorio che sferra l'attacco o da dove parte lo spostamento
    private static String SecondoTerritorio = null;   //territorio che subisce l'attacco o lo spostamento



    private static final Semaphore SemaforoOperazioni = new Semaphore(0);
    //semaforo chiamato per l'attesa dovuta al selezionamento del primo e secondo territorio
    private static final Semaphore SemaforoScelta = new Semaphore(0);
   //semaforo chiamato per l'attesa dovuta al selezionamento dell'operazione che il giocatore vuole compiere


    /**
     * Permette di scegliere le 3 operazioni principali da svolgere durante il turno (Attacco, spostamento e visualizzare il mazzo di carte)
     * Il metodo resta in attesa la prima volta per far scegliere l'operazione e la seconda volta per far scegliere i territori al giocatore
     * Fin quando il giocatore non passerà il turno il programma continuerà nel do-while
     * @param giocatore giocatore che sta attualmente giocando
     */
    protected void Scelta(Giocatore giocatore) {

        int controllore = DatiGiocatore.getTurno(); //controllore che permette di vedere se il giocatore ha passato il turno

        do{
            try{
                SemaforoScelta.acquire();   //fermo il thread
            }catch(InterruptedException e){
                System.out.println("Si è verificato un errore");
            }
            switch (giocatore.getAzioneDaSvolgere()) {


                case 1 -> {
                    try{
                        SemaforoOperazioni.acquire();   //in attesa dei territori
                    }catch(InterruptedException e){
                        System.out.println("Si è verificato un errore");
                    }

                    Territorio Attaccante = RicercaTerritorio(getPrimoTerritorio());    //ottengo i territori scelti
                    Territorio Difensore = RicercaTerritorio(getSecondoTerritorio());
                    if(Attaccante != null && Difensore != null) {   //l'annulla operazione permette di annullare la scelta del giocatore impostando i territori null

                        AnnullaOperazione.setVisible(false);
                        DichiaraAttacco(Attaccante, Difensore);
                        ConcludiTurno.setEnabled(true);
                    }

                }
                case 2 -> {

                    //i giocatori possono fare solo uno spostamento nel proprio turno

                        try {
                            SemaforoOperazioni.acquire();   //stop per i territori
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        Territorio TerritorioPrimario = RicercaTerritorio(getPrimoTerritorio());
                        Territorio TerritorioSecondario = RicercaTerritorio(getSecondoTerritorio());

                        if(TerritorioPrimario!= null && TerritorioSecondario!= null) {

                            AnnullaOperazione.setVisible(false);
                            Spostamento(TerritorioPrimario, TerritorioSecondario, 0, giocatore);
                            ConcludiTurno.setEnabled(true);

                        }

                }
                case 3 -> MostraCarte(giocatore);   //visualizzo le carte


            }

            if(GestioneTurno.GiocataTris){  //nel caso in cui il giocatore visualizza le carte e gioca il tris esso bloccherà il ciclo per permettere al giocatore
                                            //di poter inserire le armate bonus
                break;
            }

            giocatore.setAzioneDaSvolgere(0);   //rinizializzo il blocco per evitare che le operazioni possano ripetersi


            setPrimoTerritorio(null);   //ripristino dei territori
            setSecondoTerritorio(null);


        }while(controllore == DatiGiocatore.getTurno());
        giocatore.setSpostamentoEffettuato(0);  //rinizializzo lo spostamento al giocatore in modo che al suo prossimo turno possa effettuare lo spostamento
        Interfaccia.ConcludiTurno.setVisible(true);

    }


    /**
     * Permette di effettuare un attacco da un territorio a un altro, il metodo controlla che il giocatore sia il proprietario del territorio che sferra l'attacco e
     * che questo territorio abbia almeno 2 armate oltre a controllare che il territorio che subisce l'attacco sia sotto il controllo di un altro giocatore e che sia confinante
     * Appurato confini e armate il codice il metodo gestirà le armate impiegate e in caso di vittoria assegnerà il territorio all'attaccante oltre eventualmente all'eliminazione
     * del giocatore
     * @param Attaccante Territorio che sferra l'attacco
     * @param Difensore Territorio che subisce l'attacco
     */
    protected void DichiaraAttacco(Territorio Attaccante, Territorio Difensore) {

        Dado dado = new Dado();


        int numero_armate_territorio = Attaccante.getNumeroArmate();       //armate del territorio che sferra l'attacco

        Giocatore GiocatoreAttaccante = Attaccante.getProprietario();   //ottengo i giocatori
        Giocatore GiocatoreDifensore = Difensore.getProprietario();

        int territorio_confinante = Territori_confinanti(Attaccante, Difensore);    //controllo che i territori confinino

        if (numero_armate_territorio > 1 && territorio_confinante == 1) {

            numero_armate_territorio -= 1;      //si toglie un'armata essendo che su ogni territorio ci sta almeno un'armata


            int Armate_Difensore = Difensore.getNumeroArmate(); //ottengo le armate del giocatore che difende

            //si attacca con massimo tre dadi
            dado.LancioDadi(Math.min(numero_armate_territorio, 3), Math.min(Armate_Difensore, 3));       //lancio dei dadi

            int[] rt = dado.Confronto();          //ritorno in un array le armate perse da entrambi i territori

            Attaccante.SottraiArmate(rt[0]);       //vengono sottratte le armate ai territori
            Difensore.SottraiArmate(rt[1]);

            ArrayList<Armata_GUI> t = Interfaccia.getArmataGUI();

            int IconaTerritorioAttaccante = RitornaArmata(Attaccante);  //ottengo le icone corrispondenti al territorio
            t.get(IconaTerritorioAttaccante).setText(String.valueOf(Attaccante.getNumeroArmate()));        //nel caso in cui l'attaccante perda lo scontro aggiorna le armate rimanenti

            int IconaTerritorioConquistato = RitornaArmata(Difensore);
            t.get(IconaTerritorioConquistato).setText(String.valueOf(Difensore.getNumeroArmate()));

            if (Difensore.getNumeroArmate() <= 0) {         //se un territorio non ha più armate il territorio è conquistato dall'attaccante
                if (numero_armate_territorio > 3) {        //come minimo si spostano le armate utilizzate nel lancio dei dadi quindi se ce n'erano più di tre saranno stati utilizzati sicuro tre dadi
                    numero_armate_territorio = 3;
                }

                String armataAttaccanteColore = GiocatoreAttaccante.getColoreArmataGiocatore();


                new Interfaccia().Colore(armataAttaccanteColore, t.get(IconaTerritorioConquistato));    //modifico il colore dell'icona
                Difensore.setProprietario(GiocatoreAttaccante);        // si setta il nuovo proprietario

                GiocatoreAttaccante.setTerritorio(Difensore);          // si aggiorna la lista dei territori al giocatore che ha vinto il combattimento
                GiocatoreDifensore.DeleteTerritorio(Difensore);     //il territorio viene cancellato dal precedente proprietario
                Spostamento(Attaccante, Difensore, numero_armate_territorio, GiocatoreAttaccante);        //si spostano eventuale altre armate oltre quelle d'obbligo tra i due territori

                new DatiGiocatore().BarraInformazioni(GiocatoreAttaccante);    //faccio l'update dei dati del giocatore con il territorio in possesso
            }

            GestioneGiocatore.RimozioneGiocatore(GiocatoreDifensore, GiocatoreAttaccante);        //rimuove il giocatore dal gioco

        } else {
            if(numero_armate_territorio == 1){
                MessaggiErrore.MancanzaDiArmate();
            }else{
                if(territorio_confinante == -1){
                    MessaggiErrore.TerritorioInTuoPossesso();
                }else{
                    MessaggiErrore.TerritoriNonConfinanti(Difensore.getNomeTerritorio());
                }
            }
        }


    }

    /**
     * Permette lo spostamento delle armate tra un territorio e un altro, dopo aver appurato il confine tra i due territori si gestiscono le armate tra i due territori
     * ed eventuali errori in caso di mancanza di armate
     * @param TerritorioPrimario Territorio da cui partono le armate
     * @param TerritorioSecondario Territorio che riceve le armate
     * @param armate permette di distinguere lo spostamento come azione da quello dovuto in base all'attacco dato che nell'ultimo vengono passate le armate impiegate nel
     *               combattimento dei dadi mentre nello spostamento normale il valore è 0
     * @param giocatore si potrà operare sul giocatore assegnandogli lo spostamento in modo che non possa farlo in seguito
     */
    protected void Spostamento(Territorio TerritorioPrimario, Territorio TerritorioSecondario, int armate, Giocatore giocatore) {
        TerritorioSecondario.AggiungiArmate(armate);        //nella fase d'attacco fa si che le armate utilizzate nello scontro in caso di conquista passino in automatico
        //nella fase di spostamento normale invece non sposta inizialmente nulla e lo spostamento viene utilizzato dopo
        TerritorioPrimario.SottraiArmate(armate);

        int armate_da_spostare;
        int cont = 0;           //contatore per far spostare le armate necessarie senza intaccare il territorio precedente

        int numero_armate_attaccante = TerritorioPrimario.getNumeroArmate();

        int contr = Territori_confinanti(TerritorioPrimario, TerritorioSecondario); //controllo territori confinanti e dello stesso proprietario

        do {
            if (numero_armate_attaccante >= 1 && contr == -1) {   //in ogni territorio deve esserci almeno un'armata altrimenti andrà in loop fino a quando non si spostano le armate necessarie

                armate_da_spostare = AzioniIcone.DialogoSpostamentoArmate(armate, TerritorioPrimario) - armate; //ottengo le armate da spostare
                //le armate vengono rimosse essendo che l'interfaccia partirà dal numero di armate che sono state utilizzate


                if (numero_armate_attaccante - armate_da_spostare >= 1 && armate_da_spostare >= 0) {              //se si inseriscono più armate di quante se ne hanno e se si inseriscono numeri negativi

                    TerritorioSecondario.AggiungiArmate(armate_da_spostare);                //aggiunge le armate al territorio
                    TerritorioPrimario.SottraiArmate(armate_da_spostare);                  //sottrae le armate dal territorio dove provengono
                    cont = 1;       //controllore che le armate siano state spostate

                    ArrayList<Armata_GUI> t = Interfaccia.getArmataGUI();   //ottengo le icone corrispondenti in modo da aggiornare l'interfaccia con i nuovi lavori

                    int IconaTerritorioAttaccante = RitornaArmata(TerritorioPrimario);
                    t.get(IconaTerritorioAttaccante).setText(String.valueOf(TerritorioPrimario.getNumeroArmate()));        //nel caso in cui l'attaccante perda lo scontro aggiorna le armate rimanenti

                    int IconaTerritorioConquistato = RitornaArmata(TerritorioSecondario);
                    t.get(IconaTerritorioConquistato).setText(String.valueOf(TerritorioSecondario.getNumeroArmate()));

                    if(armate_da_spostare != 0){
                        MessaggiInfo.ArmateSpostate();  //comunico che lo spostamento è stato effettuato
                        if(armate == 0) {
                            giocatore.setSpostamentoEffettuato(1);  //aggiorno lo spostamento in modo che il giocatore non possa più farlo come azione
                        }
                    }       //armate == 0 significa che lo spostamente non deriva da un attacco e se delle armate sono state spostate, spostamento effettuato


                    MenuGiocatore();    //faccio ricomparire il menu del giocatore
                } else {
                    if(armate_da_spostare < 0){
                        MessaggiErrore.NumeroValido();
                    }else{
                        MessaggiErrore.MancanzaDiArmate();
                    }
                    MenuGiocatore();
                }
            } else {
                if(contr == 1){
                    MessaggiErrore.TerritorioNonProprietario();
                }else if(contr == 0){
                    MessaggiErrore.TerritoriNonConfinanti(TerritorioSecondario.getNomeTerritorio());
                }else{
                    MessaggiErrore.TerritorioSenzaArmate();
                }


                MenuGiocatore();
                break;
            }
        } while (cont == 0);    //nel caso in cui le armate stanno venendo spostate in seguito a un attacco

    }


    /**
     * Permette di ritornare un territorio cercandolo tramite il nome
     * @param nome nome del territorio
     * @return ritorna la posizione nell'arraylist dei territori, il null non sarà mai ritornato essendo che sicuro troverà il territorio corrispondente
     */
    public static Territorio RicercaTerritorio(String nome){
        for(Territorio j: InizializzazioneTerritori.getTerritori()){
            if(j.getNomeTerritorio().equals(nome)){
                return j;
            }
        }

        return null;
    }

    /**
     * Permette di controllare che i territori siano confinanti controllando anche l'effettivo proprietario
     * @param TerritorioAttaccante territorio primario
     * @param TerritorioDifensore territorio che subisce lo spostamento o l'attacco
     * @return ritorna un valore in modo da far capire il tipo di errore o comunicare che non ci sono problemi
     * 1: territorio confinante e proprietari diversi
     * -1: territorio confinante e proprietario uguale
     * 0: territori non confinanti
     */
    protected int Territori_confinanti(Territorio TerritorioAttaccante, Territorio TerritorioDifensore){

        ArrayList<Territorio> territori_confinanti = TerritorioAttaccante.getTerritoriAdiacenti();  //ogni territorio una un arraylist con i territori confinanti

        for(Territorio i: territori_confinanti){
            if(i.getNomeTerritorio().equals(TerritorioDifensore.getNomeTerritorio())){  //controllo che il territorio difensore sia confinante
                if(!(i.getProprietario().getNomeGiocatore().equals(TerritorioAttaccante.getProprietario().getNomeGiocatore()))) { //controllo del proprietario di esso
                    return 1;
                }else{
                    return -1;
                }
            }
        }

        return 0;
    }


    public static String getPrimoTerritorio() {
        return PrimoTerritorio;
    }

    public static void setPrimoTerritorio(String primoTerritorio) {
        PrimoTerritorio = primoTerritorio;
    }



    public String getSecondoTerritorio(){
        return SecondoTerritorio;
    }

    public static void setSecondoTerritorio(String secondoTerritorio) {
        SecondoTerritorio = secondoTerritorio;
    }



    public static void RipristinoSemaforoOperazioni(){
        SemaforoOperazioni.release();
    }

    public static void RipristinoSemaforoScelta(){
        SemaforoScelta.release();
    }


}
