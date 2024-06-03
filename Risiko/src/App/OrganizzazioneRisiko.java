package App;

import App.FasePreliminare.FasePreliminare;
import GUI.Interfaccia;
import App.Gestione_Turno.GestioneTurno;
import App.Gestione_file.RipristinoGioco;

import java.io.File;
import java.util.concurrent.Semaphore;

public class OrganizzazioneRisiko {

    public static boolean scelta;
    public static final Semaphore SemaforoOperazioni = new Semaphore(0);

    Interfaccia Interfaccia = new Interfaccia();
    App.FasePreliminare.FasePreliminare FasePreliminare = new FasePreliminare();
    GestioneTurno turno = new GestioneTurno();

    RipristinoGioco RipristinoGioco = new RipristinoGioco();
    public void Gioco(){

        Interfaccia.InizioMatch();

        try{
            SemaforoOperazioni.acquire();
        }catch(InterruptedException e){

        }


        if(scelta){
            FasePreliminare.Inizio();
            turno.Turno();
        }else{
            RipristinoGioco.Inizio();
            turno.Turno();


        }



    }

    public static boolean FilePresente(){
        String file = "Risiko/src/Risorse/FileSerializzati/Territori.ser";

        File FilediGioco = new File(file);

        return FilediGioco.exists();
    }



}
