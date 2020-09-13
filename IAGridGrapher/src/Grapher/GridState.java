/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grapher;

import Entities.Person;
import Input.KeyInput;
import Input.MouseInput;
import MazeCore.Laberinto;
import Rendering.Sprite;
import Rendering.SpriteSheet;
import Rendering.Texture;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import States.State;
import States.StateManager;
import Utils.Fonts;
import iagridgrapher.Game;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;

public class GridState implements State {

    Laberinto laberinto = new Laberinto();
    Queue<Point> instrucciones;
    Point startP;
    Point endP;
    boolean showNumbers = true;

    public enum Level {
        FLOOR(0),
        WALL(-1),
        START(-2),
        END(-3);

        private final int value;

        Level(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }

    Level level = Level.FLOOR;

    private final String[] options = {"Start", "Instructions", "Exit"};
    public int current = 0;
    public int currentOption = 0;
    int res = 0;
    int stp = 0;

    Person c;

    LinkedList<Sprite> pisos = new LinkedList<>();
    private SpriteSheet sheet2;
    private SpriteSheet sheet3;
    private SpriteSheet sheet4;
    private SpriteSheet sheet5;
    private Sprite sprite2;
    private Sprite sprite3;
    private Sprite sprite4;
    private Sprite sprite5;

    private final int cols = 10;
    private final int rows = 10;
    private int[][] maze = new int[rows][cols];

    @Override
    public void init() {

        for (int i = 0; i < 8; ++i) {
            String s = "floor_00" + Integer.toString(i + 1);
            SpriteSheet sheet = new SpriteSheet(new Texture(s), 64, 64);
            Sprite sprite = new Sprite(sheet, 1, 1);
            pisos.add(sprite);
        }

        c = new Person("char", 64, 64, -90, -90);

        sheet2 = new SpriteSheet(new Texture("texture-export"), 64, 64);
        sheet3 = new SpriteSheet(new Texture("spike"), 64, 64);
        sheet4 = new SpriteSheet(new Texture("texture-start"), 64, 64);
        sheet5 = new SpriteSheet(new Texture("texture-end"), 64, 64);

        sprite2 = new Sprite(sheet2, 1, 1);
        sprite3 = new Sprite(sheet3, 1, 1);
        sprite4 = new Sprite(sheet4, 1, 1);
        sprite5 = new Sprite(sheet5, 1, 1);

    }

    @Override
    public void enter() {

    }

    public int mouse_xpos = 0;
    public int mouse_ypos = 0;

    boolean start = false;
    double t = 0;

    double lerp(double a, double b, double f) {
        return a + f * (b - a);
    }

    @Override
    public void tick(StateManager manager, double delta) {

        if (start && instrucciones.size() > 0) {
            Point pp = instrucciones.element();
            c.setX(lerp(c.getX(), pp.y * 64, 0.25));
            c.setY(lerp(c.getY(), pp.x * 64, 0.25));
            t += delta;
            if (t >= 8 && instrucciones.size() > 0) {
                Point p = instrucciones.remove();
                c.setX(lerp(c.getX(), p.y * 64, 0.25));
                c.setY(lerp(c.getY(), p.x * 64, 0.25));
                //c.setX(p.y * 64);
                //c.setY(p.x * 64);
                stp++;
                if (stp == laberinto.steps.get(res)) {
                    //TOMAR SCREENSHOT
                    res++;
                    stp = 0;
                }
                t = 0;
            }
            if (instrucciones.size() == 0) {
                start = false;
                t = 0;
                res = 0;
            }

        }

        if (KeyInput.wasPressed(KeyEvent.VK_RIGHT) && current + 1 < 4) {
            current++;
        }

        if (KeyInput.wasPressed(KeyEvent.VK_LEFT) && current - 1 >= 0) {
            current--;
        }

        if (KeyInput.wasPressed(KeyEvent.VK_R)) {
            maze = new int[rows][cols];
            start = false;
        }

        if (KeyInput.wasPressed(KeyEvent.VK_R)) {
            maze = new int[rows][cols];
            startP = null;
            endP = null;
        }

        if (KeyInput.wasPressed(KeyEvent.VK_S)) {
            showNumbers=!showNumbers;
        }
        
        if (KeyInput.wasPressed(KeyEvent.VK_ENTER)) {

            if (startP != null && endP != null) {

                // INICIAR LA ANIMACION
                instrucciones = new LinkedList<>();
                instrucciones = laberinto.Instrucciones(maze, startP.x, startP.y, endP.x, endP.y);
                c.setX(-90);
                c.setY(-90);
                start = true;
                System.out.println(laberinto.results.size());

                if (instrucciones.size() == 0) {
                    start = false;
                    JOptionPane.showMessageDialog(null, "No hay soluciones");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Falta Inicio y/o Final");
            }

        }

        switch (current) {
            case 0:
                level = Level.FLOOR;
                break;
            case 1:
                level = Level.WALL;
                break;
            case 2:
                level = Level.START;
                break;
            case 3:
                level = Level.END;
                break;
        }

        if (MouseInput.click && mouse_xpos >= 0 && mouse_xpos < cols && mouse_ypos >= 0 && mouse_ypos < rows) {

            if (Level.START.getValue() == level.getValue()) {

                startP = new Point(mouse_ypos, mouse_xpos);

            } else if (Level.END.getValue() == level.getValue()) {

                endP = new Point(mouse_ypos, mouse_xpos);

            } else {

                if (startP != null && mouse_ypos == startP.x && mouse_xpos == startP.y) {
                    startP = null;
                }

                if (endP != null && mouse_ypos == endP.x && mouse_xpos == endP.y) {
                    endP = null;
                }

                maze[mouse_ypos][mouse_xpos] = level.getValue();

            }

            MouseInput.click = false;
        }
        mouse_xpos = MouseInput.xpos / 64;
        mouse_ypos = MouseInput.ypos / 64;

        //System.out.println(mouse_xpos+":"+mouse_ypos);
    }

    boolean show = false;

    @Override
    public void exit() {

    }

    @Override
    public String getName() {
        return "menu";
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);

        for (int j = 0; j < rows; ++j) {
            for (int i = 0; i < cols; ++i) {

                if (maze[j][i] == Level.FLOOR.getValue()) {
                    pisos.get(0).render(g, (double) 64 * i, (double) 64 * j, 0);
                } else {
                    if(startP==null){
                        sprite3.render(g, (double) 64 * i, (double) 64 * j, 0);
                    }else{
                       
                        if(startP.x==j && startP.y==i){
                            //System.out.println(""+startP.x+","+startP.y);
                            //System.out.println(""+i+","+j);
                            pisos.get(0).render(g, (double) 64 * i, (double) 64 * j, 0);
                        }else{
                            sprite3.render(g, (double) 64 * i, (double) 64 * j, 0);
                        }
                       
                        
                    }
                    
                }

                if (i == mouse_xpos && j == mouse_ypos) {
                    sprite2.render(g, (double) 64 * i, (double) 64 * j, 0);
                }
                int offset = 8;
                if (!start) {
                    int v = maze[j][i];
                    if (showNumbers) {
                        Fonts.drawString(g, new Font("Courier New", Font.BOLD, 32), Color.white, "" + v, i * 64 + 32 - offset, j * 64 + 32 + offset);
                    }
                } else {
                    int v = laberinto.results.get(res)[j][i];
                    float s = (float) v / laberinto.steps.get(res);

                    if (v > 0) {
                        g.setColor(Color.getHSBColor(s * 0.33f, 1, 1));
                        g.fillRect(i * 64, j * 64, 64, 64);
                    }

                    if (showNumbers) {
                        Fonts.drawString(g, new Font("Courier New", Font.BOLD, 32), Color.white, "" + v, i * 64 + 32 - offset, j * 64 + 32 + offset);
                    }
                }
            }

        }

        if (startP != null) {
            sprite4.render(g, (double) startP.y * 64, (double) startP.x * 64, 0);
        }

        if (endP != null) {
            sprite5.render(g, (double) endP.y * 64, (double) endP.x * 64, 0);
        }

        String arrow1 = "", arrow2 = "";
        if (current == 0) {
            arrow1 = "";
            arrow2 = "►";
        }
        if (current == 1) {
            arrow1 = "◄";
            arrow2 = "►";
        }
        if (current == 2) {
            arrow1 = "◄";
            arrow2 = "►";
        }
        if (current == 3) {
            arrow1 = "◄";
            arrow2 = "";
        }

        if (c != null) {
            c.render(g);
        }

        Fonts.drawString(g, new Font("Courier New", Font.BOLD, 16), Color.GREEN, arrow1 + level.name() + arrow2, 64 * 10 + 32);

    }

}
