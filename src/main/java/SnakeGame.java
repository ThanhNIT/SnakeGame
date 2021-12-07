import ui.Event;
import ui.SnakeUserInterface;
import ui.UserInterfaceFactory;

import java.util.Random;


class SnakeGame
{
    SnakeUserInterface ui;
    Random rand = new Random();
    Point food = new Point(5,5);
    int i;
    private Point[] snake = new Point[250];
    private int snakeLength =2;
    Point direction = new Point(1,0);
    int screen = 20;
    String currentMove = "R";
    boolean running =true;
    SnakeGame() {
        snake[0] = new Point(1,0);
        snake[1] = new Point(0,0);
        ui = UserInterfaceFactory.getSnakeUI(screen, screen);
        ui.setFramesPerSecond(2);
    }

    void handleAlarm()
    {

        if(currentMove.equals("R")) {
            if(direction.getX()>=screen-1)
                direction.setX(0);
            else
                direction.setX(direction.getX() + 1);
        }
        if(currentMove.equals("L")) {
            if(direction.getX()<=0)
                direction.setX(screen-1);
            else
                direction.setX(direction.getX() -1);
        }
        if(currentMove.equals("U")) {
            if(direction.getY()<=0)
                direction.setY(screen-1);
            else
                direction.setY(direction.getY() -1);
        }
        if(currentMove.equals("D")){
            if(direction.getY()>=screen-1)
                direction.setY(0);
            else
                direction.setY(direction.getY() +1);
        }



        for (int i = 0;i < snakeLength;i++){

            if (i == 0){
                snake[0].setX0(snake[0].getX());
                snake[0].setY0(snake[0].getY());

                snake[0].setX(direction.getX());
                snake[0].setY(direction.getY());
            }
            else {
                snake[i].setX0(snake[i].getX());
                snake[i].setY0(snake[i].getY());
                snake[i].setX(snake[i-1].getX0());
                snake[i].setY(snake[i-1].getY0());
            }
        }

        for (int i = 1;i < snakeLength;i++){

            if(snake[0].compare(snake[i]))
                running=false;
        }

        ui.clear();
        ui.place(food.getX(),food.getY(),ui.FOOD);

        for (int i = 0;i < snakeLength;i++){
            ui.place(snake[i].getX(), snake[i].getY(), ui.SNAKE);
        }

        if(snake[0].compare(food)){
            food.setX(rand.nextInt(screen));
            food.setY(rand.nextInt(screen));

            snake[snakeLength] = new Point(snake[snakeLength-1].getX0(),snake[snakeLength-1].getY0());
            snakeLength++;
            for (int i = 0;i < snakeLength;i++){
                ui.place(snake[i].getX(), snake[i].getY(), ui.SNAKE);
            }
        }


        ui.showChanges();

    }


    void handleArrow(String arrowType)
    {
        if (arrowType.equals("R")) {
            if (currentMove.equals("U") || currentMove.equals("D"))
                currentMove = "R";
        }
        else if (arrowType.equals("L")) {
            if (currentMove.equals("U") || currentMove.equals("D"))
                currentMove = "L";
        }
        else if (arrowType.equals("U")) {
            if (currentMove.equals("R") || currentMove.equals("L"))
                currentMove = "U";
        }
        else if (arrowType.equals("D")) {
            if (currentMove.equals("R") || currentMove.equals("L"))
                currentMove = "D";
        }
    }

    void processEvent(Event event)
    {
        if (event.name.equals("alarm")) {
            handleAlarm();
        }
        else if (event.name.equals("arrow")) {
            handleArrow(event.data);
        }
    }

    void start() {
        while (running) {
            Event event = ui.getEvent();
            processEvent(event);
        }

    }


    public static void main(String[] args)
    {
        SnakeGame e = new SnakeGame();
        e.start();
    }

}

class Point {

    private int x;
    private int y;
    private int x0;
    private int y0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
    }

    public Point(int x, int y, int x0, int y0) {
        this.x = x;
        this.y = y;
        this.x0 = x0;
        this.y0 = y0;
    }

    public int getX() {
        return x;
    }

    public boolean compare(Point point){
        if(this.x==point.getX() && this.y==point.getY())
            return true;
        return false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX0() {
        return x0;
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public int getY0() {
        return y0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }
}
