import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Objects;
import java.util.Vector;

//我们坦克大战的绘图区
class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义我的坦克
    MyTank myTank = null;
    //定义敌人的坦克，放入到Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个存放Node对象的Vector，用于恢复敌人坦克的坐标和方向。
    Vector<Node> nodes = new Vector<>();
    //定义一个Vector，用于存放炸弹
    //当子弹击中坦克时，加入一个Bomb对象到bombs
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 10;
    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    public MyPanel(String key){
        //要先判断文件是否存在
        //如果存在，就正常执行，如果文件不存在，提示，只能开启新游戏，key="1"
        File file = new File(Recorder.getRecordFile());
        //加载上局的数据（如果有的话）
        if(Objects.equals(key, "2")) {
            if (file.exists()) {
                nodes = Recorder.getNodesAndEnemyTankRec();
            }
            else {//如果是刚开始第一盘，还没有创建那个文件，就直接开始一个新游戏
                key = "1";
            }
        }
        //将MyPanel对象的enemyTanks设置给Record的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);
        myTank = new MyTank(100,100); //初始化自己坦克
        myTank.setSpeed(7);
        //初始化敌人坦克
        switch (key){
            case "1":
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank((50 * (i + 1)), 0);
                    //将enemTanks设置给enemyTank对象
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置敌人坦克初始方向
                    enemyTank.setDirect(2);
                    //启动敌人坦克的线程，让它动起来
                    new Thread(enemyTank).start();
                    //加入一颗子弹(的线程)
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, 2);
                    enemyTank.shotsenemy.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                //这里已经加载好了坦克面板，播放指定音乐
                new AePlayWave("src\\111.wav").start();
                break;
            case "2"://继续上局
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人的坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemTanks设置给enemyTank对象
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置敌人坦克初始方向
                    enemyTank.setDirect(node.getDirection());
                    //启动敌人坦克的线程，让它动起来
                    new Thread(enemyTank).start();
                    //加入一颗子弹(的线程)
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, 2);
                    enemyTank.shotsenemy.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    enemyTanks.add(enemyTank);
                }
                break;
            default:
                System.out.println("你的输入有误...");
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }
    //编写方法，显示我方击毁敌方坦克的信息。
    public void showInfo(Graphics g){
        //画出玩家的总成绩
        g.setColor(Color.black);
        Font font = new Font("黑体",Font.BOLD,16);
        g.setFont(font);

        g.drawString("您已摧毁敌方坦克",600,30);
        drawTank(600,50,g,0,0);
        g.setColor(Color.black);
        font = new Font("黑体",Font.BOLD,26);
        g.setFont(font);
        g.drawString(Recorder.getAllEnemyTankNum()+"",660,90);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0,0,600,600);//填充矩形，默认是黑色
        showInfo(g);
        if(myTank.islive){//判断我方坦克是否存活，如否，则不再画出hero
            //画出坦克--封装方法
            drawTank(myTank.getX(), myTank.getY(),g, myTank.getDirect(),0);
            //画出hero射出的所有存活子弹
            for (int i = 0; i < myTank.shotsMyTank.size(); i++) {
                Shot shot = myTank.shotsMyTank.get(i);
                if(shot!=null&&shot.islive){
                    g.draw3DRect(shot.x,shot.y,2,2,false);
                }
                else {
                    myTank.shotsMyTank.remove(shot);
                }
            }
        }
        //如果bombs集合中由对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.life>8){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            } else if (bomb.life>4){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            } else {
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            }
            //让炸弹生命值减少
            bomb.lifeDown();//因为一直在重绘，所以这里不需要别的循环
            if (bomb.life==0){
                bombs.remove(bomb);
            }
        }
        //画出敌人的坦克，遍历Vector
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断敌人坦克是否存活
            if(enemyTank.islive){//真则
                drawTank(enemyTank.getX(),enemyTank.getY(),g,enemyTank.getDirect(),1);
                //画出敌人所有子弹
                for (int j = 0; j < enemyTank.shotsenemy.size(); j++) {
                    Shot shot = enemyTank.shotsenemy.get(j);
                    if(shot.islive) {
                        g.draw3DRect(shot.x, shot.y, 2, 2, false);
                    }else {
                        //从Vector移除它,防止这个子弹已经false，却还一直在画
                        //shot.islive = false;
                        enemyTank.shotsenemy.remove(shot);
                        //然后再这里搞一个新的shot。也许，敌人的子弹不需要用到vector，因为一台坦克同时只有一发子弹。
                    }
                }
            }
            else {//否则销
                enemyTanks.remove(enemyTank);
            }
        }
//        drawTank(hero.getX()+60,hero.getY(),g,1,1);
//        drawTank(hero.getX()+140,hero.getY(),g,2,0);
//        drawTank(hero.getX()+200,hero.getY(),g,3,1);
        //drawCiacada(g);
    }
    //编写方法，画出坦克
    //  输入/**加回车
    /**
     *
     * @param x 坦克左上角横坐标
     * @param y 坦克左上角纵坐标
     * @param g 画笔
     * @param direct 坦克方向(上下左右)
     * @param type 坦克类型
     */
    public void drawTank(int x,int y,Graphics g,int direct,int type){
        //根据不同类型的坦克，给它设置不同的颜色。
        switch (type){
            case 0://我们的坦克
                g.setColor(Color.cyan);break;
            case 1://敌人的坦克
                g.setColor(Color.yellow);break;
        }
        switch (direct){//表示方向(0：向上  1：向右  2：向下  3：向左)
            case 0://表示向上
                g.fill3DRect(x,y,10,60,false);//坦克左轮
                g.fill3DRect(x+30,y,10,60,false);//坦克右轮
                g.fill3DRect(x+10,y+10,20,40,false);//坦克主题矩形
                g.fillOval(x+10,y+20,20,20);//坦克圆盖
                g.drawLine(x+20,y,x+20,y+30);//坦克炮管
                    break;
            case 1://表示向右
                g.fill3DRect(x,y,60,10,false);//坦克左轮
                g.fill3DRect(x,y+30,60,10,false);//坦克右轮
                g.fill3DRect(x+10,y+10,40,20,false);//坦克主题矩形
                g.fillOval(x+20,y+10,20,20);//坦克圆盖
                g.drawLine(x+30,y+20,x+60,y+20);//坦克炮管
                break;
            case 2://表示向下
                g.fill3DRect(x,y+1,10,60,false);//坦克左轮
                g.fill3DRect(x+30,y,10,60,false);//坦克右轮
                g.fill3DRect(x+10,y+10,20,40,false);//坦克主题矩形
                g.fillOval(x+10,y+20,20,20);//坦克圆盖
                g.drawLine(x+20,y+60,x+20,y+30);//坦克炮管
                break;
            case 3://表示向左
                g.fill3DRect(x,y,60,10,false);//坦克左轮
                g.fill3DRect(x,y+30,60,10,false);//坦克右轮
                g.fill3DRect(x+10,y+10,40,20,false);//坦克主题矩形
                g.fillOval(x+20,y+10,20,20);//坦克圆盖
                g.drawLine(x+30,y+20,x,y+20);//坦克炮管
                break;
            default:
                System.out.println("暂时没有处理。");
        }
    }
    public void drawCiacada(Graphics g){
        g.setColor(Color.white);
        g.fillOval(40,10,10,10);
        g.fillOval(60,10,10,10);
        g.setColor(Color.red);
        g.fillOval(45,15,20,20);
        g.setColor(Color.green);
        g.fillOval(40,25,20,40);
        g.fillOval(50,25,20,40);
    }
    //我们的子弹是否击中敌人
    public void hitenemyTank(EnemyTank enemyTank){
        for (int j = 0; j < myTank.shotsMyTank.size(); j++) {
            Shot shot = myTank.shotsMyTank.get(j);
            hitTank(shot, enemyTank);
            if(!shot.islive)//hero的子弹击中敌方，将该子弹从hero的vector踢除
                myTank.shotsMyTank.remove(shot);
        }
    }
    public void hitTank(Shot shot, Tank tank){//这个是基础方法
        switch (tank.getDirect()){
            case 0://向上与向下都是这段代码
            case 2:
                if((shot.x>tank.getX()&&shot.x<tank.getX()+40)&&
                        (shot.y>tank.getY()&&shot.y<tank.getY()+60)){
                    shot.islive = false;//销毁这个shot
                    //if (i==0)没有完成敌方子弹击中hero后将该子弹剔除
                    //敌方的子弹击中hero，将该子弹从敌方坦克的vector踢除
                    tank.islive = false;//销毁这个坦克

                    //当我方集合一个敌人坦克时，就对数据allEnemytankNum++
                    //解读，因为tank可以是hero也可以是EnemyTank
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }

                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(),tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if((shot.x>tank.getX()&&shot.x<tank.getX()+60)&&
                        (shot.y>tank.getY()&&shot.y<tank.getY()+40)){
                    shot.islive = false;
                    tank.islive = false;
                    if (tank instanceof EnemyTank){
                        Recorder.addAllEnemyTankNum();
                    }
                    //创建Bomb对象，加入到bombs集合
                    Bomb bomb = new Bomb(tank.getX(),tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_S){
            myTank.setDirect(2);
            if (myTank.getY()<520)
                myTank.Movedown();
        }
        else if (e.getKeyCode() == KeyEvent.VK_W){
            myTank.setDirect(0);
            if(myTank.getY()>0)
                myTank.Moveup();
        }
        else if (e.getKeyCode() == KeyEvent.VK_A){
            myTank.setDirect(3);
            if(myTank.getX()>0)
                myTank.Moveleft();
        }
        else if (e.getKeyCode() == KeyEvent.VK_D){
            myTank.setDirect(1);
            if(myTank.getX()<540)
                myTank.Moveright();
        }
        else if (e.getKeyCode() == KeyEvent.VK_J){
            //if(hero.shot!=null)当发射出一颗子弹时，前一发子弹的线程消亡
                //hero.shot.setIslive(false);
            myTank.shutOtherTank();
        }
        //要让面板重绘一次,我觉得这里可以省了，因为它的进程是每50ms就重绘一次。
        //this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每隔50毫秒,重绘区域,刷新绘图区域,子弹移动
        while (true){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (myTank.islive)
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //我方子弹是否击中敌方
                    hitenemyTank(enemyTank);//hero同时有很多子弹
                    //敌方子弹是否击中我方
                    for (int j = 0; j < enemyTank.shotsenemy.size(); j++) {
                        Shot shot = enemyTank.shotsenemy.get(j);
                        hitTank(shot, myTank);
                    }
                }
            this.repaint();
        }
    }
}
