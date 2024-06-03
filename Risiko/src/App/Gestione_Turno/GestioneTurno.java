package App.Gestione_Turno;

import App.FasePreliminare.InizializzazioneArmate;
import App.FasePreliminare.InizializzazioneGiocatori;
import GUI.AzioniIcone;
import GUI.DatiGiocatore;
import GUI.MessaggiInfo;
import App.Gestione_file.Salvataggio;
import App.Partenza.Giocatore;


public class GestioneTurno {


    GestioneControlloObiettivi GestioneObiettivi = new GestioneControlloObiettivi();
    GestioneGiocatore GestioneGiocatore = new GestioneGiocatore();
    GestioneSceltaGiocatore GestioneScelta = new GestioneSceltaGiocatore();
    GestioneArmateTurno GestioneArmate = new GestioneArmateTurno();
    InizializzazioneArmate InizializzaArmate = new InizializzazioneArmate();

    private static int numeroGiocatore = 0;

        static boolean GiocataTris; //permette di far si che dopo che un giocatore giochi un tris si ritorni all'inserimento delle armate in modo da poter inserire le armate bonus
        static boolean Obiettivo; //controllo che l'obiettivo sia stato raggiunto
        public void Turno(){

            do{

                int controllo_continente = 0;   //controllo che il giocatore non venga assegnate le armate del continente due volte
                int territori_attuali; //controllo per l'ottenimento della carta per verificare che il territorio ha conquistato un territorio

                Giocatore GiocatoreAttuale = InizializzazioneGiocatori.getGiocatore(numeroGiocatore);

                GestioneArmate.ArmateSupplementari(GiocatoreAttuale);        //assegnazione armate iniziali

                InizializzaArmate.Controllo_continente(GiocatoreAttuale, controllo_continente);        //controllo per eventuali bonus armate dovute ai continenti

                do {
                    AzioniIcone.Armata();
                    GiocataTris = false;
                    new DatiGiocatore().BarraInformazioni(GiocatoreAttuale);

                    GestioneGiocatore.Stop(GiocatoreAttuale);


                    controllo_continente = 1; //il giocatore durante il turno conquisterà dei territori e se completa l'obiettivo il gioco finisce ma se non lo completa con il settaggio di eventuali continenti
                    // Che ha conquistato nel turno farà in modo che gli rivenghino assegnate le armate di nuovo. Questo controllore fa si che setta il continente ma non le armate
                    territori_attuali = GiocatoreAttuale.getTerritoriInPossessoGiocatore().size();


                    GestioneScelta.Scelta(GiocatoreAttuale);          //permette di selezionare cosa fare nel turno
                }while(GiocataTris);    //nel caso viene giocato un tris torna alla schermata di inserimento
                InizializzaArmate.Controllo_continente(GiocatoreAttuale, controllo_continente);        //Setta tutti gli eventuali continenti che ha conquistato il giocatore in questo turno


                Obiettivo = GestioneObiettivi.ControlloObiettivi(GiocatoreAttuale);     //controlla ogni fine turno se l'obiettivo è stato raggiunto


                GestioneGiocatore.Assegnazione_carta(GiocatoreAttuale, territori_attuali);  //assegna la carta

                if(!Obiettivo) {
                    numeroGiocatore = GestioneGiocatore.Rotazione(numeroGiocatore);     //permette di far ruotare i giocatori
                }
                Salvataggio.SalvataggioPartita(numeroGiocatore);    //permette di salvare il gioco in modo in futuro da ripartire da qui


            }while(!Obiettivo);   //continuerà fin quando o si completa l'obiettivo o che i giocatori sono stati eliminati tranne 1

            MessaggiInfo.VittoriaGiocatore(InizializzazioneGiocatori.getGiocatore(numeroGiocatore).getNomeGiocatore());

            Salvataggio.Cancellazione("src\\Risorse\\FileSerializzati\\Territori.ser");
            Salvataggio.Cancellazione("src\\Risorse\\FileSerializzati\\Turno.ser");
            Salvataggio.Cancellazione("src\\Risorse\\FileSerializzati\\Continenti.ser");
            Salvataggio.Cancellazione("qsrc\\Risorse\\FileSerializzati\\MazzoDiCarte.ser");
        }


    public static void setNumeroGiocatore(int numeroGiocatore) {
        GestioneTurno.numeroGiocatore = numeroGiocatore;
    }


}
