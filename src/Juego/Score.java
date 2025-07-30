package Juego;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Score extends JFrame{
    
    private static List<Integer>score=new ArrayList<>();
    private Paint pt;
    private ImageIcon imgs = new ImageIcon(getClass().getResource("/Imagenes/Snake.png"));
    private int d = 90;
    private int [] dirY = {130, 170, 210, 250, 290, 330, 370, 410, 450, 490, 530, 570};
    
    public Score(){
        this.setTitle("Snake");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setMinimumSize(new Dimension(800,600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.addKeyListener(new Teclas());
        
        pt = new Paint();
        this.add(pt);
        
        this.setIconImage(imgs.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));
    }
    
    public static void agregarScore(int a){
        score.add(a);
        if(score.size()>1){
            score.sort((m,n)->n-m);
        }
    }
    
    public class Teclas extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent evt){
            if(evt.getKeyCode()==KeyEvent.VK_ESCAPE){
                Principal p = new Principal();
                p.setVisible(true);
                dispose();
            }else if(evt.getKeyCode()==KeyEvent.VK_L){
                System.out.println(score.size());
            }
        }
    }
    
    public class Paint extends JPanel{
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            this.setBackground(Color.BLACK);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("cooper black", Font.BOLD, 40));
            g.drawString("Top Score", 290, 80);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("cooper black", Font.BOLD, 30));
            if(score.size()>0){
                for(int i=0; i<score.size(); i++){
                    if(i<=12){
                        g.drawString((i+1)+") "+score.get(i), 100, dirY[i]);
                    }else{
                        g.drawString((i+1)+") "+score.get(i), 500, dirY[i]);
                    }
                }
            }else {
                //g.setColor(Color.WHITE);
                g.setFont(new Font("arial", Font.BOLD, 30));
                g.drawString("No hay puntajes acumulados", 100, 170);
            }
        }
    }
}
