package tictactoe;

import tictactoe.Menus.Menu;
import engine.Initializer;
import engine.Time;
import engine.window.Window;

public class TicTacToe {

    public static Initializer init = null;
    
    public static void main(String[] args) {
        init = new Initializer();
        init.initializeConsole();
        init.initializeFileSystem();
        init.initializeWindow(new Window(Window.Window_Size.FULL_SIZE, "TicTacToe", null));
//        init.initializeRenderer();
        
        Menu mainMenu = new Menu(init.getWindow());
        mainMenu.initialize();
        
        init.printInitializationDuration();
        Time.sleep(50);
        mainMenu.update();
    }
    
}
