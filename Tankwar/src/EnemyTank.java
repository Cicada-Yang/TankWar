import java.util.Vector;
@SuppressWarnings({"all"})
public class EnemyTank extends Tank implements Runnable {
    Vector<Shot> shotsenemy = new Vector<>();
    public EnemyTank(int x, int y) {
        super(x, y);
    }
    //用于存放所有的敌方坦克，用于与当前的这个敌方坦克的位置进行比较
    Vector<EnemyTank> enemyTanks = new Vector<>();//但是这里没有这个，所有要从MyPanel引过来
    //也就是用下面这个方法调
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }
    //编写方法，查看当前的坦克位置是否与其它坦克重叠
    public boolean IsTouchEnemyTank(){
        switch (this.getDirect()){
            case 0:
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    //不和自己比较
                    if(enemyTank!=this){
                        if(enemyTank.getDirect()==0||enemyTank.getDirect()==2){//上下
                            //如果敌人坦克是上/下 X范围[enemyTank.getX(),enemyTank.getX()+40]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克左上角的坐标[this.getX(),this.getY()]
                            if(this.getX()>=enemyTank.getX()//左上角
                                    &&this.getX()<=enemyTank.getX()+40
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+60){
                                return true;
                            } //当前坦克右上角的坐标[this.getX()+40,this.getY()]
                            if(this.getX()+40>=enemyTank.getX()//右上角
                                    &&this.getX()+40<=enemyTank.getX()+40
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        else if(enemyTank.getDirect()==1||enemyTank.getDirect()==3){//左右
                            //如果敌人坦克是左/右 X范围[enemyTank.getX(),enemyTank.getX()+60]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+40]
                            if(this.getX()>=enemyTank.getX()//左上角
                                    &&this.getX()<=enemyTank.getX()+60
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                            if(this.getX()+40>=enemyTank.getX()//右上角
                                    &&this.getX()+40<=enemyTank.getX()+60
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+40){
                                return true;
                            }

                        }
                    }
                }
                break;
            case 1:
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    //不和自己比较
                    if(enemyTank!=this){
                        if(enemyTank.getDirect()==0||enemyTank.getDirect()==2){//上下
                            //如果敌人坦克是上/下 X范围[enemyTank.getX(),enemyTank.getX()+40]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克右上角的坐标[this.getX()+60,this.getY()]
                            if(this.getX()+60>=enemyTank.getX()//右上角
                                    &&this.getX()+60<=enemyTank.getX()+40
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+60){
                                return true;
                            }//当前坦克右下角的坐标[this.getX()+60,this.getY()+40]
                            if(this.getX()+60>=enemyTank.getX()//右下角
                                    &&this.getX()+60<=enemyTank.getX()+40
                                    &&this.getY()+40>=enemyTank.getY()
                                    &&this.getY()+40<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        else if(enemyTank.getDirect()==1||enemyTank.getDirect()==3){//左右
                            //如果敌人坦克是左/右 X范围[enemyTank.getX(),enemyTank.getX()+60]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+40]
                            if(this.getX()+60>=enemyTank.getX()//右上角
                                    &&this.getX()+60<=enemyTank.getX()+60
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                            if(this.getX()+60>=enemyTank.getX()//右下角
                                    &&this.getX()+60<=enemyTank.getX()+60
                                    &&this.getY()+40>=enemyTank.getY()
                                    &&this.getY()+40<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }break;
            case 2:
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    //不和自己比较
                    if(enemyTank!=this){
                        if(enemyTank.getDirect()==0||enemyTank.getDirect()==2){//上下
                            //如果敌人坦克是上/下 X范围[enemyTank.getX(),enemyTank.getX()+40]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克左下角的坐标[this.getX(),this.getY()+60]
                            if(this.getX()>=enemyTank.getX()//左下角
                                    &&this.getX()<=enemyTank.getX()+40
                                    &&this.getY()+60>=enemyTank.getY()
                                    &&this.getY()+60<=enemyTank.getY()+60){
                                return true;
                            } //当前坦克右下角的坐标[this.getX()+40,this.getY()+60]
                            if(this.getX()+40>=enemyTank.getX()//右下角
                                    &&this.getX()+40<=enemyTank.getX()+40
                                    &&this.getY()+60>=enemyTank.getY()
                                    &&this.getY()+60<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        else if(enemyTank.getDirect()==1||enemyTank.getDirect()==3){//左右
                            //如果敌人坦克是左/右 X范围[enemyTank.getX(),enemyTank.getX()+60]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+40]
                            if(this.getX()>=enemyTank.getX()//左下角
                                    &&this.getX()<=enemyTank.getX()+60
                                    &&this.getY()+60>=enemyTank.getY()
                                    &&this.getY()+60<=enemyTank.getY()+40){
                                return true;
                            }
                            if(this.getX()+40>=enemyTank.getX()//右下角
                                    &&this.getX()+40<=enemyTank.getX()+60
                                    &&this.getY()+60>=enemyTank.getY()
                                    &&this.getY()+60<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }break;
            case 3:
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    //不和自己比较
                    if(enemyTank!=this){
                        if(enemyTank.getDirect()==0||enemyTank.getDirect()==2){//上下
                            //如果敌人坦克是上/下 X范围[enemyTank.getX(),enemyTank.getX()+40]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+60]
                            //当前坦克左上角的坐标[this.getX(),this.getY()]
                            if(this.getX()>=enemyTank.getX()//左上角
                                    &&this.getX()<=enemyTank.getX()+40
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+60){
                                return true;
                            } //当前坦克左下角的坐标[this.getX(),this.getY()+40]
                            if(this.getX()>=enemyTank.getX()//左下角
                                    &&this.getX()<=enemyTank.getX()+40
                                    &&this.getY()+40>=enemyTank.getY()
                                    &&this.getY()+40<=enemyTank.getY()+60){
                                return true;
                            }
                        }
                        else if(enemyTank.getDirect()==1||enemyTank.getDirect()==3){//左右
                            //如果敌人坦克是左/右 X范围[enemyTank.getX(),enemyTank.getX()+60]
                            //                 Y范围[enemyTank.getY(),enemyTank.getY()+40]
                            if(this.getX()>=enemyTank.getX()//左上角
                                    &&this.getX()<=enemyTank.getX()+60
                                    &&this.getY()>=enemyTank.getY()
                                    &&this.getY()<=enemyTank.getY()+40){
                                return true;
                            }
                            if(this.getX()>=enemyTank.getX()//左下角
                                    &&this.getX()<=enemyTank.getX()+60
                                    &&this.getY()+40>=enemyTank.getY()
                                    &&this.getY()+40<=enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }break;
        }return false;
    }
    int i = 1;
    @Override
    public void run() {
        setSpeed(3);
        while (true){
            if(shotsenemy.size()==0){
                Shot s = null;
                switch (getDirect()){
                    case 0:
                        s = new Shot(getX()+20,getY(),0);break;
                    case 1:
                        s = new Shot(getX()+60,getY()+20,1);break;
                    case 2:
                        s = new Shot(getX()+20,getY()+60,2);break;
                    case 3:
                        s = new Shot(getX(),getY()+20,3);break;
                }shotsenemy.add(s);
                new Thread(s).start();
            }
            //根据坦克的方向来继续移动
            switch (getDirect()){
                case 0:if(getY()>0&&!IsTouchEnemyTank()){
                    Moveup();
                }else i=30;
                break;
                case 1:if (getX()<540&&!IsTouchEnemyTank()){
                    Moveright();
                }else i=30;
                break;
                case 2:if (getY()<520&&!IsTouchEnemyTank()){
                    Movedown();
                }else i=30;
                break;
                case 3:if (getX()>0&&!IsTouchEnemyTank()){
                    Moveleft();
                }else i=30;
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }//随机改变坦克方向
            if(i%30==0)//不让它方向转的太快
                setDirect((int) (Math.random()*4));//是0~3的随机整数
            //老韩说:写并发程序，一定考虑清楚，该线程什么时候结束
            if (!islive)
                break;
            i++;
        }
    }
}
