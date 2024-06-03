package GUI;

import App.FasePreliminare.InizializzazioneArmate;
import App.FasePreliminare.InizializzazioneGiocatori;
import App.Gestione_Turno.GestioneGiocatore;
import App.Gestione_Turno.GestioneSceltaGiocatore;

import javax.swing.*;
import java.awt.event.ActionListener;


import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatore;

import static GUI.DatiGiocatore.getTurno;
import static GUI.DatiGiocatore.setTurno;
import static GUI.Interfaccia.*;

public class AzioneChiusura {

    private static int Controllore = 0;
    //permette di risvegliare il semaforo dell'inserimento iniziale
    //al termine permetterà di ripristinare il semaforo nella scelta delle opeazioni

    private static int ConfermaInserimento = 0;
    //conferma che tutte le armate sono inserite quando si inseriscono nel turno


    /**
     * Permette di confermare le armate dove sono state inserite in modo che si possa passare alla scelta delle azioni del turno
     *
     */
    public ActionListener ConfermaInserimento() {
        return e -> {

            if(getGiocatore(getTurno()).getArmate() == 0) { //il giocatore deve obbligatoriamente inserire tutte le armate

                setConfermaInserimento(1);
                messaggioLabel.setVisible(false);
                Conferma.setVisible(false); //tolgo il comando che non serve
                MenuGiocatore();    //mostro le azioni che può fare il giocatore
                ConcludiTurno.setEnabled(true);


                GestioneGiocatore.RisvegliaSemaforoGestioneArmateTurno();  //in modo che risveglia  facendo andare avanti il programma

            }else{
                JOptionPane.showMessageDialog(null, "Inserisci tutte le armate nei territori");

            }
        };
    }


    /**
     * Peremtte di concludere il turno facendo si che il giocatore seguente possa iniziare a inserire le armate e fare le sue scelte
     *
     */
    public ActionListener ConcludiTurno() {
        return e -> {
            try {
                if (getGiocatore(getTurno()).getArmate() == 0) {    //controllo nella fase preliminare abbia inserito tutte le armate

                    setTurno(getTurno() + 1);   //incremento il turno corrispondente al giocatore seguente
                    NascondiMenuGiocatore(); //tolgo le opzioni di azione in modo che l'utente inserisca le armate

                    if(Controllore < InizializzazioneGiocatori.getGiocatori().size()) {
                        InizializzazioneArmate.RisvegliaSemaforoInserimentoInizialeArmate();    //gestione per le armate iniziali

                        Controllore++;
                    }else{
                        GestioneSceltaGiocatore.RipristinoSemaforoScelta(); //sbloccando il semaforo il programma continuerà uscendo dal ciclo per verificare i vari dati del
                        //giocatore come se ha conquistato continenti o completato l'obiettivo
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Inserisci tutte le armate nei territori");
                }
            }catch(IndexOutOfBoundsException a){
                setTurno(0);
            }
        };
    }


    /**
     * Permette di mostrare a video le operazioni del giocatore
     */
    public static void MenuGiocatore(){

        Attacca.setVisible(true);
        MazzoDiCarte.setVisible(true);
        Spostamento.setVisible(true);

    }


    /**
     * Permette di nascondere le operazioni del giocatore
     */
    public static void NascondiMenuGiocatore(){
        Attacca.setVisible(false);
        MazzoDiCarte.setVisible(false);
        Spostamento.setVisible(false);
    }



    public static void setConfermaInserimento(int confermaInserimento) {
        ConfermaInserimento = confermaInserimento;
    }



    public static int getConfermaInserimento() {
        return ConfermaInserimento;
    }


    public static void setControllore(int controllore) {
        Controllore = controllore;
    }
}
