package GUI;

import App.Partenza.Giocatore;
import App.Partenza.Territorio;
import App.OrganizzazioneRisiko;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatori;


public class Interfaccia {

    public static ArrayList<Armata_GUI> armateGUi = new ArrayList<>();

    static ImageIcon mappa = new ImageIcon("Risiko/src/Risorse/Immagini/Mappa.png");

    ImageIcon Arancioni = new ImageIcon("Risiko/src/Risorse/Immagini/ArmateArancioni.png");
    ImageIcon Bianche = new ImageIcon("Risiko/src/Risorse/Immagini/ArmateBianche.png");
    ImageIcon Blu = new ImageIcon("Risiko/src/Risorse/Immagini/ArmateBlu.png");
    ImageIcon Lilla = new ImageIcon("Risiko/src/Risorse/Immagini/ArmateLilla.png");
    ImageIcon Nere = new ImageIcon("Risiko/src/Risorse/Immagini/ArmateNere.png");
    ImageIcon Verdi = new ImageIcon("Risiko/src/Risorse/Immagini/ArmateVerdi.png");




    static JFrame FrameRisultatiDadi = new JFrame("Risultati dadi");
    static JPanel BarraDati = new JPanel();
    static JLabel messaggioLabel = new JLabel();


    public static JButton ConcludiTurno = new JButton("Finisci il turno");
    static JButton Attacca = new JButton("Dichiara Attacco");
    static JButton Spostamento = new JButton("Effettua Spostamento");
    static JButton MazzoDiCarte = new JButton("Visualizza mazzo");
    public static JButton AnnullaOperazione = new JButton("Annulla");
    static JButton Conferma = new JButton(" Conferma ");



    static JLabel Territori = new JLabel();
    static JPanel PannelloInferiore = new JPanel();
    static JPanel PannelloGestioneScelteTurno = new JPanel(new GridBagLayout());

    static JButton Obiettivo = new JButton("Obiettivo");
    static JFrame frame = new JFrame();
    static JLabel label = new JLabel(mappa);
    static JPanel panel1 = new JPanel();
    static JPanel panel2 = new JPanel();



    static JLabel TurnoDelGiocatore = new JLabel();
    static JLabel ArmateADisposizione = new JLabel();
    static JLabel ColoreArmataGiocatore = new JLabel();
    static JLabel TerritoriInPossesso = new JLabel();
    static JLabel ContinentiInPossesso = new JLabel();

    public void Inizializzazione(){

        CreazioneFrame();
        CreazioneMappa();
        Posizionamento(getGiocatori());
        CreazioneMenu();

        frame.setLayout(null); // Disabilita il layout manager predefinito
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.add(panel1);
        frame.add(panel2);
        frame.setVisible(true); // Rende il frame visibile

    }


    public void CreazioneFrame(){
        frame.setSize(1400, 800); // Imposta le dimensioni del frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Chiude l'applicazione quando il frame viene chiuso

    }


    public void CreazioneMappa(){
        panel1.setBounds(0, 0, 1050, 800); // Imposta le dimensioni del primo pannello
        panel1.setBackground(Color.lightGray);
        panel1.add(label);
    }


    public void CreazioneMenu() {
        panel2.setBounds(1050, 0, 350, 800); // Imposta le dimensioni del secondo pannello
        panel2.setBackground(Color.gray);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        // Creazione del label con il bottone al suo interno


    CreazioneBarraDati();
    GestioneScelta();
    FineTurno();

    panel2.add(BarraDati);
    panel2.add(PannelloGestioneScelteTurno);
    panel2.add(PannelloInferiore);

    }

    public void CreazioneBarraDati(){
        BarraDati.setBackground(Color.GRAY);
        BarraDati.setPreferredSize(new Dimension(350, 200)); // Imposta le dimensioni del pannello
        BarraDati.setLayout(new BoxLayout(BarraDati, BoxLayout.Y_AXIS));

        JPanel Dati = new JPanel();
        Dati.setLayout(new BoxLayout(Dati, BoxLayout.Y_AXIS));

        TurnoDelGiocatore.setAlignmentX(Component.CENTER_ALIGNMENT);
        ColoreArmataGiocatore.setAlignmentX(Component.CENTER_ALIGNMENT);
        ArmateADisposizione.setAlignmentX(Component.CENTER_ALIGNMENT);
        TerritoriInPossesso.setAlignmentX(Component.CENTER_ALIGNMENT);
        ContinentiInPossesso.setAlignmentX(Component.CENTER_ALIGNMENT);

        TurnoDelGiocatore.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ColoreArmataGiocatore.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ArmateADisposizione.setFont(new Font("Segoe UI", Font.BOLD, 16));
        TerritoriInPossesso.setFont(new Font("Segoe UI", Font.BOLD, 16));
        ContinentiInPossesso.setFont(new Font("Segoe UI", Font.BOLD, 16));

        Dati.add(TurnoDelGiocatore);
        Dati.add(ColoreArmataGiocatore);
        Dati.add(ArmateADisposizione);
        Dati.add(TerritoriInPossesso);
        Dati.add(ContinentiInPossesso);


        BarraDati.add(Dati);

        Dati.setBorder(new EmptyBorder(20, 0, 30 ,0));
        Dati.setBackground(Color.GRAY);


        Territori.setAlignmentX(Component.CENTER_ALIGNMENT);
        BarraDati.add(Territori);

        Obiettivo();
        BarraDati.add(Obiettivo);
    }
    public void Obiettivo(){
        ActionListener AzioneObiettivo = new DatiGiocatore().MostraObiettivo();
        Obiettivo.addActionListener(AzioneObiettivo);
        Obiettivo.setAlignmentX(Component.CENTER_ALIGNMENT);

    }

    public static void GestioneScelta(){
        GridBagConstraints gbc = new GridBagConstraints();
        PannelloGestioneScelteTurno.setBackground(Color.gray);
        PannelloGestioneScelteTurno.setPreferredSize(new Dimension(350, 200)); // Imposta le dimensioni del pannello

        messaggioLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messaggioLabel.setVisible(false);
        messaggioLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));


        Dimension buttonDimension = new Dimension(150, 30);
        Attacca.setPreferredSize(buttonDimension);
        Spostamento.setPreferredSize(buttonDimension);
        MazzoDiCarte.setPreferredSize(buttonDimension);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Aggiunge uno spazio intorno ai bottoni
        gbc.fill = GridBagConstraints.NONE; // Permette ai bottoni di espandersi orizzontalmente
        gbc.anchor = GridBagConstraints.CENTER; // Imposta l'allineamento al centro
        gbc.weightx = 1.0; // Rende i bottoni espandibili orizzontalmente se necessario

        ActionListener DichiaraAttacco = new AzioneScelta().DichiaraAttacco();
        Attacca.addActionListener(DichiaraAttacco);

        ActionListener EffettuaSpostamento = new AzioneScelta().EffettuaSpostamento();
        Spostamento.addActionListener(EffettuaSpostamento);

        ActionListener VisualizzaMazzo = new AzioneScelta().VisualizzaMazzo();
        MazzoDiCarte.addActionListener(VisualizzaMazzo);

        Attacca.setVisible(false);
        Spostamento.setVisible(false);
        MazzoDiCarte.setVisible(false);
        AnnullaOperazione.setVisible(false);

        PannelloGestioneScelteTurno.add(Attacca, gbc);

        gbc.gridy = 1;
        PannelloGestioneScelteTurno.add(Spostamento, gbc);

        gbc.gridy = 2;
        PannelloGestioneScelteTurno.add(MazzoDiCarte, gbc);

        gbc.gridy = 3;
        PannelloGestioneScelteTurno.add(messaggioLabel, gbc);

    }

    public void FineTurno(){
        PannelloInferiore.setPreferredSize(new Dimension(350, 200)); // Imposta le dimensioni del pannello
        PannelloInferiore.setBackground(Color.gray);


        ActionListener AzioneConclusione = new AzioneChiusura().ConcludiTurno();
        ConcludiTurno.addActionListener(AzioneConclusione);

        ActionListener ConfermaInserimento = new AzioneChiusura().ConfermaInserimento();
        Conferma.addActionListener(ConfermaInserimento);

        ActionListener Annulla = new AzioneScelta()
                .AnnullaOperazione();
        AnnullaOperazione.addActionListener(Annulla);

        // Crea un oggetto GridBagConstraints per impostare i vincoli di posizionamento del bottone
        GridBagConstraints gbco = new GridBagConstraints();
        gbco.gridx = 0;
        gbco.gridy = 0;
        gbco.insets = new Insets(10, 10, 10, 10); // Aggiunge uno spazio intorno ai bottoni
        gbco.fill = GridBagConstraints.NONE; // Permette ai bottoni di espandersi orizzontalmente
        gbco.anchor = GridBagConstraints.CENTER; // Imposta l'allineamento al centro
        gbco.weightx = 1.0; // Rende i bottoni espandibili orizzontalmente se necessario



        PannelloInferiore.setLayout(new GridBagLayout()); // Imposta il layout manager del pannello "PannelloInferiore"

        PannelloInferiore.add(AnnullaOperazione, gbco);
        gbco.gridy = 1;

        PannelloInferiore.add(Conferma, gbco); // Aggiunge il bottone al pannello "PannelloInferiore" con i vincoli di posizionamento

        gbco.gridy = 2;

        PannelloInferiore.add(ConcludiTurno, gbco); // Aggiunge il bottone al pannello "PannelloInferiore" con i vincoli di posizionamento
        gbco.gridy = 3;


        Conferma.setVisible(false);
        AnnullaOperazione.setVisible(false);

    }


    public static ArrayList<Armata_GUI> getArmataGUI(){
        return armateGUi;
    }
    public void Colore(String colore, Armata_GUI armata) {
        switch (colore) {
            case "Verdi" -> armata.setIcon(Verdi);

            case "Arancioni" -> armata.setIcon(Arancioni);

            case "Lilla" -> armata.setIcon(Lilla);

            case "Blu" -> armata.setIcon(Blu);

            case "Bianche" -> armata.setIcon(Bianche);

            case "Nere" -> armata.setIcon(Nere);
        }
    }
    public void Posizionamento(ArrayList<Giocatore> Giocatore) {
        for (Giocatore i : Giocatore) {

            for (Territorio j : i.getTerritoriInPossessoGiocatore()) {
                String colore = i.getColoreArmataGiocatore();
                Armata_GUI armata = new Armata_GUI(j.getNomeTerritorio(), j.getCoordinataX(), j.getCoordinataY(), j.getNumeroArmate());


                switch (colore) {
                    case "Verdi" -> {


                        armata.setIcon(Verdi);
                        ActionListener listener = new AzioniIcone().DistinguiOperazioniBottone(armata);
                        armata.addActionListener(listener);
                        armateGUi.add(armata);
                        label.add(armata);
                    }


                    case "Arancioni" -> {


                        armata.setIcon(Arancioni);
                        ActionListener listener = new AzioniIcone().DistinguiOperazioniBottone(armata);
                        armata.addActionListener(listener);
                        armateGUi.add(armata);
                        label.add(armata);


                    }

                    case "Lilla" -> {


                        armata.setIcon(Lilla);
                        ActionListener listener = new AzioniIcone().DistinguiOperazioniBottone(armata);
                        armata.addActionListener(listener);
                        armateGUi.add(armata);
                        label.add(armata);


                    }

                    case "Blu" -> {

                        armata.setIcon(Blu);
                        ActionListener listener = new AzioniIcone().DistinguiOperazioniBottone(armata);
                        armata.addActionListener(listener);
                        armateGUi.add(armata);
                        label.add(armata);


                    }

                    case "Bianche" -> {

                        armata.setIcon(Bianche);
                        ActionListener listener = new AzioniIcone().DistinguiOperazioniBottone(armata);
                        armata.addActionListener(listener);
                        armateGUi.add(armata);
                        label.add(armata);


                    }

                    case "Nere" -> {

                        armata.setIcon(Nere);
                        ActionListener listener = new AzioniIcone().DistinguiOperazioniBottone(armata);
                        armata.addActionListener(listener);
                        armateGUi.add(armata);
                        label.add(armata);


                    }
                }
            }


        }
    }

    public void InizioMatch(){
        // Crea un oggetto JFrame

        JFrame frame = new JFrame("Inizia Partita");

        // Crea un pannello per contenere i bottoni
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Crea i due bottoni
        JButton Inizio = new JButton("Inizia nuova partita");

        Inizio.addActionListener(e -> {
            OrganizzazioneRisiko.scelta = true;
            OrganizzazioneRisiko.SemaforoOperazioni.release();
            frame.dispose();

        });



        JButton Continui = new JButton("Continua partita");

        Continui.addActionListener(e -> {
            OrganizzazioneRisiko.scelta = false;
            OrganizzazioneRisiko.SemaforoOperazioni.release();
            frame.dispose();
        });

        if(!OrganizzazioneRisiko.FilePresente()){
            Continui.setEnabled(false);
        }



        // Imposta la dimensione dei bottoni
        Dimension buttonDimension = new Dimension(250, 60);
        Inizio.setPreferredSize(buttonDimension);
        Continui.setPreferredSize(buttonDimension);

        // Configura i vincoli del layout per centrare i bottoni
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Aggiunge uno spazio intorno ai bottoni
        gbc.fill = GridBagConstraints.NONE; // Permette ai bottoni di espandersi orizzontalmente
        gbc.anchor = GridBagConstraints.CENTER; // Imposta l'allineamento al centro
        gbc.weightx = 1.0; // Rende i bottoni espandibili orizzontalmente se necessario

        // Aggiungi i bottoni al pannello con i vincoli del layout
        panel.add(Inizio, gbc);

        gbc.gridy = 1;
        panel.add(Continui, gbc);

        // Aggiungi il pannello al frame
        frame.add(panel);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true); // Rende il frame visibile
    }

    public ArrayList<String> CreazioneGiocatori(){
        ArrayList<String> nomiGiocatori = new ArrayList<>();
        Semaphore Semaforo = new Semaphore(0);
        JFrame frame = new JFrame("Creazione Giocatori");

        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);


        JPanel panel = new JPanel();

        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Aggiungi il titolo sopra i campi di input
        JLabel titleLabel = new JLabel("Inserisci i nomi dei giocatori:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; // colonna 0
        gbc.gridy = 0; // riga 0 (prima riga)
        gbc.gridwidth = 2; // occupa due colonne
        gbc.anchor = GridBagConstraints.CENTER; // allineamento al centro
        gbc.insets = new Insets(10, 10, 20, 10); // spazio intorno al componente
        panel.add(titleLabel, gbc);



        // Aggiungi i campi di input e le label "Giocatore n" al pannello
        for (int i = 1; i <= 6; i++) {
            JLabel label = new JLabel("Giocatore " + i + ": ");
            JTextField textField = new JTextField(20);

            // Imposta l'allineamento orizzontale della label al centro
            label.setHorizontalAlignment(SwingConstants.CENTER);

            // Configura le posizioni dei componenti nel grid
            gbc.gridx = 0; // colonna 0
            gbc.gridy = i; // riga corrispondente al giocatore n (a partire dalla riga 1)
            gbc.gridwidth = 1; // torna a occupare una sola colonna
            gbc.anchor = GridBagConstraints.CENTER; // allineamento al centro
            gbc.insets = new Insets(5, 5, 5, 5); // spazio intorno al componente
            panel.add(label, gbc);

            gbc.gridx = 1; // colonna 1
            gbc.gridy = i; // riga corrispondente al giocatore n (a partire dalla riga 1)
            panel.add(textField, gbc);
        }

        // Crea un bottone "Salva" per salvare i nomi dei giocatori
        JButton saveButton = new JButton("Salva");
        gbc.gridx = 0; // colonna 0
        gbc.gridy = 7; // riga 7 (dopo i campi di input)
        gbc.gridwidth = 2; // occupa due colonne
        gbc.anchor = GridBagConstraints.CENTER; // allineamento al centro
        gbc.insets = new Insets(20, 10, 10, 10); // spazio intorno al componente
        panel.add(saveButton, gbc);

        // Aggiungi un ActionListener al bottone "Salva"
        saveButton.addActionListener(e -> {


            // Salva i nomi dei giocatori dai campi di input nell'ArrayList
            for (int i = 1; i <= 6; i++) {
                Component component = panel.getComponent(i * 2); // Ottieni il componente JTextField
                if (component instanceof JTextField textField) {
                    String nomeGiocatore = textField.getText();
                    if(!nomeGiocatore.isEmpty() && !nomiGiocatori.contains(nomeGiocatore)) {
                        nomiGiocatori.add(nomeGiocatore);


                    }



                }
            }

            // Stampa i nomi dei giocatori salvati
            if(nomiGiocatori.size() > 2) {
                Semaforo.release();
                frame.dispose();
            }else{
                JOptionPane.showMessageDialog(null, "Numero minimo di giocatori diversi: 3");
                nomiGiocatori.clear();
            }
        });

        // Aggiungi il pannello al frame
        frame.add(panel);

        // Rendi il frame visibile
        frame.setVisible(true);
        frame.setResizable(false);

        try {
            Semaforo.acquire();
        }catch(InterruptedException e){
            System.out.println(" ");
        }


        return nomiGiocatori;

    }
}

