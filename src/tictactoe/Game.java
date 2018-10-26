package tictactoe;

import engine.ThreadHandler;
import engine.Time;
import engine.window.Window;
import engine.window.WindowPanel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import tictactoe.Menus.FieldState;

public class Game implements MouseListener, Runnable{
    
    private GameField gameField;
    private Player player1, player2;
    
    private FieldState[][] field;
    private int turn;
    
    private Window window;
    private WindowPanel menu;
    
    public Game(Window window, WindowPanel menu, Player player1, Player player2){
        this.gameField = new GameField(window, this);
        this.player1 = player1;
        this.player2 = player2;
        
        this.window = window;
        this.menu = menu;
        
        turn = 0;
        field = new FieldState[3][3];
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                field[x][y] = FieldState.NOT_USED;
            }
        }
        
        gameField.initialize();
        
        ThreadHandler.invoke("gameLogic-Thread", this);
        
    }

    
    private int reciveMouseInput;
    private Point mouseInput;
    
    @Override
    public void run() {
        // disable mouseInput
        reciveMouseInput = 0;
        
        // generate random boolean for wich player starts
        Random rd = new Random();
        boolean playerTurn = rd.nextBoolean();
        // player who is on turn
        Player playerOnTurn;
        // say wich player starts
        if(playerTurn){
            playerOnTurn = player1;
            createInfoDialog(new Dimension(450, 150), "GameInfo", "Der Zufall hat entschieden. Der Spieler "+player1.playerName+" darf beginnen.", true, 7000);
        }else{
            playerOnTurn = player2;
            createInfoDialog(new Dimension(450, 150), "GameInfo", "Der Zufall hat entschieden. Der Spieler "+player2.playerName+" darf beginnen.", true, 7000);
        }
        
        // variable for ending the game (or not)
        int gameState = 0;
        
        // start main-gameloop
        while(true){
            // say wich play is on turn
            createInfoDialog(new Dimension(450, 150), "GameInfo", "Der Spieler "+playerOnTurn.playerName+" ist am Zug!", true, 7000);
            
            // input loop
            while(true){
                if(playerOnTurn.PLAYER_HANDLE == Player.Player_Handle.PLAYER_HANDLED_BY_PLAYER){
                    // enable and wait at mouseInput
                    reciveMouseInput = 1;
                    Point target = null;
                    while(true){
                        if(reciveMouseInput == 2){
                            target = mouseInput;
                            mouseInput = null;
                            reciveMouseInput = 0;
                            break;
                        }
                        Time.sleep(5);
                    }

                    // check if mouseInput is valid
                    if(isTurnValid(target)){
                        // set turn into field
                        if(playerOnTurn == player1){
                            field[target.x][target.y] = FieldState.USED_PLAYER1;
                        }else if(playerOnTurn == player2){
                            field[target.x][target.y] = FieldState.USED_PLAYER2;
                        }
                        gameField.update(field, player1, player2);
                        break;
                    }else{
                        // wait for another mouseInput
                    }
                }else if(playerOnTurn.PLAYER_HANDLE == Player.Player_Handle.PLAYER_HANDLED_BY_COMPUTER){
                    playerOnTurn.handleInput(field);
                    gameField.update(field, player1, player2);
                    break;
                }else if(playerOnTurn.PLAYER_HANDLE == Player.Player_Handle.PLAYER_HANDLED_BY_ONLINE_PLAYER){
                    playerOnTurn.handleInput(field);
                    gameField.update(field, player1, player2);
                    break;
                }

                
            }
            if(playerOnTurn == player1){
                player2.handleOutput(field);
            }else{
                player1.handleOutput(field);
            }
            
            turn++;
            
            // check if game is over
            gameState = 0;
            if(playerOnTurn == player1){
                gameState = isGameOver(FieldState.USED_PLAYER1);
            }else if(playerOnTurn == player2){
                gameState = isGameOver(FieldState.USED_PLAYER2);
            }
            if(gameState == 1){
                // game isn't over
            }else if(gameState == 2){
                // playerOnTurn has won
                break;
            }else if(gameState == 3){
                // draw
                break;
            }
            
            if(playerOnTurn == player1){
                playerOnTurn = player2;
            }else if(playerOnTurn == player2){
                playerOnTurn = player1;
            }
        }
        
        // say the winner
        if(gameState == 3){
            createInfoDialog(new Dimension(450, 150), "GameOver", "Draw!", true, 1000);
            menu.initDefault();
        }else if(gameState == 2){
            createInfoDialog(new Dimension(450, 150), "GameOver", "Der Spieler "+playerOnTurn.playerName+" hat gewonnen!", true, 1000);
            menu.initDefault();
        }
        
    }
    
    private int isGameOver(FieldState player){
        if(player == FieldState.NOT_USED){
            System.err.println("NOT_USED can't be used to check if the game is over!");
            System.exit(1);
        }
        
        for(int x = 0; x < 3; x++){
            int y = 0;
            if(field[x][y] == player && field[x][y+1] == player && field[x][y+2] == player){
                System.out.println(player);
                return 2;
            }
        }
        for(int y = 0; y < 3; y++){
            int x = 0;
            if(field[x][y] == player && field[x+1][y] == player && field[x+2][y] == player){
                return 2;
            }
        }
        if(field[0][0] == player && field[1][1] == player && field[2][2] == player){
            return 2;
        }
        if(field[2][0] == player && field[1][1] == player && field[0][2] == player){
            return 2;
        }
        
        if(turn == 9){
            return 3;
        }
        return 1;
    }
    
    private boolean isTurnValid(Point target){
        if(field[target.x][target.y] == FieldState.NOT_USED){
            return true;
        }
        return false;
    }
    
    private boolean infoDialogFinished;
    private void createInfoDialog(Dimension size, String title, String msg, boolean ok_button, int ms_opentime){
        JFrame dlg = new JFrame();
        dlg.setTitle(title);
        dlg.setSize(size);
        dlg.setLocationRelativeTo(null);
        dlg.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        dlg.setUndecorated(true);
        dlg.setLayout(null);
        dlg.setResizable(false);
        dlg.setLayout(null);
        
        JLabel border = new JLabel();
        border.setBounds(0, 0, size.width, size.height);
        border.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        dlg.add(border);
        
        JLabel label = new JLabel(msg, JLabel.CENTER);
        label.setFont(new Font("Arial", 0, 12));
        if(ok_button)
            label.setBounds(0, 0, size.width, size.height-20);
        else
            label.setBounds(0, 0, size.width, size.height);
        dlg.add(label);
        
        infoDialogFinished = false;
        if(ok_button){
            JButton button = new JButton("OK");
            button.setBounds(size.width-100-1, size.height-20-1, 100, 20);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dlg.setVisible(false);
                    dlg.dispose();
                    infoDialogFinished = true;
                }
            });
            dlg.add(button);
        }else{
            ThreadHandler.invokeLater("closeDialog-Thread", ms_opentime, new Runnable() {
                @Override
                public void run() {
                    dlg.setVisible(false);
                    dlg.dispose();
                    infoDialogFinished = true;
                }
            });
        }
        
        dlg.setVisible(true);
        
        while(infoDialogFinished == false){Time.sleep(5);}
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() instanceof JLabel){
            JLabel source = (JLabel)e.getSource();
            if(source.getName().contains(",")){
                if(reciveMouseInput == 1){
                        int x = Integer.parseInt(source.getName().charAt(0)+"");
                        int y = Integer.parseInt(source.getName().charAt(source.getName().length()-1)+"");
                        mouseInput = new Point(x, y);
                        reciveMouseInput = 2;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

}
