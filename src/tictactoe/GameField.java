package tictactoe;

import engine.filesystem.ImageHandler;
import engine.window.Window;
import engine.window.WindowPanel;
import engine.window.WindowResizedListener;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import tictactoe.Menus.FieldState;

public class GameField extends WindowPanel implements WindowResizedListener{

    private JLabel[][] labelField;
    
    private FieldState[][] field;
    private Player player1, player2;
    
    private MouseListener listener;
    
    public GameField(Window window, MouseListener listener) {
        super(window);
        this.listener = listener;
        
//        window.addWindowResizedListener(this);

        labelField = new JLabel[3][3];
    }

    public void initialize(){
        initDefault();
        update();
    }
    
    @Override
    protected void loadMenu() {
        
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                int width = 225, height = 225;
                labelField[x][y] = new JLabel("", JLabel.CENTER);
                labelField[x][y].setBounds(getWidth() / 2 - width / 2 - width - 25 + (x * (width + 25)), getHeight() / 2 - height / 2 - 100 - height - 25 + (y * (height + 25)) , width, height);
                if(field != null && player1 != null && player2 != null){
                    if(field[x][y] == FieldState.USED_PLAYER1)
                        labelField[x][y].setIcon(new ImageIcon(ImageHandler.scaleImage(player1.getImg(), width, height)));
                    else if(field[x][y] == FieldState.USED_PLAYER2)
                        labelField[x][y].setIcon(new ImageIcon(ImageHandler.scaleImage(player2.getImg(), width, height)));
                }
                labelField[x][y].setBorder(BorderFactory.createLoweredBevelBorder());
                labelField[x][y].setName(x+","+y);
                labelField[x][y].addMouseListener(listener);
                
                add(labelField[x][y]);
            }
        }
        
        
        
    }

    @Override
    public void windowResized(Window window) {
        clear();
        loadMenu();
        update();
    }


    public void update(FieldState[][] field, Player player1, Player player2){
        this.field = field;
        this.player1 = player1;
        this.player2 = player2;
        
        windowResized(window);
    }
    
}
