package applications;

import apis.CircularOrbitAPIs;
import apis.CircularOrbitHelper;
import centralobject.CentralObject;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import exception.DependIncorrectException;
import exception.SameLabelException;
import exception.OutRules.ElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.PhysicalObject;
import track.Track;

public class AtomStructureAPI {

  private Logging logging = new Logging();

  /**
   * 菜单
   */
  private static void menu() {
    System.out.println("-----------------------------");
    System.out.println("1.读取数据文件，生成多轨道结构");
    System.out.println("2.可视化轨道结构");
    System.out.println("3.增加新轨道");
    System.out.println("4.向特定轨道上增加电子");
    System.out.println("5.删除整条轨道");
    System.out.println("6.从特定轨道上删除电子");
    System.out.println("7.计算多轨道系统中各轨道上电子分布的熵值");
    System.out.println("8.模拟电子跃迁");
    System.out.println("9.按照时间段查询日志");
    System.out.println("10.按照操作类型查询日志");
    System.out.println("11.查询日志中出现的异常");
    System.out.println("12.结束");
    System.out.println("请输入序号选择操作：");
  }

  /**
   * 选择菜单中的功能执行
   * 
   * @return true 读取的文件不存在语法错误 false 读取的文件存在语法错误
   */
  public boolean execute() {
    CircularOrbit<CentralObject, PhysicalObject> c = new AtomStructure<>();
    Scanner in = new Scanner(System.in);
    while (true) {
      boolean end = false;
      menu();
      int m = in.nextInt();
      switch (m) {
      case 1:
        System.out.print("请输入文件名称：");
        String nameString = in.next();
        logging.doLog("读取文件" + nameString + ".txt");
        try {
          AtomStructure<CentralObject, PhysicalObject> a1 = (AtomStructure<CentralObject, PhysicalObject>) c;
          a1.readFile("src/txt/" + nameString + ".txt");
        } catch (SameLabelException | DependIncorrectException | ElementException e) {
          logging.logException(e);
          e.printStackTrace();
          System.out.println("读取文件语法错误，一会儿请重新选择文件");
          logging.doLog("读取文件语法错误一会儿请重新选择文件");
          return false;
        }
        break;
      case 2:
        logging.doLog("可视化");
        CircularOrbitHelper.visualize(c);
        break;
      case 3:
        System.out.print("请输入要增加轨道的编号：");
        int n3 = in.nextInt();
        System.out.print("请输入要增加轨道的半径：");
        int r3 = in.nextInt();
        logging.doLog("添加轨道的编号为" + n3 + "半径为" + r3);
        c.addTrack(n3, r3);
        break;
      case 4:
        System.out.print("请输入目标轨道的编号：");
        int n4 = in.nextInt();
        System.out.print("请输入目标轨道的半径：");
        int r4 = in.nextInt();
        logging.doLog("在编号为" + n4 + "半径为" + r4 + "的轨道上添加电子");
        AtomStructure<CentralObject, PhysicalObject> atomStructure4 = (AtomStructure<CentralObject, PhysicalObject>) c;
        c.addTrackObject(n4, r4, atomStructure4.createPhysicalObject(n4));
        break;
      case 5:
        System.out.print("请输入要删除轨道的半径：");
        int r5 = in.nextInt();
        c.removeTrack(r5);
        logging.doLog("删除半径为" + r5 + "的轨道");
        break;
      case 6:
        System.out.print("请输入要删除物体所在轨道的半径：");
        int r6 = in.nextInt();
        logging.doLog("删除在半径为" + r6 + "的轨道上的一个电子");
        for (Track track : c.getCircles().keySet()) {
          if (track.getR() == r6) {
            if (c.getCircles().get(track).size() != 0) {
              c.removeObjectFromOneTrack(r6, c.getCircles().get(track).get(0));
              break;
            } else {
              System.out.println("此轨道上不存在电子");
              logging.doLog("此轨道上不存在电子");
            }
          }
        }

        break;
      case 7:
        CircularOrbitAPIs api7 = new CircularOrbitAPIs();
        System.out.print("物体分布的熵值" + api7.getObjectDistributionEntropy(c));
        logging.doLog("计算电子分布熵值");
        break;
      case 8:
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();
        AtomStructure<CentralObject, PhysicalObject> atomStructure8 = (AtomStructure<CentralObject, PhysicalObject>) c;
        AtomStructure<CentralObject, PhysicalObject> a1 = atomStructure8.copyAS();
        a1.changeCircles(atomStructure8.getCircles());
        originator.setAtom(a1);
        careTaker.add(originator.saveStateToMemento());
        System.out.print("请输入目标轨道的编号：");
        int n8 = in.nextInt();
        System.out.print("请输入目标轨道的半径：");
        int r8 = in.nextInt();
        System.out.print("请输入要跃迁的电子所在的轨道的半径：");
        int r88 = in.nextInt();
        logging.doLog("电子从半径为" + r8 + "的轨道跃迁到半径为" + r88 + "的轨道上");
        for (Track track : c.getCircles().keySet()) {
          if (track.getR() == r88) {
            if (c.getCircles().get(track).size() != 0) {
              PhysicalObject ele = c.getCircles().get(track).get(0);
              atomStructure8.transit(ele, n8, r8);
              break;
            } else {
              System.out.println("原来的轨道上不存在电子");
              logging.doLog("原来的轨道上不存在电子");
            }
          }
        }

        break;
      case 9:
        List<String> content = new ArrayList<>();
        try {
          FileReader fr = new FileReader(logging.getFileName());
          BufferedReader bf = new BufferedReader(fr);
          String str; // 按行读取字符串
          while ((str = bf.readLine()) != null) {
            content.add(str);
          }
          bf.close();
          fr.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        String startTime = null, finishTime = null;
        Matcher matcherStart, matcherFinish;
        while (true) {
          System.out.println("请输入要查询的起始时间(例：2019-01-01T00:00:00)");
          startTime = in.next();
          Pattern patternStart = Pattern
              .compile("([0-9]{4}-[0-9]{2}-[0-9]{2})T([0-9]{2}:[0-9]{2}:[0-9]{2})");
          matcherStart = patternStart.matcher(startTime);
          if (matcherStart.find())
            break;
          else {
            System.out.println("输入格式错误，请重新输入");
          }
        }
        while (true) {
          System.out.println("请输入要查询的结束时间(例：2019-01-01T00:00:00),请输入晚于开始的时间");
          finishTime = in.next();
          Pattern patternFinish = Pattern
              .compile("([0-9]{4}-[0-9]{2}-[0-9]{2})T([0-9]{2}:[0-9]{2}:[0-9]{2})");
          matcherFinish = patternFinish.matcher(finishTime);
          if (matcherFinish.find())
            break;
          else {
            System.out.println("输入格式错误，请重新输入");
          }
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start = null, finish = null;
        try {
          start = sf.parse(matcherStart.group(1) + " " + matcherStart.group(2));
          finish = sf.parse(matcherFinish.group(1) + " " + matcherFinish.group(2));
        } catch (ParseException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        Pattern patternt = Pattern
            .compile("<date>([0-9]{4}-[0-9]{2}-[0-9]{2})T([0-9]{2}:[0-9]{2}:[0-9]{2})");
        int i = 0, flag = 0;
        int j = i;
        while (i < content.size() && j < content.size()) {
          for (j = i; j < content.size(); j++) {
            Matcher matchert = patternt.matcher(content.get(j));
            if (matchert.find()) {
              Date now = null;
              Calendar cal = Calendar.getInstance();
              try {
                now = sf.parse(matchert.group(1) + " " + matchert.group(2));
                cal.setTime(now);
                cal.add(Calendar.HOUR_OF_DAY, 8);
                now = cal.getTime();
              } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
              if (now != null && start != null && finish != null) {
                if (now.after(start) && now.before(finish)) {
                  for (int k = j + 1; k < content.size(); k++) {
                    Pattern patternm = Pattern
                        .compile("<message>([a-zA-Z0-9\\u4e00-\\u9fa5\\.]*)</message>");
                    Matcher matcherm = patternm.matcher(content.get(k));
                    if (matcherm.find()) {
                      System.out.println(matcherm.group(1));
                      i = k + 1;
                      flag = 1;
                      break;
                    }
                  }
                }
              }
              if (flag == 1)
                break;
            }
          }
        }
        if (flag == 0)
          System.out.println("此时间段没有匹配的操作");
        break;
      case 10:
        List<String> content1 = new ArrayList<>();
        try {
          FileReader fr = new FileReader(logging.getFileName());
          BufferedReader bf = new BufferedReader(fr);
          String str; // 按行读取字符串
          while ((str = bf.readLine()) != null) {
            content1.add(str);
          }
          bf.close();
          fr.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println("1.读取数据文件，生成多轨道结构");
        System.out.println("2.可视化轨道结构");
        System.out.println("3.增加新轨道");
        System.out.println("4.向特定轨道上增加电子");
        System.out.println("5.删除整条轨道");
        System.out.println("6.从特定轨道上删除电子");
        System.out.println("7.计算多轨道系统中各轨道上电子分布的熵值");
        System.out.println("8.模拟电子跃迁");
        System.out.println("请输入要查询的操作的序号");
        int o = in.nextInt();
        if (o == 1) {
          for (String line : content1) {
            Pattern pattern1 = Pattern.compile("<message>读取文件");
            Matcher matcher1 = pattern1.matcher(line);
            if (matcher1.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 2) {
          for (String line : content1) {
            Pattern pattern2 = Pattern.compile("<message>可视");
            Matcher matcher2 = pattern2.matcher(line);
            if (matcher2.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 3) {
          for (String line : content1) {
            Pattern pattern3 = Pattern.compile("<message>添加轨道的编号为");
            Matcher matcher3 = pattern3.matcher(line);
            if (matcher3.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 4) {
          for (String line : content1) {
            Pattern pattern4 = Pattern.compile("<message>在编号为");
            Matcher matcher4 = pattern4.matcher(line);
            if (matcher4.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 5) {
          for (String line : content1) {
            Pattern pattern5 = Pattern.compile("<message>删除半径为");
            Matcher matcher5 = pattern5.matcher(line);
            if (matcher5.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 6) {
          for (String line : content1) {
            Pattern pattern6 = Pattern.compile("<message>删除在半径为");
            Matcher matcher6 = pattern6.matcher(line);
            if (matcher6.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 7) {
          for (String line : content1) {
            Pattern pattern7 = Pattern.compile("<message>计算电子分布熵值");
            Matcher matcher7 = pattern7.matcher(line);
            if (matcher7.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 8) {
          for (String line : content1) {
            Pattern pattern8 = Pattern.compile("<message>电子从半径为");
            Matcher matcher8 = pattern8.matcher(line);
            if (matcher8.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else {
          System.out.println("输入序号错误");
        }
        break;
      case 11:
        List<String> content2 = new ArrayList<>();
        try {
          FileReader fr = new FileReader(logging.getFileName());
          BufferedReader bf = new BufferedReader(fr);
          String str; // 按行读取字符串
          while ((str = bf.readLine()) != null) {
            content2.add(str);
          }
          bf.close();
          fr.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        for (String line : content2) {
          Pattern pattern1 = Pattern.compile("<message>Exception.DependIncorrectException");
          Matcher matcher1 = pattern1.matcher(line);
          Pattern pattern2 = Pattern.compile("<message>Exception.SameLabelException");
          Matcher matcher2 = pattern2.matcher(line);
          Pattern pattern3 = Pattern.compile("<message>Exception.Exception.OutRules");
          Matcher matcher3 = pattern3.matcher(line);
          if (matcher1.find()) {
            System.out.println("存在文件中各元素之间的依赖关系的异常");
          }
          if (matcher2.find()) {
            System.out.println("存在文件中标签完全一样的元素的异常");
          }
          if (matcher3.find()) {
            System.out.println("存在文件中输入文件中存在不符合语法规则的语句的异常");
          }
        }
        break;
      case 12:
        end = true;
        break;
      default:
        System.out.print("输入错误，请重新输入");
        logging.doLog("输入错误请重新输入");
      }
      if (end)
        break;
    }
    return true;
  }
}
