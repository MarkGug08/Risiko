package App.Gestione_file;

import App.FasePreliminare.InizializzazioneCarte;
import App.FasePreliminare.InizializzazioneTerritori;
import App.Partenza.Carta;
import App.Partenza.Continente;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import java.io.*;
import java.util.ArrayList;




public class Salvataggio {

    public static ArrayList<Giocatore> Giocatori = new ArrayList<>();

    public static ArrayList<Territorio> Territori = new ArrayList<>();
    public static ArrayList<Carta> MazzoCarte = new ArrayList<>();
    public static ArrayList<Continente> Continenti = new ArrayList<>();

    public static int Turno;

    public static void SalvataggioPartita(int Turno){

        CreazioneFile("Territori.ser", InizializzazioneTerritori.getTerritori());
        CreazioneFile("Continenti.ser", InizializzazioneTerritori.getContinenti());
        CreazioneFile("Turno.ser", Turno);
        CreazioneFile("MazzoDiCarte.ser", InizializzazioneCarte.getMazzoCarte());


    }

    public void CaricaPartita(){
        CaricaTerritori();
        MazzoCarte = CaricaFile("MazzoDiCarte.ser");
        Continenti = CaricaFile("Continenti.ser");
        CaricaTurno();

    }



    private static void CreazioneFile(String NomeFileSerializzabile, Object OggettoDaSalvare){
        String Path = "Risiko/src/Risorse/FileSerializzati/";
        try( FileOutputStream File = new FileOutputStream(Path + NomeFileSerializzabile);) {
            ObjectOutputStream Output = new ObjectOutputStream(File);
            Output.writeObject(OggettoDaSalvare);
        }catch (IOException e){
            System.out.println("qualcosa è andato storto");
            e.printStackTrace();
        }
    }


    public <T> ArrayList<T> CaricaFile(String fileName) {
        String Path = "Risiko/src/Risorse/FileSerializzati/";
        ArrayList<T> arrayList = new ArrayList<>();

        try (FileInputStream fileIn = new FileInputStream(Path + fileName);){
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Object obj = in.readObject();
            ArrayList<?> ar = (ArrayList<?>) obj;

            for (Object x : ar) {
                arrayList.add((T) x);
            }

            in.close();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("Si è verificato un errore: " + fileName);
        }
        return arrayList;
    }


    public void CaricaTerritori(){

            Territori = CaricaFile("Territori.ser");
            for(Territorio x: Territori){
                if(!(Giocatori.contains(x.getProprietario()))){
                    Giocatori.add(x.getProprietario());
                }
            }
    }
    public void CaricaTurno(){
        try(FileInputStream fileIn = new FileInputStream("Risiko/src/Risorse/FileSerializzati/Turno.ser");) {
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Turno =  (Integer) in.readObject();
            in.close();

        }catch (ClassNotFoundException | IOException e){
            System.out.println("Si è verificato un errore");
        }
    }



    public static void Cancellazione(String PathFile){
        File file = new File(PathFile);
        file.delete();

    }



    protected static ArrayList<Giocatore> getGiocatori() {
        return Giocatori;
    }
    protected static ArrayList<Carta> getMazzoCarte() {
        return MazzoCarte;
    }

    protected static int getTurno() {
        return Turno;
    }


    protected static ArrayList<Territorio> getTerritori() {
        return Territori;
    }
    protected static ArrayList<Continente> getContinenti() {
        return Continenti;
    }
}
