package App.FasePreliminare;

import App.Partenza.Carta;
import App.Partenza.Territorio;

import java.util.ArrayList;
import java.util.Collections;



public class InizializzazioneCarte {


    private static ArrayList<Carta> MazzoCarte = new ArrayList<>();       //mazzo di carte con le figure per fare i tris


    /**
     * Permette di creare il mazzo di carte che sarà distribuito durante il gioco
     * Il mazzo è costituito da 14 carte per ogni tipologia (Artiglieria, Fanteria, Cavalleria) ognuno
     * rappresentando un territorio diverso. Oltre queste sono presenti due jolly per la giocata di un tris
     */
    public static void CreaMazzo() {

        int x = 0;
        for( Territorio i: InizializzazioneTerritori.getTerritori()){
            if(x < 14) {
                MazzoCarte.add(new Carta(i.getNomeTerritorio(), "Artiglieria"));
            }else if(x < 28){
                MazzoCarte.add(new Carta(i.getNomeTerritorio(), "Fanteria"));
            }else{
                MazzoCarte.add(new Carta(i.getNomeTerritorio(), "Cavalleria"));
            }
            x++;
        }

        MazzoCarte.add(new Carta(null, "Jolly" ));          //due carte hanno la carta jolly che servirà per la combinazione speciale
        MazzoCarte.add(new Carta(null, "Jolly" ));

        Collections.shuffle(MazzoCarte);    //mischio il mazzo
    }


    public static ArrayList<Carta> getMazzoCarte() {
        return MazzoCarte;
    }
    public static void setMazzoCarte(ArrayList<Carta> Mazzo) {
        MazzoCarte = Mazzo;
    }
}
