import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class TankGame extends JFrame{
    MyPanel mp = null;
    Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        TankGame tankGame = new TankGame();
    }
    public TankGame(){
        System.out.println("请输入选择: 1.新游戏 2.继续上局");
        String key = scanner.next();
        mp = new MyPanel(key);
        //把mp放进Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//把面板(就是游戏绘图区域)加进去。
        this.setSize(760,600);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        //在JFrame 中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}
