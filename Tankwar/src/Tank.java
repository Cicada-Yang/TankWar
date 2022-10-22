public class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direct;
    private int speed;
    boolean islive = true;

    public Tank(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void Moveup(){
        y-=speed;
    }
    public void Movedown(){
        y+=speed;
    }
    public void Moveleft(){
        x-=speed;
    }
    public void Moveright(){
        x+=speed;
    }

    public int getDirect() {return direct;}

    public void setDirect(int direct) {this.direct = direct;}

    public int getX() {
        return x;
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
}
