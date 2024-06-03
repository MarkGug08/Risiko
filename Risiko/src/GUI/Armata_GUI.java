package GUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


public class Armata_GUI extends JButton{
    String nome;
    int coordinataX;
    int coordinataY;

    ImageIcon armata2 = new ImageIcon("Risiko/src/Risorse/Immagini/ArmateNere.png");
    public Armata_GUI(String nome, int coordinataX, int coordinataY, int armate){

        this.nome = nome;
        this.coordinataX = coordinataX;
        this.coordinataY = coordinataY;
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setFocusPainted(false);
        setName(nome);
        setBorderPainted(false);
        setPressedIcon(armata2);
        setText(String.valueOf(armate));
        setContentAreaFilled(false);
        setOpaque(false);
        setBounds(coordinataX, coordinataY,50, 30);


    }

    public String getNome() {
        return nome;
    }
}
