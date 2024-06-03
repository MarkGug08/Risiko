package App.FasePreliminare;

import GUI.Interfaccia;
import App.Partenza.Giocatore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InizializzazioneGiocatori {

    private static ArrayList<Giocatore> Giocatori = new ArrayList<>();        //Contiene tutti i giocatori


    /**
     * Permette di creare il giocatore tramite un'interfaccia e assegna ad ognuno di essi
     * un'armata diversa
     */
    protected void CreaGiocatore(){
        ArrayList<String> NomiGiocatori;

        List<String> colori_armate = new ArrayList<>();
        colori_armate.add("Verdi");
        colori_armate.add("Arancioni");
        colori_armate.add("Lilla");
        colori_armate.add("Blu");
        colori_armate.add("Bianche");
        colori_armate.add("Nere");

        Collections.shuffle(colori_armate);

        NomiGiocatori = new Interfaccia().CreazioneGiocatori();


        for(int x = 0; x < NomiGiocatori.size(); x++) {
            Giocatori.add(new Giocatore(NomiGiocatori.get(x)));
            Giocatori.get(x).setColoreArmataGiocatore(colori_armate.get(x));

        }
        Collections.shuffle(Giocatori);
    }       //Creazione dei giocatori




    public static ArrayList<Giocatore> getGiocatori() {
        return Giocatori;
    }

    //permette di ottenere un determinato giocatore tramite index
    public static Giocatore getGiocatore(int index){
        return Giocatori.get(index);
    }

    public static void setGiocatori(ArrayList<Giocatore> giocatori) {
        Giocatori = giocatori;
    }
}
