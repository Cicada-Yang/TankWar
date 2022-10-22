public class Node {//用于存储record.txt的敌方坦克数据
    private int x;
    private int y;
    private int direction;

    public Node(int x,int y,int direction){
        this.x=x;
        this.y=y;
        this.direction=direction;
    }
    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getDirection() {
        return direction;
    }
}
