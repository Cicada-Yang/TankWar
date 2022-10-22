import java.util.Vector;

public class MyTank extends Tank {//我的坦克
    Vector<Shot> shotsMyTank = new Vector<>();
    Shot shot = null;
    public MyTank(int x, int y){
        super(x,y);
    }
    public void shutOtherTank(){
        if (shotsMyTank.size()==5)//使同一时间面板上最多5颗我方子弹
            return;
        switch (getDirect()){
            case 0:shot = new Shot(getX()+20,getY(),0);break;
            case 1:shot = new Shot(getX()+60,getY()+20,1);break;
            case 2:shot = new Shot(getX()+20,getY()+60,2);break;
            case 3:shot = new Shot(getX(),getY()+20,3);break;
        }
        shotsMyTank.add(shot);//把新子弹加入vector
        new Thread(shot).start();
    }
}
