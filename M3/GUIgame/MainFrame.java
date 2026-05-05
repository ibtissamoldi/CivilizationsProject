package M3.GUIgame;


import javax.swing.*;

public class MainFrame extends JFrame{
    PanelPrincipal panel_principal;
    public static void main(String[] args) {
        new MainFrame();
    }

    public MainFrame() {
        setTitle("Civilizations");
        setSize(1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);





        setVisible(true);
    }
    

}


class PanelPrincipal extends JPanel {


}


