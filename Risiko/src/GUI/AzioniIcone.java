package GUI;

import App.FasePreliminare.InizializzazioneArmate;
import App.Gestione_Turno.GestioneSceltaGiocatore;
import App.Partenza.Territorio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatore;
import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatori;
import static GUI.DatiGiocatore.getTurno;
import static GUI.DatiGiocatore.setTurno;
import static GUI.Interfaccia.*;
import static App.Gestione_Turno.GestioneArmateTurno.InserimentoArmateTurno;
import static App.Gestione_Turno.GestioneSceltaGiocatore.setPrimoTerritorio;
import static App.Gestione_Turno.GestioneSceltaGiocatore.setSecondoTerritorio;


public class AzioniIcone {

    private static int ControlloreAzioneBottone = 0;        //l'azione che farà l'armata in base al turno
    private static int ControlloreSelezioneTerritorioAttacco = 0;       //permette di decidere quale armata attacca e quale difende
    private static boolean PremutaBottone = false;      //permette di premere le armate


    /**
     * Permette di distinguere le operazioni che il bottone delle armate fa, distinguendo da inserimento iniziale
     * oppure tra la selezione tra i due territori da scegliere per le operazioni
     * @param button bottone icona corrispondente alla scelta
     *
     */
    public ActionListener DistinguiOperazioniBottone(JButton button) {
        return e -> {


            if (ControlloreAzioneBottone == 0) {    //valido solo per l'inserimento delle armate all'inizio della fase preliminare non nel turno



                if(getTurno() < getGiocatori().size()) {
                    InserimentoArmataIniziale(button);

                }else{
                    setTurno(0);    //tutti i giocatori hanno inserito e andrà avanti
                }


            } else if (ControlloreAzioneBottone == 1) { //questo farà si che si inseriscano le armate nel turno


                Conferma.setVisible(true);
                ConcludiTurno.setEnabled(false);
                InserimentoArmataTurno(button);

            } else {

                if(PremutaBottone) {
                    if (ControlloreSelezioneTerritorioAttacco == 0) {   //questo farà si che si scelgano i territori per le operazioni

                        setPrimoTerritorio(button.getName());

                        Territorio Attaccante = GestioneSceltaGiocatore.RicercaTerritorio(GestioneSceltaGiocatore.getPrimoTerritorio());
                        //ritorna il territorio dal nome che si ottiene premendo il bottone
                        if(Attaccante.getProprietario().getNomeGiocatore().equals(getGiocatore(getTurno()).getNomeGiocatore())) {

                            messaggioLabel.setText("Scegli il territorio di destinazione");
                            ControlloreSelezioneTerritorioAttacco += 1;
                        }else{
                            MessaggiErrore.TerritorioNonProprietario();
                        }

                    } else {    //se un primo territorio è stato selezionato si andrà alla scelta del secondo territorio

                        setSecondoTerritorio(button.getName());
                        GestioneSceltaGiocatore.RipristinoSemaforoOperazioni(); //risveglio il semaforo in modo che i due territori operino tra di loro

                        ControlloreSelezioneTerritorioAttacco = 0;  //al termine rinizializzo in modo che i territori eventualmente potranno essere entrambi scelti


                        messaggioLabel.setVisible(false);
                        AzioneChiusura.MenuGiocatore();
                        PremutaBottone = false;
                    }

                }
            }
        };
    }

    /**
     * Permette di inserire le armate nella fase preliminare ovvero il primo inserimento nel gioco
     * @param button bottone selezionato
     */
    public static void InserimentoArmataIniziale(JButton button) {

        try {
            String numero = new InizializzazioneArmate().Inserisci_armate_Territorio(button.getName(), getGiocatore(getTurno()));
            button.setText(numero);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Inserisci un numero");
        }
    }


    /**
     * Permette di inserire le armate nel turno del giocatore
     * @param button territorio scelto
     */
    public static void InserimentoArmataTurno(JButton button) {
        try {
            String numero = InserimentoArmateTurno(button.getName(), getGiocatore(getTurno()));
            button.setText(numero);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Inserisci un numero");
        }

    }


    /**
     * Permette di scegliere quante armate inserire nel territorio o di quante spostarne da un territorio a un altro
     * @param ValoreIniziale permette di mostrare il territorio da che valore di armate parte
     * @param LimiteInferiore permette che le armate non vadano sotto un determinato limite in modo da non creare problemi con armate negative
     * @param Messaggio mostra il messaggio nel pannello in modo da differenziare tra inserimento e spostamento
     *
     */
    public static int DialogoArmate(int ValoreIniziale, int LimiteInferiore, String Messaggio){

        JPanel PanelInput = new JPanel(new BorderLayout()); //gestisce le armate da inserire

        JLabel LabelArmate = new JLabel(Messaggio, SwingConstants.CENTER); //Testo della finestra

        JTextField CampoTesto = new JTextField(10); //area input
        CampoTesto.setText(String.valueOf(ValoreIniziale));


        JButton Addizione = new JButton("+");   //operazioni per modificare le armate
        JButton Sottrazione = new JButton("-");
        JButton Conferma = new JButton("Conferma");

        CampoTesto.setHorizontalAlignment(SwingConstants.CENTER);
        Addizione.setFocusPainted(false);   //in modo che il rettangolo verticale non compaia
        Sottrazione.setFocusPainted(false);


        Addizione.addActionListener(e -> {
            try {
                int Valore = Integer.parseInt(CampoTesto.getText());
                CampoTesto.setText(String.valueOf(Valore + 1));
            }catch (NumberFormatException x){
                MessaggiErrore.NumeroValido();
            }
        });

        Sottrazione.addActionListener(e -> {
            try {
                int Valore = Integer.parseInt(CampoTesto.getText());
                if (Valore > LimiteInferiore) {
                    CampoTesto.setText(String.valueOf(Valore - 1));
                }
            }catch (NumberFormatException y){
                MessaggiErrore.NumeroValido();
            }
        });

        Conferma.addActionListener(e -> {
            JOptionPane.getRootFrame().dispose();   //chiude il frame e conferma le armate

        });


        JPanel PanelBottoni = new JPanel(new FlowLayout());
        PanelBottoni.add(Sottrazione);
        PanelBottoni.add(CampoTesto);
        PanelBottoni.add(Addizione);


        PanelInput.add(LabelArmate, BorderLayout.NORTH);
        PanelInput.add(PanelBottoni, BorderLayout.CENTER);
        PanelInput.add(Conferma, BorderLayout.SOUTH);



        int controlloArmata;
        int armata = 0;

        //evita che si inseriscano lettere evitando la rottura del programma
        do {

            try {
                JOptionPane.showOptionDialog(null, PanelInput, "Regolazione Armate", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{}, null);
                armata = Integer.parseInt(CampoTesto.getText());
                controlloArmata = 1;
            } catch (NumberFormatException | InputMismatchException e) {
                MessaggiErrore.NumeroValido();
                controlloArmata = 0;
            }
        }while(controlloArmata == 0);




        return armata;

    }

    /**
     * Permette di inserire le armate mettendo un limite inferiore in modo da non avere numeri negativi
     * @param territorio territorio scelto
     */
    public static int DialogoInserimentoArmate(Territorio territorio){
        return DialogoArmate(territorio.getNumeroArmate(), 0, "Armate nel territorio: ");
    }

    /**
     * Permette di inserire le armate permettendo di non andare oltre un limite di armate obbligatori (quelle impiegate nell'attacco)
     * @param armate armate utilizzate nello scontro
     *
     */
    public static int DialogoSpostamentoArmate(int armate, Territorio territorio){

        return DialogoArmate(armate, armate, "Quante armate vuoi spostare? " + "(max): " + (territorio.getNumeroArmate()-1 + armate));
    }

    public static void Armata(){
        messaggioLabel.setText("Inserisci le armate nei territori");
        messaggioLabel.setVisible(true);
    }





    public static void setControlloreAzioneBottone(int x) {
        ControlloreAzioneBottone = x;
    }

    public static void setControlloreSelezioneTerritorioAttacco(int controlloreSelezioneTerritorioAttacco) {
        ControlloreSelezioneTerritorioAttacco = controlloreSelezioneTerritorioAttacco;
    }

    public static void setPremutaBottone(boolean premutaBottone) {
        PremutaBottone = premutaBottone;
    }
}
