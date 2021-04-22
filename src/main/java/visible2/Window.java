package visible2;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Window extends JFrame implements KeyListener {
    private static int WIDTH = 720;
    private static int HEIGHT = 720;
    
    private final Color windowBackgroundColor = new Color(43, 43, 43);
    
    private Camera view = new Camera();
    
    public Window() {
        //Set close window on exit button
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Window size
        setSize(WIDTH, HEIGHT);
        //Add keys operation support
        addKeyListener(this);
        //Window name
        setTitle("3Dto2D perspective view");
        //Show gui window
        setVisible(true);
        
        view.setBackground(windowBackgroundColor);
        add(view);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        //Forward move
        if(e.getKeyCode() == KeyEvent.VK_W) {
            view.moveZ(-view.SHIFT);
        }
        //Backword move
        if(e.getKeyCode() == KeyEvent.VK_S) {
            view.moveZ(+view.SHIFT);
        }
        //Left move
        if(e.getKeyCode() == KeyEvent.VK_A) {
            view.moveX(+view.SHIFT);
        }
        //Right move
        if(e.getKeyCode() == KeyEvent.VK_D) {
            view.moveX(-view.SHIFT);
        }
        //Up move
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            view.moveY(-view.SHIFT);
        }
        //Down move
        if(e.getKeyCode() == KeyEvent.VK_Z) {
            view.moveY(+view.SHIFT);
        }
        //Forward rotation
        if(e.getKeyCode() == KeyEvent.VK_UP) {
            view.rotateX(+view.TURN);
        }
        //Backword rotation
        if(e.getKeyCode() == KeyEvent.VK_DOWN) {
            view.rotateX(-view.TURN);
        }
        //Left rotation
        if(e.getKeyCode() == KeyEvent.VK_Q) {
            view.rotateY(+view.TURN);
        }
        //Right rotation
        if(e.getKeyCode() == KeyEvent.VK_E) {
            view.rotateY(-view.TURN);
        }
        //Left rotation down
        if(e.getKeyCode() == KeyEvent.VK_LEFT) {
            view.rotateZ(+view.TURN);
        }
        //Right rotation down
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
            view.rotateZ(-view.TURN);
        }
        //ZOOM +
        if(e.getKeyCode() == KeyEvent.VK_T) {
            view.focalLength(+view.ZOOM);
        }
        //ZOOM -
        if(e.getKeyCode() == KeyEvent.VK_G) {
            view.focalLength(-view.ZOOM);
        }
        
        view.repaint();
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    }
}
