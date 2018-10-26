package tictactoe.Menus;

import engine.window.Window;
import engine.window.WindowPanel;
import engine.window.WindowResizedListener;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Menu extends WindowPanel implements WindowResizedListener{

    private SingleplayerMenu spMenu;
    private MultiplayerMenu mpMenu;
    private OptionMenu oMenu;
    
    public Menu(Window window) {
        super(window);

        spMenu = new SingleplayerMenu(window, this);
        mpMenu = new MultiplayerMenu(window, this);
        oMenu = new OptionMenu(window, this);

//        window.addWindowResizedListener(this);
    }

    public void initialize(){
        initDefault();
        update();
    }
    
    @Override
    protected void loadMenu() {
        JButton menuSingle = new JButton();
        JButton menuMulti = new JButton();
        JButton menuConfig = new JButton();
        
        
        
        JLabel title = new JLabel("", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", 0, 180));
        title.setText("TicTacToe");
        title.setBounds(getWidth() / 2 - 800 / 2, 30, 800, 300);
        add(title);
        
        
        int fontSize = 43;
        
        menuSingle.setFont(new Font("Arial", 0, fontSize));
        menuMulti.setFont(new Font("Arial", 0, fontSize));
        menuConfig.setFont(new Font("Arial", 0, fontSize));
        
        menuSingle.setText("Singleplayer");
        menuMulti.setText("Multiplayer");
        menuConfig.setText("Einstellungen");
        
        int x = 300, y = 120;
        
        menuSingle.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 - (y+y/3) + 50, x, y);
        menuMulti.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 + 50, x, y);
        menuConfig.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 + (y+y/3) + 50, x, y);

        menuSingle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spMenu.initialize();
            }
        });
        menuMulti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mpMenu.initialize();
            }
        });
        menuConfig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                oMenu.initialize();
            }
        });
        
        add(menuSingle);
        add(menuMulti);
        add(menuConfig);
        
    }

    @Override
    public void windowResized(Window window) {
        clear();
        loadMenu();
        update();
    }

}
