package App.Gestione_Turno;


import GUI.AzioniIcone;
import GUI.MessaggiErrore;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GestioneArmateTurno {

    static ArrayList<Map<String, Integer>> ControlloreTerritoriSelezionati = new ArrayList<>();
    //permette di controllare se un territorio ha avuto un aumento di armate in modo tale da poter eventualmente
    //rimuovere le armate da esso


    /**
     * Permette di inserire le armate ogni turno controllando armate non vengano rimosse dove non è possibile
     * e controllare il proprietario del territorio
     * @param nomeTerritorio nome del territorio dove si andrà a operare
     * @param giocatore giocatore con cui operare con le sue armate e controllare il proprietario
     * @return ritorna il valore del territorio in modo da aggiornare il valore nell'interfaccia
     */
    public static String InserimentoArmateTurno(String nomeTerritorio, Giocatore giocatore) {

        int numeroArmata;       //numero di armate che si andranno ad aggiungere o rimuovere

        int armate_giocatore = giocatore.getArmate();

        Territorio territorio = GestioneSceltaGiocatore.RicercaTerritorio(nomeTerritorio);  //territorio scelto


        //controllo proprietario
        if(territorio.getProprietario().equals(giocatore)) {


                numeroArmata = AzioniIcone.DialogoInserimentoArmate(territorio);    //armate ottenute

                numeroArmata -= territorio.getNumeroArmate();

                int contr = TrovaModifiche(territorio);     //controllo se il territorio scelto è presente nei territori modificati


                if ((armate_giocatore - numeroArmata >= 0) ) {        //condizione per evitare che le armate vadano in negativo

                    if(numeroArmata >= 0) {

                        Map<String, Integer> Territorio = new HashMap<>();      //controllore territori che hanno ricevuto armate
                        Territorio.put(territorio.getNomeTerritorio(), territorio.getNumeroArmate());
                        territorio.AggiungiArmate(numeroArmata);         //aggiunge le armate al territorio
                        giocatore.RimuoviArmate(numeroArmata);                          //rimuove le armate al giocatore

                        ControlloreTerritoriSelezionati.add(Territorio);

                        GestioneGiocatore.RisvegliaSemaforoGestioneArmateTurno();   //risveglio il semaforo per aggiornare i dati

                    }else{

                        if(contr != -1){   //se il valore è diverso da -1, il territorio selezionato è stato modificato quindi si controllerà il limite massimo di rimozione delle armate
                            int armateTerritorioLimite = ControlloreTerritoriSelezionati.get(contr).get(territorio.getNomeTerritorio());

                            if(territorio.getNumeroArmate() + numeroArmata >= armateTerritorioLimite){
                                //controllo che le armate non superino il valore iniziale del territorio
                                territorio.AggiungiArmate(numeroArmata);
                                giocatore.setArmate(numeroArmata * (-1));


                                GestioneGiocatore.RisvegliaSemaforoGestioneArmateTurno();
                                return String.valueOf(territorio.getNumeroArmate());    //ritorno il valore delle armate all'interfaccia
                            }else{
                                MessaggiErrore.RimozioneArmate();
                            }
                        }else{
                            MessaggiErrore.RimozioneArmate();
                        }
                    }
                } else {
                    MessaggiErrore.MancanzaDiArmate();
                }
        }else{
            MessaggiErrore.TerritorioNonProprietario();
        }

        return String.valueOf(territorio.getNumeroArmate());

    }


    /**
     * Controlla che il territorio sia stato modificato in precedenza
     * @param territorio viene passato il territorio in modo da controllare l'effettiva presenza
     * @return ritorna o la posizione del territorio o che il territorio non è presente
     */
    public static int TrovaModifiche(Territorio territorio){
        for(int x = 0; x < ControlloreTerritoriSelezionati.size(); x++){
            Map<String, Integer> territorioMap = ControlloreTerritoriSelezionati.get(x);
            if(territorioMap.containsKey(territorio.getNomeTerritorio())){

                return x;
            }
        }

        return -1;
    }

    /**
     * Assegna le armate in base al numero di territori del giocatore (il numero minimo di armate è 3)
     * @param giocatore giocatore che otterrà le armate
     */
    protected void ArmateSupplementari(Giocatore giocatore) {
        ArrayList<Territorio> numero_territori = giocatore.getTerritoriInPossessoGiocatore();        //territori in possesso del giocatore

        //Per regola minimo tre armate si ricevono altrimenti il numero dei territori / tre
        giocatore.setArmate(Math.max(numero_territori.size() / 3, 3));
    }       //all'inizio di ogni turno aggiunge delle armate al giocatore in base ai territori





}
