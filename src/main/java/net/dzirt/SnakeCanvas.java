package net.dzirt;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Dzirt on 01.06.2016.
 */


public class SnakeCanvas extends Canvas implements Runnable, KeyListener{
    private final int BOX_HEIGHT = 20;
    private final int BOX_WIDTH = 20;
    private final int GRID_HEIGHT = 20;
    private final int GRID_WIDTH = 30;

    private LinkedList<Point> snake;
    private Point fruit;
    private int direction = Direction.NO_DIRECTION;

    private Thread runThread;
    private Graphics globalGraphics;

    public void init(){

    }

    public Dimension getSize(){
        return new Dimension(BOX_WIDTH * GRID_WIDTH + 1, BOX_HEIGHT * GRID_HEIGHT + 1);
    }

    public void paint(Graphics g){
        this.setPreferredSize(new Dimension(640, 480));
        snake = new LinkedList<Point>();

        generateDefaultSnake();
        placeFruit();
//        fruit = new Point(10,10);

        g.fillRect(0, 0, 10, 10);
        globalGraphics = g.create();
        this.addKeyListener(this);
        if(runThread == null){
            runThread = new Thread(this);
            runThread.start();
        }
    }

    public void generateDefaultSnake(){
        snake.clear();

        snake.add(new Point(5,6));
        snake.add(new Point(4,6));
        snake.add(new Point(3,6));
        direction = Direction.NO_DIRECTION;
    }

    public void Draw(Graphics g){
        g.clearRect(0, 0, BOX_WIDTH * GRID_WIDTH, BOX_HEIGHT * GRID_HEIGHT);
        DrawGrid(g);
        DrawSnake(g);
        DrawFruit(g);
    }

    public void Move(){
        Point head = snake.peekFirst();
        Point newPoint = head;
        switch (direction){
            case Direction.NORTH:
                newPoint = new Point(head.x, head.y - 1);
                break;
            case Direction.SOUTH:
                newPoint = new Point(head.x, head.y + 1);
                break;
            case Direction.WEST:
                newPoint = new Point(head.x - 1, head.y);
                break;
            case Direction.EAST:
                newPoint = new Point(head.x + 1, head.y);
                break;
        }


        snake.remove(snake.peekLast());

        if (newPoint.equals(fruit)){

            Point addPoint = (Point) newPoint.clone();
            switch (direction){
                case Direction.NORTH:
                    newPoint = new Point(head.x, head.y - 1);
                    break;
                case Direction.SOUTH:
                    newPoint = new Point(head.x, head.y + 1);
                    break;
                case Direction.WEST:
                    newPoint = new Point(head.x - 1, head.y);
                    break;
                case Direction.EAST:
                    newPoint = new Point(head.x + 1, head.y);
                    break;
            }
            snake.push(addPoint);
            placeFruit();

        } else if (newPoint.x < 0 || newPoint.x >= GRID_WIDTH){
            //game over, reset
            generateDefaultSnake();
            return;
        } else if (newPoint.y < 0 || newPoint.y >= GRID_HEIGHT){
            //game over, reset
            generateDefaultSnake();
            return;
        } else if (snake.contains(newPoint)){
            //game over, reset

            generateDefaultSnake();
            return;
        }

        //if we reach this point in code, we're still good
        snake.push(newPoint);

    }

    public void DrawGrid(Graphics g){
        g.drawRect(0,0, GRID_WIDTH * BOX_WIDTH, GRID_HEIGHT * BOX_HEIGHT);
        for(int x = BOX_WIDTH; x < GRID_WIDTH * BOX_WIDTH; x += BOX_HEIGHT){
            g.drawLine(x, 0, x, BOX_HEIGHT * GRID_HEIGHT);
        }
        for(int y = BOX_HEIGHT; y < GRID_HEIGHT * BOX_HEIGHT;y += BOX_WIDTH){
            g.drawLine(0, y, BOX_WIDTH * GRID_WIDTH, y);
        }
    }

    public void DrawSnake(Graphics g){
        g.setColor(Color.GREEN);
        for(Point p : snake){
            g.fillRect(p.x * BOX_WIDTH + 1, p.y * BOX_HEIGHT + 1, BOX_WIDTH - 1, BOX_HEIGHT - 1);
        }
        g.setColor(Color.BLACK);
    }

    public void placeFruit(){
        Random rand = new Random();
        int randomX = rand.nextInt(GRID_WIDTH);
        int randomY = rand.nextInt(GRID_HEIGHT);
        Point randomPoint = new Point(randomX, randomY);
        while (snake.contains((randomPoint))){
            randomX = rand.nextInt(GRID_WIDTH);
            randomY = rand.nextInt(GRID_HEIGHT);
            randomPoint = new Point(randomX, randomY);
        }
        fruit = randomPoint;
    }

    public void DrawFruit(Graphics g){
        g.setColor(Color.RED);
        g.fillOval(fruit.x * BOX_WIDTH, fruit.y  * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
        g.setColor(Color.BLACK);
    }

    @Override
    public void run() {
        while(true){
            Move();
            Draw(globalGraphics);
            try{
                Thread.currentThread();
                Thread.sleep(100);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                if (direction != Direction.SOUTH) {
                    direction = Direction.NORTH;
                }
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.NORTH) {
                    direction = Direction.SOUTH;
                }
                break;
            case KeyEvent.VK_LEFT:
                if (direction != Direction.EAST) {
                    direction = Direction.WEST;
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.WEST) {
                    direction = Direction.EAST;
                }
                break;
            //case KeyEvent.VK_ESCAPE:

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
