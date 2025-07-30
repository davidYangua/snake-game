package Juego;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class Snake extends JFrame {

    private Point snake;
    private Point comida;
    private ImageIcon imgs = new ImageIcon(getClass().getResource("/Imagenes/Snake.png"));
    private ImageSnake img = new ImageSnake();
    private int direccion = KeyEvent.VK_RIGHT;
    private long frecuencia = 50;
    private List<Point> puntos;
    private boolean gameOver = false;
    private int score = 0;
    private boolean estado = false;
    private JPopupMenu menu = new JPopupMenu();
    private JMenu color = new JMenu();
    private static JMenuItem negro = new JMenuItem();
    private static JMenuItem blanco = new JMenuItem();
    private static JMenuItem verde = new JMenuItem();
    private static JMenuItem gris = new JMenuItem();
    private static Color c = Color.BLACK;

    public Snake() {
        this.setSize(800, 600);
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(imgs.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH));

        evento();
        iniciar();

        Movimiento mv = new Movimiento();
        Thread th = new Thread(mv);
        th.start();

        generarMenu();
        eventoAccion();
    }

    public void eventoAccion() {
        negro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c = Color.BLACK;
            }
        });
        blanco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c = Color.WHITE;
            }
        });
        verde.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c = Color.GREEN;
            }
        });
        gris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c = Color.GRAY;
            }
        });
    }

    public void generarMenu() {
        color.setText("Cambiar fondo");
        menu.add(color);
        negro.setText("Negro");
        color.add(negro);
        color.addSeparator();
        blanco.setText("Blanco");
        color.add(blanco);
        color.addSeparator();
        verde.setText("Verde");
        color.add(verde);
        color.addSeparator();
        gris.setText("Gris");
        color.add(gris);
    }

    public void actualizar() {
        puntos.add(0, new Point(snake.x, snake.y));

        Point cabeza = puntos.get(0);
        for (int i = 1; i < puntos.size(); i++) {
            if (cabeza.equals(puntos.get(i))) {
                gameOver = true;
                return;
            }
        }

        if (Math.abs(cabeza.x - comida.x) < 14 && Math.abs(cabeza.y - comida.y) < 14) {
            score++;
            generarComida();
        } else {
            puntos.remove(puntos.size() - 1); // Solo se acorta si no comió
        }
    }

    public void evento() {
        Teclas t = new Teclas();
        this.addKeyListener(t);
    }

    public void iniciar() {
        puntos = new LinkedList<>(); // CAMBIO: LinkedList más eficiente
        img.addMouseListener(new menuDespleable());
        snake = new Point(378, 280);
        puntos.add(snake);
        generarComida();
        this.add(img);
    }

    public void generarComida() {
        Random r = new Random();
        int a = 0, b = 0;
        do {
            a = r.nextInt(760);
            b = r.nextInt(560);
        } while (!(a % 14 == 0 && b % 14 == 0));
        comida = new Point(a, b);
    }

    public class ImageSnake extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            this.setBackground(c);

            g.setColor(Color.BLACK);
            for (int i = 0; i < 800; i += 14) {
                for (int j = 0; j < 600; j += 14) {
                    g.drawRect(i, j, 14, 14);
                }
            }

            g.setColor(new Color(0, 0, 255));
            for (Point p : puntos) {
                g.fillRect(p.x, p.y, 14, 14);
            }

            g.setColor(new Color(255, 0, 0));
            g.fillOval(comida.x, comida.y, 14, 14);

            if (gameOver) {
                g.setColor(Color.red);
                g.setFont(new Font("cooper black", Font.PLAIN, 20));
                g.drawString("Game Over ", 300, 260);
                g.drawString("Score: " + score + " pts", 300, 310);
                g.drawString("Press Enter to continue", 300, 360);
            }
        }
    }

    public class menuDespleable extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent evt) {
            if (evt.isPopupTrigger() || evt.isMetaDown()) {
                menu.show(evt.getComponent(), evt.getX(), evt.getY());
            }
        }
    }

    public class Teclas extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ESCAPE) {
                estado = true;
                int r = JOptionPane.showConfirmDialog(null, "¿Desea salir al menu principal?", "¿Salir?", JOptionPane.YES_NO_OPTION);
                if (r == JOptionPane.YES_OPTION) {
                    new Principal().setVisible(true);
                    dispose();
                } else {
                    estado = false;
                }
            } else if (key == KeyEvent.VK_RIGHT && direccion != KeyEvent.VK_LEFT) {
                direccion = KeyEvent.VK_RIGHT;
            } else if (key == KeyEvent.VK_LEFT && direccion != KeyEvent.VK_RIGHT) {
                direccion = KeyEvent.VK_LEFT;
            } else if (key == KeyEvent.VK_UP && direccion != KeyEvent.VK_DOWN) {
                direccion = KeyEvent.VK_UP;
            } else if (key == KeyEvent.VK_DOWN && direccion != KeyEvent.VK_UP) {
                direccion = KeyEvent.VK_DOWN;
            } else if (key == KeyEvent.VK_ENTER && gameOver) {
                Score.agregarScore(score);
                gameOver = false;
                new Principal().setVisible(true);
                dispose();
            }
        }
    }

    public class Movimiento extends Thread {
        long tiempo = 0;

        @Override
        public void run() {
            while (true) {
                if ((System.currentTimeMillis() - tiempo) > frecuencia) {
                    if (!(gameOver || estado)) {
                        if (direccion == KeyEvent.VK_RIGHT) {
                            snake.x += 14;
                            if (snake.x > 780) snake.x = 0;
                        } else if (direccion == KeyEvent.VK_LEFT) {
                            snake.x -= 14;
                            if (snake.x < 0) snake.x = 780;
                        } else if (direccion == KeyEvent.VK_UP) {
                            snake.y -= 14;
                            if (snake.y < 0) snake.y = 550;
                        } else if (direccion == KeyEvent.VK_DOWN) {
                            snake.y += 14;
                            if (snake.y > 550) snake.y = 0;
                        }

                        actualizar();
                        img.repaint(); // mover repaint aquí para mejor control
                        tiempo = System.currentTimeMillis();
                    }
                }
                try {
                    Thread.sleep(5); // pequeño descanso para no saturar la CPU
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
