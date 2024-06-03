package GUI;

import App.Partenza.Giocatore;

import javax.swing.*;
import java.awt.event.ActionListener;

import static App.FasePreliminare.InizializzazioneGiocatori.getGiocatore;
import static GUI.Interfaccia.*;

public class DatiGiocatore {
    //Informazioni del giocatore corrente


    private static int Turno = 0;

    /**
     * Mostra le statistiche del giocatore nell'interfaccia in modo da aiutarlo
     * @param Giocatore giocatore che gioca attualmente
     */
    public void BarraInformazioni(Giocatore Giocatore) {

        TurnoDelGiocatore.setText("Turno di: "+ Giocatore.getNomeGiocatore());
        ColoreArmataGiocatore.setText("Colore armata: " + Giocatore.getColoreArmataGiocatore());
        ArmateADisposizione.setText("Armate a disposizione: "+ Giocatore.getArmate());
        TerritoriInPossesso.setText("Territori in tuo possesso: "+ Giocatore.getTerritoriInPossessoGiocatore().size());
        ContinentiInPossesso.setText("Continenti in tuo possesso: "+ Giocatore.getContinentiInTuoPossesso().size());
    }




    //Mostra obiettivo del giocatore
    public ActionListener MostraObiettivo() {
        return e -> JOptionPane.showMessageDialog(null, "Il tuo obiettivo è: " + getGiocatore(Turno).getObiettivo().getDescrizione());
    }



    public static int getTurno() {
        return Turno;
    }

    public static void setTurno(int x) {
        Turno = x;
    }






}
