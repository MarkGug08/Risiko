package App.FasePreliminare;

import App.Partenza.Continente;
import App.Partenza.Giocatore;
import App.Partenza.Territorio;

import java.util.ArrayList;
import java.util.Collections;

public class InizializzazioneTerritori {
    private static ArrayList<Territorio> Territori = new ArrayList<>();       //Territori del gioco

    private static ArrayList<Continente> Continenti = new ArrayList<>();       //Continenti del gioco per il controllo delle armate supplementari

    /**
     * Permette di creare i territori assegnando loro un nome e delle coordinate utilizzate poi
     * per la posizione all'interno della mappa
     */
    protected void CreaTerritori() {
        Territori.add(new Territorio("Alaska", 53, 95));
        Territori.add(new Territorio("Alberta", 135, 145));
        Territori.add(new Territorio("America Centrale", 165, 315));
        Territori.add(new Territorio("Stati Uniti Orientali", 215, 235));
        Territori.add(new Territorio("Groenlandia", 330, 60));
        Territori.add(new Territorio("Territori del Nord Ovest", 145, 95));
        Territori.add(new Territorio("Ontario", 200, 160));
        Territori.add(new Territorio("Quebec", 270, 155));
        Territori.add(new Territorio("Stati Uniti Occidentali", 145, 215));
        Territori.add(new Territorio("Argentina", 240, 530));
        Territori.add(new Territorio("Brasile", 295, 420));
        Territori.add(new Territorio("Perù", 230, 450));
        Territori.add(new Territorio("Venezuela", 225, 360));
        Territori.add(new Territorio("Gran Bretagna", 380, 205));
        Territori.add(new Territorio("Islanda", 400, 125));
        Territori.add(new Territorio("Europa Settentrionale", 485, 215));
        Territori.add(new Territorio("Scandinavia", 492, 125));
        Territori.add(new Territorio("Europa Meridionale", 485, 275));
        Territori.add(new Territorio("Ucraina", 575, 165));
        Territori.add(new Territorio("Europa Occidentale", 400, 285));
        Territori.add(new Territorio("Congo", 525, 480));
        Territori.add(new Territorio("Africa Orientale", 560, 440));
        Territori.add(new Territorio("Egitto", 525, 365));
        Territori.add(new Territorio("Madagascar", 610, 575));
        Territori.add(new Territorio("Africa Del Nord", 455, 400));
        Territori.add(new Territorio("Africa Del Sud", 530, 565));
        Territori.add(new Territorio("Afghanistan", 655, 235));
        Territori.add(new Territorio("Cina", 760, 275));
        Territori.add(new Territorio("India", 705, 330));
        Territori.add(new Territorio("Cita", 780, 155));
        Territori.add(new Territorio("Giappone", 895, 225));
        Territori.add(new Territorio("Kamchatka", 860, 80));
        Territori.add(new Territorio("Medio oriente", 595, 320));
        Territori.add(new Territorio("Mongolia", 800, 220));
        Territori.add(new Territorio("Siam", 780, 373));
        Territori.add(new Territorio("Siberia", 720, 95));
        Territori.add(new Territorio("Urali", 660, 150));
        Territori.add(new Territorio("Jacuzia", 790, 80));
        Territori.add(new Territorio("Australia orientale", 910, 540));
        Territori.add(new Territorio("Indonesia", 780, 470));
        Territori.add(new Territorio("Nuova Guinea", 890, 440));
        Territori.add(new Territorio("Australia Occidentale", 845, 565));

    }         //Creo un metodo che inserisce i territori nella lista Territori



    /**
     * Vengono assegnati i territori adiacenti a un determinato territorio in modo da poter muovere operazioni tra territori
     */
    protected void AssegnaTerritoriAdiacenti(){
        Territori.get(0).setTerritoriAdiacenti(Territori.get(5), Territori.get(1), Territori.get(31));     //Territori del nord ovest->Alberta->Kamchatka
        //Alaska

        Territori.get(1).setTerritoriAdiacenti(Territori.get(0), Territori.get(5), Territori.get(6), Territori.get(8));      //Alaska->Territori del nord ovest->Ontario->Stati uniti occidentali
        //Alberta

        Territori.get(2).setTerritoriAdiacenti(Territori.get(8), Territori.get(3), Territori.get(12));         //Stati uniti Occidentali, Stati uniti orientali, Venezuela
        //America Centrale

        Territori.get(3).setTerritoriAdiacenti(Territori.get(8), Territori.get(2), Territori.get(6), Territori.get(7));        //Stati uniti occidentali->America Centrale-> Ontario->Quebec
        //Stati Uniti orientali

        Territori.get(4).setTerritoriAdiacenti(Territori.get(5), Territori.get(6), Territori.get(7), Territori.get(14));       //Territori del nord ovest->Ontario->Quebec->Islanda
        //Groenlandia

        Territori.get(5).setTerritoriAdiacenti(Territori.get(0), Territori.get(1), Territori.get(6), Territori.get(4));        //Alaska->Alberta->Ontario->Groenlandia
        //Territori del Nord Ovest

        Territori.get(6).setTerritoriAdiacenti(Territori.get(4), Territori.get(5), Territori.get(1), Territori.get(8), Territori.get(3), Territori.get(7));    //Groenlandia->Territori del nord ovest->Alberta->Stati uniti occidentali->Stati uniti orientali->Quebec
        //Ontario

        Territori.get(7).setTerritoriAdiacenti(Territori.get(6), Territori.get(3), Territori.get(4));      //Ontario->Stati uniti orientali->Groenlandia
        //Quebec

        Territori.get(8).setTerritoriAdiacenti(Territori.get(1), Territori.get(6), Territori.get(3), Territori.get(2));    //Alberta->Ontario->Stati uniti orientali->America centrale
        //Stati Uniti Occidentali

        Territori.get(9).setTerritoriAdiacenti(Territori.get(11), Territori.get(10));      //Perù->Brasile
        //Argentina

        Territori.get(10).setTerritoriAdiacenti(Territori.get(12), Territori.get(11), Territori.get(9), Territori.get(24));    //Venezuela->Perù->Argentina->Africa del Nord
        //Brasile

        Territori.get(11).setTerritoriAdiacenti(Territori.get(12), Territori.get(10), Territori.get(9));        //venezuela->Brasile->Argentina
        //Perù

        Territori.get(12).setTerritoriAdiacenti(Territori.get(2), Territori.get(11), Territori.get(10));        //America Centrale->Perù->Brasile
        //Venezuela

        Territori.get(13).setTerritoriAdiacenti(Territori.get(19), Territori.get(15), Territori.get(16), Territori.get(14));      //Europa Occidentale->Europa Settentrionale->Scandinavia->Islanda
        //Gran Bretagna

        Territori.get(14).setTerritoriAdiacenti(Territori.get(4), Territori.get(16), Territori.get(13));       //Groenlandia->Scandinavia->Gran Bretagna
        //Islanda

        Territori.get(15).setTerritoriAdiacenti(Territori.get(18), Territori.get(16), Territori.get(13), Territori.get(19), Territori.get(17));        //Ucraina->Scandinavia->Gran Bretagna->Europa Occidentale->Europa Meridionale
        //Europa Settentrionale

        Territori.get(16).setTerritoriAdiacenti(Territori.get(14), Territori.get(13), Territori.get(15), Territori.get(18));     //Islanda->Gran Bretagna->Europa settentrionale->Ucraina
        //Scandinavia

        Territori.get(17).setTerritoriAdiacenti(Territori.get(19), Territori.get(15), Territori.get(18), Territori.get(32), Territori.get(22), Territori.get(24));    //Europa occidentale, Europa Settentrionale, Ucraina, Medio Oriente, Egitto, Africa del nord
        //Europa Meridionale

        Territori.get(18).setTerritoriAdiacenti(Territori.get(16), Territori.get(15), Territori.get(17), Territori.get(36), Territori.get(26), Territori.get(32));    //Scandinavia->Europa Settentrionale->Europa meridionale->Urali->Afghanistan->Medio Oriente
        //Ucraina

        Territori.get(19).setTerritoriAdiacenti(Territori.get(13), Territori.get(24), Territori.get(15), Territori.get(17));        //Gran Bretagna-> Africa del Nord->Europa Settentrionale->Europa Meridionale
        //Europa Occidentale

        Territori.get(20).setTerritoriAdiacenti(Territori.get(24), Territori.get(21), Territori.get(25));       //Africa del nord->Africa Orientale->Africa del sud
        //Congo

        Territori.get(21).setTerritoriAdiacenti(Territori.get(22), Territori.get(24), Territori.get(20), Territori.get(25), Territori.get(23), Territori.get(32));     //Egitto->Africa del Nord->Congo->Africa del sud->Madagascar->Medio Oriente
        //Africa Orientale

        Territori.get(22).setTerritoriAdiacenti(Territori.get(24), Territori.get(21), Territori.get(17), Territori.get(32));     //Africa del nord->Africa orientale->Europa meridionale->medio oriente
        //Egitto

        Territori.get(23).setTerritoriAdiacenti(Territori.get(21), Territori.get(25));     //Africa orientale->Africa del sud
        //Madagascar

        Territori.get(24).setTerritoriAdiacenti(Territori.get(19), Territori.get(17), Territori.get(22), Territori.get(21), Territori.get(20), Territori.get(10) );        //Europa Occidentale->Europa Meridionale->Egitto->Africa orientale->Congo->Brasile
        //Africa Del Nord

        Territori.get(25).setTerritoriAdiacenti(Territori.get(20), Territori.get(21), Territori.get(23));        //Congo->Africa orientale->Madagascar
        //Africa Del Sud

        Territori.get(26).setTerritoriAdiacenti(Territori.get(18), Territori.get(36), Territori.get(27), Territori.get(32), Territori.get(28));      //Ucraina->Urali->Cina->Medio oriente->India
        //Afghanistan

        Territori.get(27).setTerritoriAdiacenti(Territori.get(33), Territori.get(35), Territori.get(36), Territori.get(26), Territori.get(32),Territori.get(28), Territori.get(34));      //Mongolia->Siberia->Urali->afghanistan->Medio oriente->India->Siam
        //Cina

        Territori.get(28).setTerritoriAdiacenti(Territori.get(32), Territori.get(27), Territori.get(34));      //Medio Oriente->Cina->Siam
        //India

        Territori.get(29).setTerritoriAdiacenti(Territori.get(37), Territori.get(31), Territori.get(33), Territori.get(35));      //Jacuzia->Kamchatka->Mongolia->Siberia
        //Cita

        Territori.get(30).setTerritoriAdiacenti(Territori.get(31), Territori.get(33));        //Kamchatka->Mongolia
        //Giappone

        Territori.get(31).setTerritoriAdiacenti(Territori.get(37), Territori.get(29), Territori.get(33), Territori.get(30), Territori.get(0));      //Jacuzia->Cita->Mongolia->Giappone->Alaska
        //Kamchatka

        Territori.get(32).setTerritoriAdiacenti(Territori.get(22), Territori.get(17), Territori.get(26), Territori.get(18), Territori.get(28));        //Egitto->Europa Meridionale->Afghanistan->Ucraina->India
        //Medio Oriente

        Territori.get(33).setTerritoriAdiacenti(Territori.get(29), Territori.get(31), Territori.get(30), Territori.get(27));        //Cita->kamchatka->Giappone->Cina
        //Mongolia

        Territori.get(34).setTerritoriAdiacenti(Territori.get(27), Territori.get(28), Territori.get(39));     //Cina->india->indonesia
        //Siam

        Territori.get(35).setTerritoriAdiacenti(Territori.get(37), Territori.get(29), Territori.get(36), Territori.get(33), Territori.get(27));       //Jacuzia->Cita->Urali->Mongolia->Cina
        //Siberia

        Territori.get(36).setTerritoriAdiacenti(Territori.get(35), Territori.get(26), Territori.get(27), Territori.get(18));      //Siberia->Afghanistan->Cina->Ucraina
        //Urali

        Territori.get(37).setTerritoriAdiacenti(Territori.get(35), Territori.get(31), Territori.get(29));     //SIberia->kamchatka->Cita
        //Jacuzia

        Territori.get(38).setTerritoriAdiacenti(Territori.get(40), Territori.get(41));      //Nuova guinea->Australia Occidentale
        //Australia Orientale

        Territori.get(39).setTerritoriAdiacenti(Territori.get(34), Territori.get(40), Territori.get(41));      //Siam->Nuova Guinea->Australia Occidentale
        //Indonesia

        Territori.get(40).setTerritoriAdiacenti(Territori.get(39), Territori.get(41), Territori.get(38));      //Indonesia->Australia Occidentale->Australia Orientale
        //Nuova Guinea

        Territori.get(41).setTerritoriAdiacenti(Territori.get(40), Territori.get(39), Territori.get(38));       //Nuova guinea->Indonesia->Australia Orientale
        //Australia Occidentale

    }       //Assegnazione dei territori adiacenti

    /**
     * Tutti i territori devono avere almeno un'armata quindi vengono prelevate le armate dal giocatore e assegnate
     */
    protected void InizializzaTerritori(){
        for (Territorio i : Territori) {
            i.AggiungiArmate(1);
            Giocatore g = i.getProprietario();
            g.RimuoviArmate(1);
        }
    }

    /**
     * Permette di assegnare i territori ai giocatori
     */
    protected void Assegnazione_Territori(){
        int z = 0;

        Collections.shuffle(Territori);

        for(int x = 0; x < InizializzazioneGiocatori.getGiocatori().size(); x++){      //A turno i giocatori ricevono tot territori
            for(int y = 0; y < 42/ InizializzazioneGiocatori.getGiocatori().size(); y++){       //In base al numero di giocatori si ricevono tot territori

                InizializzazioneGiocatori.getGiocatori().get(x).setTerritorio(Territori.get(z));     //Aggiungo il territorio al proprietario
                Territori.get(z).setProprietario(InizializzazioneGiocatori.getGiocatori().get(x));         //Setto il proprietario del territorio
                Territori.get(z).setID(z + 1);

                z++;
            }

        }

        //Questa parte è stata creata per lo scarto dovuto nel caso i giocatori siano 4 o 5
        // Avanzando in entrambi i casi due territori solamente
        int x = 0;
        while(z != 42){
            InizializzazioneGiocatori.getGiocatori().get(x).setTerritorio(Territori.get(z));     //Aggiungo il territorio al proprietario
            Territori.get(z).setProprietario(InizializzazioneGiocatori.getGiocatori().get(x));         //Setto il proprietario del territorio
            x++;
            z++;
        }

    }           //Assegnazione dei territori ai giocatori


    /**
     * Creo i continenti creando un arraylist per ogni continente dove in seguito conterrà tutti i territori di cui
     * ha bisogno per la conquista completa
     */
    protected void CreaContinenti(){
        ArrayList<Territorio> America_del_nord = new ArrayList<>();
        ArrayList<Territorio> America_del_sud = new ArrayList<>();
        ArrayList<Territorio> Europa = new ArrayList<>();
        ArrayList<Territorio> Africa = new ArrayList<>();
        ArrayList<Territorio> Asia = new ArrayList<>();
        ArrayList<Territorio> Oceania = new ArrayList<>();

        for(int x = 0; x < 42; x++){        //Avendo i territori ancora divisi per continenti in questo modo si spartiscono negli arraylist più facilmente


            if(x < 9) {
                America_del_nord.add(Territori.get(x));
            }else if(x < 13){
                America_del_sud.add(Territori.get(x));
            }else if(x < 20){
                Europa.add(Territori.get(x));
            }else if(x < 26){
                Africa.add(Territori.get(x));
            }else if(x < 38){
                Asia.add(Territori.get(x));
            }else {
                Oceania.add(Territori.get(x));
            }
        }


        Continenti.add(new Continente(America_del_nord, "AmericaDelNord"));
        Continenti.add(new Continente(America_del_sud, "AmericaDelSud"));
        Continenti.add(new Continente(Europa, "Europa"));
        Continenti.add(new Continente(Africa, "Africa"));
        Continenti.add(new Continente(Asia, "Asia"));
        Continenti.add(new Continente(Oceania, "Oceania"));


    }





    public static ArrayList<Territorio> getTerritori(){
        return Territori;
    }

    public static void setTerritori(ArrayList<Territorio> territori) {
        Territori = territori;
    }




    public static ArrayList<Continente> getContinenti() {
        return Continenti;
    }

    public static Continente getContinente(int x){
        return Continenti.get(x);
    }


    public static void setContinenti(ArrayList<Continente> continenti) {
        Continenti = continenti;
    }



}
