package App.Partenza;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Giocatore implements Serializable {


    private final String NomeGiocatore;
    private final ArrayList<Territorio> TerritoriInPossessoGiocatore;
    private final HashSet<String> ArmateEliminate = new HashSet<>();
    private final HashSet<Continente> ContinentiInTuoPossesso = new HashSet<>();
    private int NumeroArmateDaSchierare;
    private final ArrayList<Carta> MazzoCarteGiocatore = new ArrayList<>();
    private String ColoreArmataGiocatore;       //Ogni giocatore ha un colore che rappresenta l'armata
    private Obiettivi Obiettivo;
    private int AzioneDaSvolgere;
    private int SpostamentoEffettuato = 0;



    public Giocatore(String nome) {
        this.NomeGiocatore = nome;
        this.TerritoriInPossessoGiocatore = new ArrayList<>();
        this.NumeroArmateDaSchierare = 0;
        this.Obiettivo = null;
        this.ColoreArmataGiocatore = "";
    }



    public String getNomeGiocatore(){            //Restituisce il nome del Giocatore
        return this.NomeGiocatore;
    }



    //territori del giocatore
    
    public void setTerritorio(Territorio Territorio){         //Assegna il Territorio al Giocatore
        this.TerritoriInPossessoGiocatore.add(Territorio);
    }

    public ArrayList<Territorio> getTerritoriInPossessoGiocatore() {
        return TerritoriInPossessoGiocatore;
    }

    public void DeleteTerritorio(Territorio territorio){
            //confronta
        TerritoriInPossessoGiocatore.remove(territorio);

    }




    //continenti del giocatore

    public void setContinentiInTuoPossesso(Continente continente) {
        ContinentiInTuoPossesso.add(continente);
    }

    public void DeleteContinente(Continente continente) {

        try {
            ContinentiInTuoPossesso.remove(continente);
        }catch (NullPointerException ignored){
            
        }
    }

    public HashSet<Continente> getContinentiInTuoPossesso() {
        return ContinentiInTuoPossesso;
    }





    // colore armata del giocatore

    public void setColoreArmataGiocatore(String color) {
        this.ColoreArmataGiocatore = color;
    }

    public String getColoreArmataGiocatore(){
        return ColoreArmataGiocatore;
    }



    //armate del giocatore

    public void setArmate(int Armate){        //Aggiungi un numero di armate
        this.NumeroArmateDaSchierare += Armate;

    }

    public void RimuoviArmate(int Armate){         //Rimuovi un numero di armate
        this.NumeroArmateDaSchierare -= Armate;
    }

    public int getArmate(){              //Restituisce il nome delle armate
        return this.NumeroArmateDaSchierare;
    }



    //obiettivi del giocatore

    public void setObiettivo(Obiettivi Obiettivo){
        this.Obiettivo = Obiettivo;
    }

    public Obiettivi getObiettivo() {
        return Obiettivo;
    }



    // giocatori eliminati dal giocatore
    public HashSet<String> getArmateEliminate() {
        return ArmateEliminate;
    }
    public void setArmateEliminate(String Colore_armata_avversaria) {
        ArmateEliminate.add(Colore_armata_avversaria);
    }




    //mazzo di carte


    public ArrayList<Carta> getMazzoCarteGiocatore() {
        return MazzoCarteGiocatore;
    }

    public void setMazzoCarteGiocatore(Carta carta) {
        MazzoCarteGiocatore.add(carta);
    }

    public void Delete_carta(Carta carta){
        MazzoCarteGiocatore.remove(carta);
    }


    //azione del giocatore
    public int getAzioneDaSvolgere(){
        return AzioneDaSvolgere;
    }
    public void setAzioneDaSvolgere(int azionedaSvolgere) {
        AzioneDaSvolgere = azionedaSvolgere;
    }


    public int getSpostamentoEffettuato() {
        return SpostamentoEffettuato;
    }

    public void setSpostamentoEffettuato(int spostamentoEffettuato) {
        SpostamentoEffettuato = spostamentoEffettuato;
    }
}
