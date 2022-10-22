import java.io.*;
import java.util.Vector;

public class Recorder {
    //定义变量，记录我方击毁敌人的坦克数
    private static int allEnemyTankNum = 0 ;
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\RecordFile.txt";
    private static Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义一个Node 的Vector,用于保存敌人的信息node
    private static Vector<Node> nodes = new Vector<>();
    //增加一个方法，用于读取recordFile,恢复相关信息
    //该方法在继续上局的时候调用
    //返回记录文件路径
    public static String getRecordFile() {
        return recordFile;
    }

    public static Vector<Node> getNodesAndEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());//把击毁敌人的数量读入
            //循环读取文件，生成nodes集合
            String line = "";
            while((line=br.readLine())!=null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);//将每一个node放入nodes
            }

        } catch (IOException e) {
            try {
                br.close();
            } catch (IOException ex) {
                e.printStackTrace();
            }
            e.printStackTrace();
        }
        return nodes;
    }
    //当游戏退出时，我们将allEnemyTankNum保存到recordFile
    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum+"\r\n");
            //bw.newLine();换行
            for (int i = 0; i <enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.islive){
                    //保存该enemytank的信息
                    String record = enemyTank.getX()+" "+enemyTank.getY()+" "+enemyTank.getDirect();
                    bw.write(record+"\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
                try {
                    if(bw!=null)
                        bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
    public static void  addAllEnemyTankNum(){
        Recorder.allEnemyTankNum++;
    }
}
