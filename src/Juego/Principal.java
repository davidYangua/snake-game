package Juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Principal extends JFrame{
    
    private Point p;
    private Imagen img;
    private int dir;
    private ImageIcon imgs = new ImageIcon(getClass().getResource("/Imagenes/Snake.png"));
    private int [] puntosx = {270,270,300};
    int [] puntosy = {215,235,225};
    
    private final int [] puntosx1 = {280,280,310};
    private final int [] puntosx2 = {220,220,250};
    private final int [] puntosx3 = {290,290,320};
    private final int [] puntosy1 = {215,235,225};
    private final int [] puntosy2 = {300,320,310};
    private final int [] puntosy3 = {385,405,395};
    private int m = 1;
    
    public Principal(){
        this.setTitle("Snake");
        this.setSize(800, 600);
        //JFrame.setDefaultLookAndFeelDecorated(false);
	//setUndecorated(true);
        this.setResizable(false);
        this.setMinimumSize(new Dimension(800,600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        img = new Imagen();
        this.add(img);
        
        this.addKeyListener(new Teclas());
        
        this.setIconImage(imgs.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    }
    
    public void actualizar(){
        img.repaint();
    }
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
        }
        Principal p = new Principal();
        p.setVisible(true);
    }
    
    public class Teclas extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
            int f = JOptionPane.showConfirmDialog(null, "¿Desea salir del juego?", "¿Salir?", JOptionPane.YES_NO_OPTION);
            if(f==JOptionPane.YES_OPTION){
                System.exit(0);
            }
        }else if(e.getKeyCode()==KeyEvent.VK_UP){
                m--;
                if(m<1){
                    m=3;
                }
                if(m==1){
                    puntosx=puntosx1;
                    puntosy=puntosy1;
                }else if(m==2) {
                    puntosx=puntosx2;
                    puntosy=puntosy2;
                }else if(m==3){
                    puntosx=puntosx3;
                    puntosy=puntosy3;
                }
        }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
                m++;
                if(m>3){
                    m=1;
                }
                if(m==1){
                    puntosx=puntosx1;
                    puntosy=puntosy1;
                }else if(m==2) {
                    puntosx=puntosx2;
                    puntosy=puntosy2;
                }else if(m==3){
                    puntosx=puntosx3;
                    puntosy=puntosy3;
                }
            
    }else if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(puntosy[0]==215){
                Snake sk = new Snake();
                sk.setVisible(true);
                dispose();
            }else if(puntosy[0]==300){
                Score s = new Score();
                s.setVisible(true);
                dispose();
            }else if(puntosy[0]==385){
                System.exit(0);
            }
        }
        
        actualizar();
}
    
}
    
    public class Imagen extends JPanel {
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        this.setBackground(Color.BLACK);
        
        g.setColor(Color.red);
        g.fillPolygon(puntosx, puntosy, 3);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("cooper black", Font.BOLD, 90));
        g.drawString("Snake", 245, 110);
        
        g.setColor(Color.white);
        g.setFont(new Font("cooper black", Font.BOLD, 40));
        g.drawString("Jugar", 320, 240);
        g.drawString("Ver Puntaje", 260, 320);
        g.drawString("Salir", 330, 405);
    }
}

}
