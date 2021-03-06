package Telas;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import principal.Busca;
import principal.Matriz;
import principal.Posicao;

/**
 *
 * @author bruno
 */
public class View extends JFrame {

    /**
     * Conventions:
     *
     * maze[row][col]
     *
     * Values: 0 = not-visited node 1 = wall
     *
     * borders must be filled with "1" to void ArrayIndexOutOfBounds exception.
     */
    private Matriz maze;
    private Posicao agente;

    public View() {
        setTitle("Labirinto");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public View(Busca busca) {
        this();
        this.maze = busca.getMatriz();
        this.agente = busca.getInicio();
        percorrer(busca);
    }

    public void setMaze(Matriz maze) {
        this.maze = maze;
    }

    public void setAgente(Posicao agente) {
        this.agente = agente;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.translate(50, 50);

        // draw the maze
        for (int row = 0; row < maze.getLinhas(); row++) {
            for (int col = 0; col < maze.getColunas(); col++) {
                Color color;
                switch ((int) maze.retornaValor(row, col)) {
                    case 1:
                        color = Color.WHITE;
                        break;
                    default:
                        color = Color.BLACK;
                }
                g.setColor(color);
                g.fillRect(30 * col, 30 * row, 30, 30);
                g.setColor(Color.BLACK);
                g.drawRect(30 * col, 30 * row, 30, 30);
            }
        }
        //  Pintando o caminho
        g.setColor(Color.RED);
        g.fillOval(30 * this.agente.getY(), 30 * this.agente.getX(), 30, 30);
    }

    private void percorrer(Busca busca) {
        Posicao atual;
        for (int i = 0; i < busca.getCaminho().size(); i++) {
            atual = busca.getCaminho().get(i).getAgente();
            this.setAgente(atual);
            revalidate();
            repaint();
            setVisible(true);

            try {
                Thread.sleep(500L);
            } catch (InterruptedException ex) {
                Logger.getLogger(View.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (i == busca.getCaminho().size() - 1) {
                JOptionPane.showMessageDialog(rootPane, "Objetivo concluido");
                this.setVisible(false);
                return;
            }
        }
    }

    public void iniciar(Busca busca) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                View view = new View(busca);
                view.setVisible(true);
                percorrer(busca);
            }
        });
    }

}
