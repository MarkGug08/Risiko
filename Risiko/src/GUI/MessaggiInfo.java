package GUI;

import App.Gestione_file.Salvataggio;

import javax.swing.*;

public class MessaggiInfo {

    /**
     * Permette di inserire un messaggio in modo da comunicare avvenimenti in base alle sue operazioni
     * @param Messaggio messaggio per il giocatore
     */
    private static void Messaggio(String Messaggio){
        JOptionPane.showMessageDialog(null, Messaggio);
    }

    public static void ArmateSpostate(){
        Messaggio("Armate spostate");
    }

    public static void SpostamentoEffettuato(){
        Messaggio("Spostamento già effettuato");
    }

    public static void ArmateTrisOttenute(int Tris, int Territori){
        Messaggio("Hai ottenuto "+ (Tris + Territori)+ " armate");
    }

    public static void VittoriaGiocatore(String giocatore){
        Messaggio("Complimenti "+ giocatore + " hai vinto questa partita");

    }
}
