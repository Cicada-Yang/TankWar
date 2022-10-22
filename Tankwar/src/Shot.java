public class Shot implements Runnable{
    int x;
    int y;
    int direct = 0;
    int speed = 2;
    boolean islive = true;
    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向来改变x,y坐标
            switch (direct){
                case 0://上
                    y-= speed;break;
                case 1://右
                    x+= speed;break;
                case 2://下
                    y+= speed;break;
                case 3://左
                    x-= speed;break;
            }
           // System.out.println("x="+x+",y="+y);
            //碰到边界销毁
            //当子弹碰到敌人坦克时，也应该销毁。
            if(!((x>0&&x<=600)&&(y>0&&y<=600&&islive))){
                islive = false;
                break;
            }
        }
    }

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }
    public void setSpeed(int speed) {
        this.speed = speed;
    }

//    public void setIslive(boolean islive) {
//        this.islive = islive;
//    }
}
