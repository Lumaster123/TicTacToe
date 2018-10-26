package tictactoe.Menus;

import engine.window.Window;
import engine.window.WindowPanel;
import engine.window.WindowResizedListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import tictactoe.Game;
import tictactoe.Player;

public class MultiplayerMenu extends WindowPanel implements WindowResizedListener{
    
    public MultiplayerMenu(Window window, WindowPanel parent) {
        super(window, parent);
    }
    
    public void initialize(){
        initDefault();
        update();
    }
    
    @Override
    protected void loadMenu() {
        
        JTextField ipaddress = new JTextField();
        JTextField port = new JTextField();
        JButton accept = new JButton();
        JButton back = new JButton();
        
        JLabel title = new JLabel("", JLabel.CENTER);
        title.setFont(new Font("Times New Roman", 0, 100));
        title.setText("Verbindungseinstellungen");
        title.setBounds(getWidth() / 2 - 1200 / 2, 30, 1200, 300);
        add(title);
        
        int fontSize = 43;
        
        ipaddress.setFont(new Font("Arial", 0, fontSize - 10));
        port.setFont(new Font("Arial", 0, fontSize - 10));
        accept.setFont(new Font("Arial", 0, fontSize));
        back.setFont(new Font("Arial", 0, fontSize));
        
        ipaddress.setText("IP-Adresse");
        port.setText("Port");
        accept.setText("SUCHEN");
        back.setText("zurück");
        
        ipaddress.setForeground(new Color(190,190,190));
        port.setForeground(new Color(190,190,190));
        
        int x = 450, y = 80;
        
        ipaddress.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 - (y+y/3) + 50 - 80, x, y);
        port.setBounds(getWidth() / 2 - x / 2, getHeight() / 2 - y / 2 + 50 - 80, x, y);
        accept.setBounds(getWidth() / 2 - 450 / 2, getHeight() / 2 - 120 / 2 + 50 + 40, 450, 120);
        back.setBounds(getWidth() / 2 - 450 / 2, getHeight() / 2 - 120 / 2 + (120+120/3) + 50 + 20, 450, 120);

        ipaddress.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(ipaddress.getText().equals("IP-Adresse")){
                    ipaddress.setText("");
                }
                ipaddress.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!isIpAddress(ipaddress.getText())){
                    ipaddress.setForeground(Color.red);
                }else{
                    ipaddress.setForeground(Color.green);
                }
                if(ipaddress.getText().equals("")){
                    ipaddress.setText("IP-Adresse");
                    ipaddress.setForeground(new Color(190,190,190));
                }
            }
        });
        port.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(port.getText().equals("Port")){
                    port.setText("");
                }
                port.setForeground(Color.black);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(!isPort(port.getText())){
                    port.setForeground(Color.red);
                }else{
                    port.setForeground(Color.green);
                }
                if(port.getText().equals("")){
                    port.setText("Port");
                    port.setForeground(new Color(190,190,190));
                }
            }
        });
        accept.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(isIpAddress(ipaddress.getText()) && isPort(port.getText())){
                    Game game = new Game(window, parent, new Player(Player.Player_Handle.PLAYER_HANDLED_BY_PLAYER, true, "PlayerONE"), new Player(Player.Player_Handle.PLAYER_HANDLED_BY_ONLINE_PLAYER, false, "PlayerTWO", ipaddress.getText(), Integer.parseInt(port.getText())));
                }
            }
        });
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.initDefault();
            }
        });
        
        add(ipaddress);
        add(port);
        add(accept);
        add(back);
        
    }

    @Override
    public void windowResized(Window window) {
        clear();
        loadMenu();
        update();
    }

    private boolean isIpAddress(String ipaddress){
        if(ipaddress.equals("localhost"))
            return true;
        String[] part = ipaddress.split("\\.");
        if(part.length != 4)
            return false;
        int[] numPart = new int[part.length];
        for (int i = 0; i < part.length; i++) {
            try{
                numPart[i] = Integer.parseInt(part[i]);
            }catch(NumberFormatException ex){
                return false;
            }
        }
        for (int i : numPart) {
            if(i < 0 || i > 255)
                return false;
        }
        return true;
    }
    
    private boolean isPort(String port){
        int i = 0;
        try{
            i = Integer.parseInt(port);
        }catch(NumberFormatException ex){
            return false;
        }
        if(i > 0 && i < 65535)
            return true;
        return false;
    }
    
}
