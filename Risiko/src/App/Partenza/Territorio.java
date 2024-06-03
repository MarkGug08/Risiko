package App.Partenza;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Territorio implements Serializable {
    private final String NomeTerritorio;
    private final ArrayList<Territorio> TerritoriAdiacenti;

    private final int CoordinataX;

    private final int CoordinataY;

    private Giocatore Proprietario;
    private int ID;
    private int numeroArmate;

    public Territorio(String nome, int CoordinataX, int CoordinataY){
        this.NomeTerritorio = nome;
        this.TerritoriAdiacenti = new ArrayList<>();
        this.Proprietario = null;
        this.numeroArmate = 0;
        this.ID = 0;
        this.CoordinataX = CoordinataX;
        this.CoordinataY = CoordinataY;

    }

    public String getNomeTerritorio() {
        return this.NomeTerritorio;
    }

    //proprietario del territorio
    public void setProprietario(Giocatore giocatore){           //assegna il proprietario al territorio
        this.Proprietario = giocatore;
    }

    public Giocatore getProprietario(){
        return Proprietario;
    }

    //territori adiacenti al territorio
    public ArrayList<Territorio> getTerritoriAdiacenti(){       //ritorna i territori adiacenti
        return this.TerritoriAdiacenti;
    }
    public void setTerritoriAdiacenti(Territorio... territorio){
        TerritoriAdiacenti.addAll(Arrays.asList(territorio));
    }   //Inserisce i territori adiacenti


    //Id del territorio
    public int get_ID() {
        return ID;
    }
    public void setID(int x){
        this.ID = x;
    }


    //armate del territorio
    public int getNumeroArmate(){
        return this.numeroArmate;
    }


    public void AggiungiArmate(int Armate){
        this.numeroArmate += Armate;
    }
    public void SottraiArmate(int Armate){
        this.numeroArmate -= Armate;
    }


    public int getCoordinataX() {
        return CoordinataX;
    }

    public int getCoordinataY() {
        return CoordinataY;
    }
}
