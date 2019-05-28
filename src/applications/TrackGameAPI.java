package applications;

import apis.CircularOrbitAPIs;
import apis.CircularOrbitHelper;
import centralobject.CentralObject;
import circularorbit.CircularOrbit;
import circularorbit.TrackGame;
import exception.SameLabelException;
import exception.OutRules.DecimalsException;
import exception.OutRules.InformationNumIncorrectException;
import exception.OutRules.MetresFalseException;
import exception.OutRules.NotCaptialLetterException;
import exception.OutRules.TransposeException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.PhysicalObject;
import track.Track;

public class TrackGameAPI {

  private Logging logging = new Logging();

  /**
   * 菜单
   */
  private static void menu() {
    System.out.println("-----------------------------");
    System.out.println("1.读取数据文件，生成多轨道结构");
    System.out.println("2.可视化轨道结构");
    System.out.println("3.增加新轨道");
    System.out.println("4.向特定轨道上增加物体");
    System.out.println("5.删除整条轨道");
    System.out.println("6.从特定轨道上删除物体");
    System.out.println("7.计算多轨道系统中各轨道上物体分布的熵值");
    System.out.println("8.编排比赛方案");
    System.out.println("9.手工调整比赛方案（为两个选手更换赛道、更换组）");
    System.out.println("10.按照时间段查询日志");
    System.out.println("11.按照操作类型查询日志");
    System.out.println("12.查询日志中出现的异常");
    System.out.println("13.结束");
    System.out.println("请输入序号选择操作：");
  }

  /**
   * 判断多轨道系统的合法性（跑道数为 4-10 之间；每一组的人数不能超 过跑道数、每一组的每条跑道里最多 1 位运动员 （但可以没有运动员）；如果第 n
   * 组的人数少于跑道数，则第 0 到第 n-1 各组的人数必须等于跑 道数；同一个运动员只能出现在一组比赛中）
   * 
   * @param makeGroup a map key是组数 value是此组对应的多轨道系统
   * @return true 多轨道系统合法 false 多轨道系统不合法 防御策略：assert 判断pre-condition 每组都存在一个多轨道系统
   */
  private boolean legal(Map<Integer, CircularOrbit<CentralObject, PhysicalObject>> makeGroup) {
    for (Integer kk : makeGroup.keySet()) {
      assert makeGroup.get(kk) != null;
    }
    List<String> allAth = new ArrayList<>();
    for (Integer integer : makeGroup.keySet()) {
      Map<Track, List<PhysicalObject>> cirMap = makeGroup.get(integer).getCircles();
      if (cirMap.size() < 4 || cirMap.size() > 10) {
        logging.doLog("跑道数不在 4到10 之间");
        System.out.println("跑道数不在 4-10 之间");
        return false;
      }
      if (integer < makeGroup.size() - 1) {// 0~n-1组
        for (Track track : cirMap.keySet()) {
          if (cirMap.get(track).size() != 1) {
            System.out.println("第 0 到第 n-1 各组的人数不等于跑道数");
            logging.doLog("第 0 到第 n到1 各组的人数不等于跑道数");
            return false;
          } else {
            if (!allAth.contains(cirMap.get(track).get(0).getName())) {
              allAth.add(cirMap.get(track).get(0).getName());
            } else {
              System.out.println("同一个运动员不止只出现在一组比赛中");
              logging.doLog("同一个运动员不止只出现在一组比赛中");
              return false;
            }
          }
        }
      } else {// n组
        for (Track track : cirMap.keySet()) {
          if (cirMap.get(track).size() != 1 && cirMap.get(track).size() != 0) {
            System.out.println("第n各组的人数不是小于等于跑道数");
            logging.doLog("第n各组的人数不是小于等于跑道数");
            return false;
          } else if (cirMap.get(track).size() == 1) {
            if (!allAth.contains(cirMap.get(track).get(0).getName())) {
              allAth.add(cirMap.get(track).get(0).getName());
            } else {
              System.out.println("同一个运动员不止只出现在一组比赛中");
              logging.doLog("同一个运动员不止只出现在一组比赛中");
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * 选择菜单中的功能执行
   * 
   * @return true 读取的文件不存在语法错误 false 读取的文件存在语法错误
   */
  public boolean execute() {
    Map<Integer, CircularOrbit<CentralObject, PhysicalObject>> makeGroup = new HashMap<>();
    CircularOrbit<CentralObject, PhysicalObject> c = new TrackGame<CentralObject, PhysicalObject>();
    Scanner in = new Scanner(System.in);
    String nameString = null;
    while (true) {
      boolean end = false;
      menu();
      int m = in.nextInt();
      switch (m) {
      case 1:
        System.out.print("请输入文件名称:");
        nameString = in.next();
        logging.doLog("读取文件" + nameString + ".txt");
        try {
          TrackGame<CentralObject, PhysicalObject> t1 = (TrackGame<CentralObject, PhysicalObject>) c;
          t1.readFile("src/txt/" + nameString + ".txt");
        } catch (MetresFalseException | InformationNumIncorrectException | TransposeException
            | NotCaptialLetterException | DecimalsException | SameLabelException e) {
          logging.logException(e);
          e.printStackTrace();
          System.out.println("读取文件语法错误，一会儿请重新选择文件");
          logging.doLog("读取文件语法错误一会儿请重新选择文件");
          return false;
        }
        break;
      case 2:
        System.out.print("可视化的组有：");
        for (int i = 0; i < makeGroup.size(); i++) {
          System.out.print(i + "组  ");
        }
        System.out.print("请输入可视化的组:");
        int n2 = in.nextInt();
        logging.doLog("可视化第" + n2 + "组");
        CircularOrbitHelper.visualize(makeGroup.get(n2));
        break;
      case 3:
        System.out.print("请输入要增加轨道的编号：");
        int n3 = in.nextInt();
        System.out.print("请输入要增加轨道的半径：");
        int r3 = in.nextInt();
        logging.doLog("添加轨道的编号为" + n3 + "半径为" + r3);
        for (Integer x : makeGroup.keySet()) {
          assert makeGroup.get(x).getCircles().size() + 1 <= 10 : "添加后轨道超过10条";
          makeGroup.get(x).addTrack(n3, r3);
        }
        break;
      case 4:
        System.out.print("请输入要加入组的编号：");
        int g4 = in.nextInt();
        System.out.print("请输入目标轨道的编号：");
        int n4 = in.nextInt();
        System.out.print("请输入目标轨道的半径：");
        int r4 = in.nextInt();
        System.out.print("请输入该运动员的名字：");
        String name4 = in.next();
        System.out.print("请输入该运动员的号码：");
        int num4 = in.nextInt();
        System.out.print("请输入该运动员的国籍：");
        String nation4 = in.next();
        System.out.print("请输入该运动员的年龄：");
        int age4 = in.nextInt();
        System.out.print("请输入该运动员的最好成绩：");
        double result4 = in.nextDouble();
        logging.doLog("添加运动员" + name4 + "在第" + g4 + "组的编号为" + n4 + "的跑道上号码为" + num4 + "国籍为"
            + nation4 + "年龄为" + age4 + "最好成绩为" + result4);
        TrackGame<CentralObject, PhysicalObject> trackgame4 = (TrackGame<CentralObject, PhysicalObject>) makeGroup
            .get(g4);
        c.addTrackObject(n4, r4,
            trackgame4.createPhysicalObject(name4, num4, nation4, age4, result4));
        break;
      case 5:
        System.out.print("请输入要删除轨道的半径：");
        int r5 = in.nextInt();
        for (Integer x : makeGroup.keySet()) {
          assert makeGroup.get(x).getCircles().size() - 1 >= 4 : "删除后轨道数小于4";
          makeGroup.get(x).removeTrack(r5);
        }
        logging.doLog("删除半径为" + r5 + "的跑道");
        break;
      case 6:
        System.out.print("请输入要删除运动员所在组的编号：");
        int g6 = in.nextInt();
        System.out.print("请输入要删除运动员所在轨道的半径：");
        int r6 = in.nextInt();
        System.out.print("请输入该运动员的名字：");
        String name6 = in.next();
        System.out.print("请输入该运动员的号码：");
        int num6 = in.nextInt();
        System.out.print("请输入该运动员的国籍：");
        String nation6 = in.next();
        System.out.print("请输入该运动员的年龄：");
        int age6 = in.nextInt();
        System.out.print("请输入该运动员的最好成绩：");
        double result6 = in.nextDouble();
        logging.doLog("删除运动员" + name6 + "在第" + g6 + "组的半径为" + r6 + "的跑道上号码为" + num6 + "国籍为"
            + nation6 + "年龄为" + age6 + "最好成绩为" + result6);
        TrackGame<CentralObject, PhysicalObject> trackgame6 = (TrackGame<CentralObject, PhysicalObject>) makeGroup
            .get(g6);
        c.removeObjectFromOneTrack(r6,
            trackgame6.createPhysicalObject(name6, num6, nation6, age6, result6));
        break;
      case 7:
        CircularOrbitAPIs api7 = new CircularOrbitAPIs();
        System.out.print("请输入要计算组的编号（共有" + makeGroup.size() + "组，编号从0开始）：");
        int g7 = in.nextInt();
        System.out.print("物体分布的熵值：" + api7.getObjectDistributionEntropy(makeGroup.get(g7)));
        logging.doLog("计算第" + g7 + "组的物体分布熵值");
        break;
      case 8:
        System.out.print("输入1按成绩排，输入2随机排，请输入序号：");
        Map<Integer, Map<PhysicalObject, Integer>> groupMap;
        while (true) {
          int n8 = in.nextInt();
          if (n8 == 1) {
            logging.doLog("选择按成绩编排出场顺序");
            StrategyContext strategyContext1 = new StrategyContext(new Score());
            TrackGame<CentralObject, PhysicalObject> trackgame8 = (TrackGame<CentralObject, PhysicalObject>) c;
            groupMap = strategyContext1.choose(trackgame8.getAllObjects(),
                trackgame8.getTracknums());
            break;
          } else if (n8 == 2) {
            logging.doLog("选择随机编排出场顺序");
            StrategyContext strategyContext2 = new StrategyContext(new Random());
            TrackGame<CentralObject, PhysicalObject> trackgame8 = (TrackGame<CentralObject, PhysicalObject>) c;
            groupMap = strategyContext2.choose(trackgame8.getAllObjects(),
                trackgame8.getTracknums());
            break;
          } else {
            logging.doLog("输入错误请重新输入序号");
            System.out.print("输入错误，请重新输入序号：");
          }
        }
        for (Integer num : groupMap.keySet()) {
          for (Entry<PhysicalObject, Integer> entry : groupMap.get(num).entrySet()) {
            System.out.println(
                "组号：" + num + "  运动员：" + entry.getKey().getName() + "  跑道：" + entry.getValue());
          }
        }
        for (Integer num : groupMap.keySet()) {
          TrackGame<CentralObject, PhysicalObject> k = new TrackGame<CentralObject, PhysicalObject>();
          try {
            k.readFile("src/txt/" + nameString + ".txt");
          } catch (MetresFalseException | InformationNumIncorrectException | TransposeException
              | NotCaptialLetterException | DecimalsException | SameLabelException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          for (Entry<PhysicalObject, Integer> entry : groupMap.get(num).entrySet()) {
            k.addTrackObject(entry.getValue() - 1, entry.getValue() - 1, entry.getKey());
          }
          makeGroup.put(num, k);
        }
        break;
      case 9:
        System.out.print("请输入运动员1的名字：");
        String name1 = in.next();
        System.out.print("请输入运动员2的名字：");
        String name2 = in.next();
        logging.doLog("交换运动员" + name1 + "运动员" + name2 + "的位置");
        int group1 = 0, group2 = 0, index1 = 0, index2 = 0, flag1 = 0, flag2 = 0;
        PhysicalObject ath1 = null, ath2 = null;
        for (Integer b : makeGroup.keySet()) {
          Map<Track, List<PhysicalObject>> p = makeGroup.get(b).getCircles();
          for (Track track : p.keySet()) {
            for (PhysicalObject phy : p.get(track)) {
              if (phy.getName().equals(name1)) {
                group1 = b;
                index1 = track.getN();
                flag1 = 1;
                ath1 = phy;
              } else if (phy.getName().equals(name2)) {
                group2 = b;
                index2 = track.getN();
                flag2 = 1;
                ath2 = phy;
              }
            }
          }
        }
        if (flag1 == 1 && flag2 == 1) {
          makeGroup.get(group1).removeObjectFromOneTrack(index1, ath1);
          makeGroup.get(group1).addTrackObject(index1, index1, ath2);
          makeGroup.get(group2).removeObjectFromOneTrack(index2, ath2);
          makeGroup.get(group2).addTrackObject(index2, index2, ath1);
        } else {
          System.out.println("这两个运动员有不存在的");
          logging.doLog("这两个运动员有不存在的");
        }
        break;
      case 10:
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
      case 11:
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
        System.out.println("1.读取数据文件");
        System.out.println("2.可视化轨道结构");
        System.out.println("3.增加新轨道");
        System.out.println("4.向特定轨道上增加物体");
        System.out.println("5.删除整条轨道");
        System.out.println("6.从特定轨道上删除物体");
        System.out.println("7.计算多轨道系统中各轨道上物体分布的熵值");
        System.out.println("8.编排比赛方案");
        System.out.println("9.手工调整比赛方案");
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
            Pattern pattern4 = Pattern.compile("<message>添加运动员");
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
            Pattern pattern6 = Pattern.compile("<message>删除运动员");
            Matcher matcher6 = pattern6.matcher(line);
            if (matcher6.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 7) {
          for (String line : content1) {
            Pattern pattern7 = Pattern.compile("<message>计算第");
            Matcher matcher7 = pattern7.matcher(line);
            if (matcher7.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 8) {
          for (String line : content1) {
            Pattern pattern8 = Pattern.compile("<message>选择");
            Matcher matcher8 = pattern8.matcher(line);
            if (matcher8.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 9) {
          for (String line : content1) {
            Pattern pattern9 = Pattern.compile("<message>交换运动员");
            Matcher matcher9 = pattern9.matcher(line);
            if (matcher9.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else {
          System.out.println("输入序号错误");
        }

        break;
      case 12:
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
      case 13:
        end = true;
        logging.doLog("结束");
        break;
      default:
        System.out.print("输入错误，请重新输入");
        logging.doLog("输入错误请重新输入");
      }
      if (end)
        break;
    }
    if (!legal(makeGroup)) {
      System.out.println("多轨道系统不合法");
      logging.doLog("多轨道系统不合法 ");
    }
    return true;
  }
}
