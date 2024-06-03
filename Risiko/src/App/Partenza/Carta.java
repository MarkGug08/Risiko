package App.Partenza;

import java.io.Serializable;

public class Carta implements Serializable {
    private final String Territorio;
    private final String figura;


    public Carta(String territorio, String figura){
        this.figura = figura;
        this.Territorio = territorio;
    }



    public String getTerritorio() {
        return Territorio;
    }

    public String getFigura() {
        return figura;
    }






}
