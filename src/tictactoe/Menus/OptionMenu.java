package tictactoe.Menus;

import engine.window.Window;
import engine.window.WindowPanel;
import engine.window.WindowResizedListener;

public class OptionMenu extends WindowPanel implements WindowResizedListener{
    
    public OptionMenu(Window window, WindowPanel parent) {
        super(window, parent);
    }
    
    public void initialize(){
        initDefault();
        update();
    }
    
    @Override
    protected void loadMenu() {
        
        
    }

    @Override
    public void windowResized(Window window) {
        clear();
        loadMenu();
        update();
    }

}
