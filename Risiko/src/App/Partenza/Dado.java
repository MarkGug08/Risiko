package App.Partenza;

import GUI.AzioneScelta;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Dado {

    private static final List<Integer> RisultatiDifensore = new ArrayList<>();
    private static final List<Integer> RisultatiAttaccante = new ArrayList<>();


    static Random generator = new Random();


    public void LancioDadi(int armate_attaccante, int armate_difensore){
        for(int x = 0; x < armate_attaccante; x++){             //Per ogni armata corrisponde un dado che viene aggiunto a un arraylist per il confronto
            RisultatiAttaccante.add(generator.nextInt(6) + 1);
        }

        for(int y = 0; y < armate_difensore; y++){
            RisultatiDifensore.add(generator.nextInt(6) + 1);
        }


    }       //permette il lancio di n dadi da parte del difensore e attaccante
    public int[] Confronto() {
        int cont_attaccante = 0;        //calcolo armate perse
        int cont_difensore = 0;
        int[] risultati = new int[2];

        RisultatiAttaccante.sort(Comparator.reverseOrder());       //ordinamento in ordine decrescente per poter vedere i maggiori tramite il ciclo
        RisultatiDifensore.sort(Comparator.reverseOrder());

        if (RisultatiDifensore.size() < RisultatiAttaccante.size()) {
            for (int x = 0; x < RisultatiDifensore.size(); x++) {        //se il difensore ha meno armate
                if (RisultatiAttaccante.get(x) <= RisultatiDifensore.get(x)) {    //se l'attaccante ha un numero minore o uguale vince il difensore e perde un'armata
                    cont_attaccante += 1;
                } else {            //attaccante con numero maggiore
                    cont_difensore += 1;
                }


            }
        } else {
            for (int x = 0; x < RisultatiAttaccante.size(); x++) {        //attaccante con meno armate
                if (RisultatiAttaccante.get(x) <= RisultatiDifensore.get(x)) {
                    cont_attaccante += 1;
                } else {
                    cont_difensore += 1;
                }


            }

        }       //Confronta i risultati ottenuti e calcola le armate perse da entrambi i lati





        risultati[0] = cont_attaccante;
        risultati[1] = cont_difensore;

        AzioneScelta.MostraRisultati(cont_attaccante, cont_difensore);


        RisultatiDifensore.clear();
        RisultatiAttaccante.clear();


        return risultati;
    }

    public static List<Integer> getRisultatiAttaccante() {
        return RisultatiAttaccante;
    }

    public static List<Integer> getRisultatiDifensore() {
        return RisultatiDifensore;
    }
}
