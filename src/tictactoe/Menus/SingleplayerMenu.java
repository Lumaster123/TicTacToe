package tictactoe.Menus;

import engine.window.Window;
import engine.window.WindowPanel;
import engine.window.WindowResizedListener;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import tictactoe.Game;
import tictactoe.GameField;
import tictactoe.Player;

public class SingleplayerMenu extends WindowPanel implements WindowResizedListener{

    public SingleplayerMenu(Window window, WindowPanel parent) {
        super(window, parent);
    }
    
    public void initialize(){
        initDefault();
        update();
    }
    
    @Override
    protected void loadMenu() {
        JButton pVp = new JButton();
        JButton pVc = new JButton();
        JButton back = new JButton();
        
        JLabel title = new JLabel("", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", 0, 140));
        title.setText("Singleplayer");
        title.setBounds(getWidth() / 2 - 800 / 2, 30, 800, 300);
        add(title);
        
        int fontSize = 43;
        
        pVp.setFont(new Font("Arial", 2, fontSize));
        pVc.setFont(new Font("Arial", 2, fontSize));
        back.setFont(new Font("Arial", 0, fontSize));
        
        pVp.setText("Player VS Player");
        pVc.setText("Player VS Computer");
        back.setText("zurück");
        
        int x = 450, y = 120;
        
        pVp.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 - (y+y/3) + 50, x, y);
        pVc.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 + 50, x, y);
        back.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 + (y+y/3) + 50, x, y);

        pVp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(window, parent, new Player(Player.Player_Handle.PLAYER_HANDLED_BY_PLAYER, true, "PlayerONE"), new Player(Player.Player_Handle.PLAYER_HANDLED_BY_PLAYER, false, "PlayerTWO"));
            }
        });
        pVc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = new Game(window, parent, new Player(Player.Player_Handle.PLAYER_HANDLED_BY_PLAYER, true, "PlayerONE"), new Player(Player.Player_Handle.PLAYER_HANDLED_BY_COMPUTER, false, "PlayerTWO"));
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.initDefault();
            }
        });
        
        add(pVp);
        add(pVc);
        add(back);
        
    }

    @Override
    public void windowResized(Window window) {
        clear();
        loadMenu();
        update();
    }

    
    
}
