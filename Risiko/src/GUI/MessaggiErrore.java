package GUI;

import javax.swing.*;

public class MessaggiErrore {

    /**
     * Mostra un messaggio di errore nel caso il giocatore sbagli qualcosa
     * @param Messaggio messaggio in modo da mostrare e aiutare il giocatore
     */
    private static void MostraErrore(String Messaggio){
        JOptionPane.showMessageDialog(null, Messaggio, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    public static void TerritoriNonConfinanti(String NomeTerritorio){
        MostraErrore("Il territorio: " + NomeTerritorio + " non è confinante");
    }

    public static void TerritorioInTuoPossesso(){
        MostraErrore("Non puoi attaccare un tuo territorio");
    }

    public static void MancanzaDiArmate(){
        MostraErrore("Non possiedi abbastanza armate");
    }

    public static void NumeroValido(){
        MostraErrore("Inserisci un numero valido");
    }

    public static void TerritorioNonProprietario(){
        MostraErrore("Non sei il proprietario del territorio");
    }

    public static void TerritorioSenzaArmate(){
        MostraErrore("Non puoi lasciare un territorio senza nemmeno un'armata");
    }

    public static void RimozioneArmate(){
        MostraErrore("Non puoi rimuovere armate da un territorio dove non ne hai aggiunte");
    }



}
