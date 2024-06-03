package App.Gestione_Turno;

import GUI.AzioneChiusura;
import GUI.MessaggiInfo;
import App.Partenza.Carta;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import javax.swing.*;
import java.util.ArrayList;


public class GestioneTris {

    /**
     * Permette di controllare che il tris sia in regola prima di passare al controllare la tipologia di tris
     * in seguito si comunicherà il verdetto e saranno assegnate le armate
     * @param giocatore giocatore che richiede il tris
     * @param cartaList elenco di carte (per forza 3) che saranno controllate per l'assegnazione delle armate
     */
    public void Tris(Giocatore giocatore, JList<Carta> cartaList){

        ArrayList<Carta> mazzo_carte_giocatore = giocatore.getMazzoCarteGiocatore();    //ottengo le carte del giocatore
        ArrayList<Carta> tris;

        int contatore_territori_posseduti_nel_tris;

        if (mazzo_carte_giocatore.size() >= 3) {

            tris = SelezioneCarteTris(cartaList); //ottengo le carte selezionate dal giocatore in modo da controllare che si possa passare al controllo

            if(tris != null) {

                String TrisOttenuto = ControlloAccoppiamentiTris(tris); //controllo il tris

                contatore_territori_posseduti_nel_tris = ArmateTrisTerritori(giocatore, tris); //le carte hanno anche un territorio quindi in caso il giocatore
                                                                                                //controlli quel territorio 2 armate in più

                if (TrisOttenuto.equals("Nulla")) {

                    JOptionPane.showMessageDialog(null, "Non possiedi tris");
                } else {

                    ArmateTris(TrisOttenuto, contatore_territori_posseduti_nel_tris * 2, giocatore);    //passo il tris e i territori per assegnare le armate
                    RimozioneCarte(tris, giocatore); //rimuovo le carte utilizzate dal giocatore
                    GestioneTurno.GiocataTris = true; //permette di far si che il giocatore possa inserire le armate

                    GestioneSceltaGiocatore.RipristinoSemaforoScelta();
                    AzioneChiusura.NascondiMenuGiocatore();
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Non disponi di abbastanza carte");
        }

    }


    /**
     * Permette di scegliere le carte dal mazzo personale del giocatore
     * @param cartaList Rappresenta il mazzo personale del giocatore
     * @return ritorna null nel caso le carte siano != 3 o ritorna le carte scelte dal giocatore
     */
    private ArrayList<Carta> SelezioneCarteTris(JList<Carta> cartaList) {
        int[] CarteSelezionate = cartaList.getSelectedIndices();    //ottengo gli indici dal mazzo del giocatore

        if (CarteSelezionate.length != 3) {
            JOptionPane.showMessageDialog(null, "Seleziona 3 carte.", "Errore", JOptionPane.ERROR_MESSAGE);
            return null;
        } else {

            ArrayList<Carta> CarteScelte= new ArrayList<>();
            for (int index : CarteSelezionate) {    //scorro e ottengo le carte

                Carta selectedCard = cartaList.getModel().getElementAt(index);
                CarteScelte.add(selectedCard);
            }

            return CarteScelte;
        }
    }

    /**
     * Elimino le carte dal mazzo del giocatore in modo che non possa riutilizzare le carte
     * @param scelta Rappresenta le carte scelte dal giocatore
     * @param giocatore Giocatore che utilizza il tris
     */
    public void RimozioneCarte(ArrayList<Carta> scelta, Giocatore giocatore){

        for(int x = 0; x < 3; x++){
            giocatore.Delete_carta(scelta.get(x));

        }
    }

    /**
     * Permette di stabilire quale tris ha il giocatore in modo da poter capire quante armate supplementari avrà
     * @param Tris Rappresenta il tris di carte scelte dal giocatore
     * @return ritorna o il tipo di tris o nulla in caso le carte non corrispondono a nessun tris
     */
    public String ControlloAccoppiamentiTris(ArrayList<Carta> Tris){
        if(ControlloArtiglieria(Tris )){
            return "Artiglieria";
        }else if(ControlloFanteria(Tris )){
            return "Fanteria";
        }else if(ControlloCavalleria(Tris )){
            return "Cavalleria";
        }else if(ControlloMisto(Tris )){
            return "Misto";
        }else if(ControlloJolly( Tris )){
            return "Jolly";
        }else{
            return "Nulla";
        }
    }


    /**
     * Permette di controllare il tris controllando che le carte abbiano tutte la figura dell'artiglieria, questo tris richiede 3 carte artiglieria
     * @param Tris carte del giocatore scelte
     * @return esito del controllo
     */
    public boolean ControlloArtiglieria(ArrayList<Carta> Tris){
        int cont_Artiglieria = 0;

        for(Carta i: Tris) {
            if (i.getFigura().contains("Artiglieria")) {    //controllo della figura
                cont_Artiglieria++;
            }
        }

        return cont_Artiglieria == 3;
    }

    /**
     * Permette di controllare il tris controllando che le carte abbiano tutte la figura della fanteria, questo tris richiede 3 carte fanteria
     * @param Tris tris di carte scelto dall'utente
     * @return esito del controllo
     */
    public boolean ControlloFanteria(ArrayList<Carta> Tris){
        int cont_Fanteria= 0;

        for(Carta i: Tris) {
            if (i.getFigura().contains("Fanteria")) {
                cont_Fanteria++;
            }
        }

        return cont_Fanteria == 3;
    }


    /**
     * Permette di controllare il tris controllando che le carte abbiano tutte la figura della cavalleria, questo tris richiede 3 carte cavalleria
     * @param Tris tris del giocatore
     * @return esito del controllo
     */
    public boolean ControlloCavalleria(ArrayList<Carta> Tris){
        int cont_Cavalleria = 0;

        for(Carta i: Tris ) {
            if (i.getFigura().contains("Cavalleria")) {
                cont_Cavalleria++;
            }
        }

        return cont_Cavalleria == 3;
    }


    /**
     * Permette di controllare il tris controllando che le carte abbiano tutte le figure, questo tris richiede 1 carta per ogni tipo
     * @param Tris tris di carte del giocatore
     * @return esito del controllo
     */
    public boolean ControlloMisto(ArrayList<Carta> Tris){
        int cont_A = 0;
        int cont_B = 0;
        int cont_C = 0;
        for(Carta i: Tris){
            if(i.getFigura().contains("Artiglieria")){  //controllo ci sia esattamente una carta per ogni figura
                cont_A++;

            } else if (i.getFigura().contains("Fanteria")) {
                cont_B++;

            }else{
                cont_C++;
            }
        }

        return cont_A == cont_B && cont_A == cont_C;
    }


    /**
     * Permette di controllare il tris controllando che le carte abbiano due carte figura uguali e un jolly
     * @param Tris tris di carte scelto dall'utente
     * @return esito del controllo
     */
    public boolean ControlloJolly(ArrayList<Carta> Tris){
        int cont_jolly = 0;
        int cont_A = 0;
        int cont_F = 0;
        int cont_C = 0;

        for(Carta i: Tris){
            if(i.getFigura().contains("Jolly")){    //controllo che il jolly sia presente
                cont_jolly++;
            }else{
                switch (i.getFigura()){     //controllo che le altre due carte siano uguali
                    case "Artiglieria" -> cont_A++;
                    case "Cavalleria" -> cont_C++;
                    case "Fanteria" -> cont_F++;
                }
            }
        }
        return cont_jolly == 1 && (cont_F == 2 || cont_A == 2 || cont_C == 2);
    }


    /**
     * Nel caso in cui il tris giocato dal giocatore corrisponda a un tris presente nel gioco si provvederà ad assegnare le armate al giocatore
     * aggiungendo a quelle del tris anche le eventuali armate dovuti ai territori presenti sulla carta
     * @param tris tris del giocatore
     * @param territori numero di territori presenti sulla carta di cui in possesso il giocatore che aggiungeranno 2 armate per ogni territorio
     * @param giocatore giocatore che ha giocato il tris
     */
    public void ArmateTris(String tris, int territori, Giocatore giocatore){

        switch(tris) {
            case "Artiglieria" -> {
                giocatore.setArmate(4 + territori);
                MessaggiInfo.ArmateTrisOttenute(4 , territori);
            }
            case "Fanteria" -> {
                giocatore.setArmate(6 + territori);
                MessaggiInfo.ArmateTrisOttenute(6 , territori);

            }
            case "Cavalleria" -> {
                giocatore.setArmate(8 + territori);
                MessaggiInfo.ArmateTrisOttenute(8 , territori);
            }
            case "Misto" -> {
                giocatore.setArmate(10 + territori);
                MessaggiInfo.ArmateTrisOttenute(10 , territori);

            }
            case "Jolly" -> {
                giocatore.setArmate(12 + territori);
                MessaggiInfo.ArmateTrisOttenute(12 , territori);
            }
        }
    }

    /**
     * Permette di controllare che il giocatore sia in possesso del territorio presente sulla carta
     * @param giocatore giocatore che ha giocato il tris
     * @param Tris tris scelto dal giocatore
     * @return ritorno del conto dei territori presenti sulla carta in possesso del giocatore
     */
    public int ArmateTrisTerritori(Giocatore giocatore, ArrayList<Carta> Tris){
        ArrayList<Territorio> Territori_giocatore = giocatore.getTerritoriInPossessoGiocatore();
        int cont = 0;

        for(Carta i: Tris){
            for(Territorio j: Territori_giocatore){
                
                if(j.getNomeTerritorio().equals(i.getTerritorio())){
                    cont++;
                }
            }
        }

        return cont;
    }

}
