package GUI;

import App.Gestione_Turno.GestioneTris;
import App.Partenza.Carta;
import App.Partenza.Dado;
import App.Partenza.Giocatore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatore;
import static App.Gestione_Turno.GestioneSceltaGiocatore.*;
import static GUI.AzioneChiusura.MenuGiocatore;
import static GUI.AzioneChiusura.NascondiMenuGiocatore;
import static GUI.AzioniIcone.setControlloreSelezioneTerritorioAttacco;
import static GUI.AzioniIcone.setPremutaBottone;
import static GUI.DatiGiocatore.getTurno;
import static GUI.Interfaccia.*;


public class AzioneScelta {

    private static JPanel mainPanel = new JPanel(new GridLayout(1, 2));
    /**
     * Azione per la dichiarazione di un attacco tra due territori
     *
     */
    protected ActionListener DichiaraAttacco() {
        return e -> {
            FrameRisultatiDadi.dispose();   //cancella i risultati dei dadi dall'interfaccia di un precedente attacco
            MessaggioComparsa();    //fa comparire un messaggio in modo da guidare il giocatore

            getGiocatore(getTurno()).setAzioneDaSvolgere(1);    //permette di far partire l'attacco

            messaggioLabel.setText("Seleziona il territorio attaccante");
            AnnullaOperazione.setVisible(true); //mostro il tasto per annullare l'operazione

            setPremutaBottone(true); //permette di premere le armate
            RipristinoSemaforoScelta(); //ripristino il semaforo che andrà poi in quello dell'operazione

        };
    }

    /**
     * Permette di spostare delle armate tra due territori
     *
     */
    protected ActionListener EffettuaSpostamento() {
        return e -> {
            FrameRisultatiDadi.dispose();   //eliminazione dei precedenti risultati di un precedente attacco
            if(getGiocatore(getTurno()).getSpostamentoEffettuato() == 0) {  //controllo uno spostamento non è stato effettuato in precedenza
                getGiocatore(getTurno()).setAzioneDaSvolgere(2);    //seleziono l'operazione dello spostamento
                MessaggioComparsa();
                messaggioLabel.setText("Seleziona il territorio di partenza");

                AnnullaOperazione.setVisible(true);
                setPremutaBottone(true);
            }else {
                MessaggiInfo.SpostamentoEffettuato();       //messaggio in caso lo spostamento è stato già eseguito
            }
            RipristinoSemaforoScelta();

        };
    }

    /**
     * Permette di visualizzare il mazzo di carte del giocatore
     *
     */
    protected ActionListener VisualizzaMazzo (){
        return e -> {

            FrameRisultatiDadi.dispose();
            getGiocatore(getTurno()).setAzioneDaSvolgere(3);    //operazione del mostra carte
            RipristinoSemaforoScelta();

        };
    }

    /**
     * Permette di annullare l'operazione
     *
     */
    protected ActionListener AnnullaOperazione(){
        return e -> {

            setPrimoTerritorio(null);   //settando i territori nulli nessuna operazione sarà compiuta
            setSecondoTerritorio(null);
            RipristinoSemaforoOperazioni();

            setControlloreSelezioneTerritorioAttacco(0);
            //permette di tornare all'inizio nel caso in cui il giocatore voglia sferrare un attacco con un altro territorio



            messaggioLabel.setVisible(false);
            MenuGiocatore();
            setPremutaBottone(false);   //annullo eventuali operazioni di premuta del bottone
            AnnullaOperazione.setVisible(false);
            ConcludiTurno.setEnabled(true);

        };
    }

    /**
     * Permette la visualizzazione delle carte in modo che possano essere selezionate ed eventualmente giocate per un tris
     * @param giocatore colui che richiama l'azione in modo da poter visualizzare le sue carte
     */
    public static void MostraCarte(Giocatore giocatore) {
        ArrayList<Carta> MazzoCarteGiocatore = giocatore.getMazzoCarteGiocatore();

        DefaultListModel<Carta> listModel = new DefaultListModel<>();   //aggiungo le carte selte
        for (Carta carta : MazzoCarteGiocatore) {
            listModel.addElement(carta);
        }

        JList<Carta> cartaList = new JList<>(listModel);

        //renderer della cella per personalizzare la visualizzazione degli elementi della lista.
        cartaList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,   //imposto la visualizzazione delle carte
                                                          boolean isSelected, boolean cellHasFocus) {
                // Ottieni il componente della cella dalla superclasse
                JLabel renderer = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Carta carta) {
                    // Imposta il testo della cella con territorio e figura nella stessa riga
                    renderer.setText("Territorio: " + carta.getTerritorio() + ", Figura: " + carta.getFigura());
                }

                return renderer;
            }
        });

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JScrollPane(cartaList), BorderLayout.CENTER);

        JButton giocaTrisButton = new JButton("Gioca Tris");
        giocaTrisButton.addActionListener(new AzioneScelta().GiocaTris(cartaList));



        listPanel.add(giocaTrisButton, BorderLayout.SOUTH);

        JFrame f = new JFrame();
        f.getContentPane().add(listPanel);

        // Imposta le dimensioni della finestra e altre proprietà
        f.setSize(300, 200);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    /**
     * Operazione per la giocata del tris che in seguito andrà al controllo
     * @param cartaList carte selezionate dal giocatore
     *
     */
    public ActionListener GiocaTris(JList<Carta> cartaList) {
        return e -> new GestioneTris().Tris(getGiocatore(getTurno()), cartaList);
    }


    /**
     * Permette di mostrare a video i risultati dei dadi dovuti allo scontro dai due territori in modo tale che i giocatori possano vedere e confrontare con i risultati
     * @param att   //numero di armate perse dal giocatore che ha sferrato l'attacco
     * @param dif   //numero di armate perse dal giocatore che ha subito l'attacco
     */
    public static void MostraRisultati(int att, int dif) {

        mainPanel.removeAll();  //rimuovo i precedenti risultati
        DefaultListModel<Integer> Attaccante = new DefaultListModel<>();
        DefaultListModel<Integer> Difensore = new DefaultListModel<>();


        for (Integer num : Dado.getRisultatiAttaccante()) {
            Attaccante.addElement(num);
        }

        for (Integer num : Dado.getRisultatiDifensore()) {
            Difensore.addElement(num);
        }

        JList<Integer> list1JList = new JList<>(Attaccante);
        JList<Integer> list2JList = new JList<>(Difensore);

        JPanel listPanel1 = new JPanel(new BorderLayout());
        listPanel1.setBorder(BorderFactory.createTitledBorder("Dadi Attaccante"));
        listPanel1.add(new JScrollPane(list1JList), BorderLayout.CENTER);

        JPanel listPanel2 = new JPanel(new BorderLayout());
        listPanel2.setBorder(BorderFactory.createTitledBorder("Dadi Difensore"));
        listPanel2.add(new JScrollPane(list2JList), BorderLayout.CENTER);

// Creare un pannello generale per contenere entrambi i pannelli delle liste



        JLabel textLabel1 = new JLabel("Armate perse: " + att);
        JLabel textLabel2 = new JLabel("Armate perse: " + dif);
        listPanel1.add(textLabel1, BorderLayout.SOUTH);
        listPanel2.add(textLabel2, BorderLayout.SOUTH);

        mainPanel.add(listPanel1);
        mainPanel.add(listPanel2);

        FrameRisultatiDadi.add(mainPanel);

// Imposta le dimensioni della finestra e altre proprietà
        FrameRisultatiDadi.setSize(300, 200);
        FrameRisultatiDadi.setLocation(0, 300);
        FrameRisultatiDadi.setVisible(true);
        FrameRisultatiDadi.setResizable(false);

// Aggiorna il pannello principale
        FrameRisultatiDadi.revalidate();
        FrameRisultatiDadi.repaint();



    }

    /**
     * Mostra i messaggi per per guidare l'utente
     */
    public static void MessaggioComparsa(){
        NascondiMenuGiocatore();
        messaggioLabel.setVisible(true);
        ConcludiTurno.setEnabled(false);

    }
}
