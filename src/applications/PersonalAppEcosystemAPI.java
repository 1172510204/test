package applications;

import apis.CircularOrbitAPIs;
import apis.CircularOrbitHelper;
import apis.PersonalAppEcosystemDifference;
import centralobject.CentralObject;
import circularorbit.CircularOrbit;
import circularorbit.PersonalAppEcosystem;
import exception.DependIncorrectException;
import exception.SameLabelException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.PhysicalObject;
import track.Track;

public class PersonalAppEcosystemAPI {

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
    System.out.println("8.获取两个时间段的轨道系统结构差异");
    System.out.println("9.计算两个 App 之间的逻辑距离");
    System.out.println("10.按照时间段查询日志");
    System.out.println("11.按照操作类型查询日志");
    System.out.println("12.查询日志中出现的异常");
    System.out.println("13.结束");
    System.out.println("请输入序号选择操作：");
  }

  /**
   * 判断多轨道系统的合法性（判断安装卸载的APP是否正确，判断轨道上APP的亲密度是否正确）
   * 
   * @param c 多轨道系统
   */
  private void legal(CircularOrbit<CentralObject, PhysicalObject> c) {
    PersonalAppEcosystem<CentralObject, PhysicalObject> per = (PersonalAppEcosystem<CentralObject, PhysicalObject>) c;
    Map<String, Integer> close = per.getIntimacy();
    List<Integer> inside = new ArrayList<>();
    Map<Track, List<PhysicalObject>> cirMap = c.getCircles();
    while (c.iterator().hasNext()) {
      Track track = c.iterator().next();
      List<Integer> eachTrack = new ArrayList<>();
      for (PhysicalObject each : cirMap.get(track)) {
        for (Integer integer : inside) {
          if (close.get(each.getName()) < integer) {
            eachTrack.add(close.get(each.getName()));
          } else {
            System.out.println("存在靠用户更近的APP的亲密度小于靠用户更远的APP的亲密度");
            logging.doLog("存在靠用户更近的APP的亲密度小于靠用户更远的APP的亲密度");
          }
        }
      }
      for (Integer integer : eachTrack) {
        inside.add(integer);
      }
    }
  }

  /**
   * 选择菜单中的功能执行
   * 
   * @return true 读取的文件不存在语法错误 false 读取的文件存在语法错误
   */
  public boolean execute() {
    CircularOrbit<CentralObject, PhysicalObject> c = new PersonalAppEcosystem<>();
    Scanner in = new Scanner(System.in);
    String nameString = null;
    while (true) {
      boolean end = false;
      menu();
      int m = in.nextInt();
      switch (m) {
      case 1:
        System.out.print("请输入文件名称：");
        nameString = in.next();
        logging.doLog("读取文件：" + nameString + ".txt");
        try {
          PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = (PersonalAppEcosystem<CentralObject, PhysicalObject>) c;
          p1.readFile("src/txt/" + nameString + ".txt");
        } catch (SameLabelException | DependIncorrectException e) {
          logging.logException(e);
          e.printStackTrace();
          System.out.println("读取文件语法错误，一会儿请重新选择文件");
          logging.doLog("读取文件语法错误一会儿请重新选择文件");
          return false;
        }
        break;
      case 2:
        PersonalAppEcosystem<CentralObject, PhysicalObject> s = new PersonalAppEcosystem<>();
        try {
          s.readFile("src/txt/" + nameString + ".txt");
        } catch (SameLabelException | DependIncorrectException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
          return false;
        }
        System.out.print("请输入要可视化的时间段");
        String timeString = in.next();
        logging.doLog("可视化" + timeString + "的时间段");
        s.putPhysicalObjectOnCircles(timeString);
        CircularOrbitHelper.visualize(s);
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
        System.out.print("请输入要添加APP的名字：");
        String appname4 = in.next();
        System.out.print("请输入APP的公司：");
        String company4 = in.next();
        System.out.print("请输入APP的版本：");
        String version4 = in.next();
        System.out.print("请输入APP的功能：");
        String function4 = in.next();
        System.out.print("请输入APP的适用领域：");
        String field4 = in.next();
        logging.doLog("向编号为" + n4 + "半径为" + r4 + "的轨道上添加名字为" + appname4 + "的APP公司" + company4 + "版本"
            + version4 + "功能" + function4 + "适用领域" + field4);
        PersonalAppEcosystem<CentralObject, PhysicalObject> personalAppEcosystem4 = (PersonalAppEcosystem<CentralObject, PhysicalObject>) c;
        c.addTrackObject(n4, r4, personalAppEcosystem4.createPhysicalObject(appname4, company4,
            version4, function4, field4));
        break;
      case 5:
        System.out.print("请输入要删除轨道的半径：");
        int r5 = in.nextInt();
        logging.doLog("删除半径为" + r5 + "的轨道");
        c.removeTrack(r5);
        break;
      case 6:
        System.out.print("请输入要删除APP所在轨道的半径：");
        int r6 = in.nextInt();
        System.out.print("请输入要删除APP的名字：");
        String appname6 = in.next();
        logging.doLog("删除的app" + appname6 + "在半径为" + r6 + "的轨道上的");
        for (Track track : c.getCircles().keySet()) {
          int flag = 0;
          if (track.getR() == r6) {
            for (PhysicalObject app : c.getCircles().get(track)) {
              if (app.getName().equals(appname6)) {
                c.removeObjectFromOneTrack(r6, app);
                flag = 1;
                break;
              }
            }
          }
          if (flag == 1)
            break;
        }
        break;
      case 7:
        CircularOrbitAPIs api7 = new CircularOrbitAPIs();
        PersonalAppEcosystem<CentralObject, PhysicalObject> l = new PersonalAppEcosystem<>();
        try {
          l.readFile("src/txt/" + nameString + ".txt");
        } catch (SameLabelException | DependIncorrectException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        System.out.print("请输入要计算熵值的时间段");
        String timeString7 = in.next();
        l.putPhysicalObjectOnCircles(timeString7);
        System.out.print("物体分布的熵值：" + api7.getObjectDistributionEntropy(l));
        logging.doLog("计算时间段为" + timeString7 + "app分布熵值");
        break;
      case 8:
        CircularOrbitAPIs api8 = new CircularOrbitAPIs();
        PersonalAppEcosystem<CentralObject, PhysicalObject> t81 = new PersonalAppEcosystem<>();
        PersonalAppEcosystem<CentralObject, PhysicalObject> t82 = new PersonalAppEcosystem<>();
        System.out.print("请输入要比较差异的第一个时间段：");
        String time81 = in.next();
        System.out.print("请输入要比较差异的第二个时间段：");
        String time82 = in.next();
        logging.doLog("比较时间段为" + time81 + "和时间段为" + time82 + "两个多轨道系统的差异");
        try {
          t81.readFile("src/txt/" + nameString + ".txt");
          t82.readFile("src/txt/" + nameString + ".txt");
        } catch (SameLabelException | DependIncorrectException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        t81.putPhysicalObjectOnCircles(time81);
        t82.putPhysicalObjectOnCircles(time82);
        PersonalAppEcosystemDifference diff = (PersonalAppEcosystemDifference) api8
            .getDifference(t81, t82);
        System.out.println("轨道数差异" + diff.getTrackNums());
        Map<Integer, Integer> eachNumsMap = diff.getTrackNumberAndObjectsDif();
        Map<Integer, String> eachNameMap = diff.getTrackNumberAndName();
        for (int i = 1; i <= eachNameMap.size(); i++) {
          System.out
              .println("轨道" + i + "的物体数量差异：" + eachNumsMap.get(i) + "  物体差异：" + eachNameMap.get(i));
        }
        break;
      case 9:
        CircularOrbitAPIs api9 = new CircularOrbitAPIs();
        System.out.print("请输入第一个APP的名字：");
        String appname91 = in.next();
        System.out.print("请输入第二个APP的名字：");
        String appname92 = in.next();
        logging.doLog("计算逻辑距离" + appname91 + "和" + appname92);
        PersonalAppEcosystem<CentralObject, PhysicalObject> per9 = (PersonalAppEcosystem<CentralObject, PhysicalObject>) c;
        List<PhysicalObject> allObjects = per9.getAllObjects();
        PhysicalObject app91 = null, app92 = null;
        for (PhysicalObject each : allObjects) {
          if (each.getName().equals(appname91)) {
            app91 = each;
          }
          if (each.getName().equals(appname92)) {
            app92 = each;
          }
        }
        if (app91 != null && app92 != null) {
          System.out.println("两个App的逻辑距离为：" + api9.getLogicalDistance(c, app91, app92));
        } else {
          System.out.println("这两个APP有不存在的");
          logging.doLog("这两个APP有不存在的");
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
        System.out.println("1.读取数据文件，生成多轨道结构");
        System.out.println("2.可视化轨道结构");
        System.out.println("3.增加新轨道");
        System.out.println("4.向特定轨道上增加物体");
        System.out.println("5.删除整条轨道");
        System.out.println("6.从特定轨道上删除物体");
        System.out.println("7.计算多轨道系统中各轨道上物体分布的熵值");
        System.out.println("8.获取两个时间段的轨道系统结构差异");
        System.out.println("9.计算两个 App 之间的逻辑距离");
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
            Pattern pattern4 = Pattern.compile("<message>向编号为");
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
            Pattern pattern6 = Pattern.compile("<message>删除的app");
            Matcher matcher6 = pattern6.matcher(line);
            if (matcher6.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 7) {
          for (String line : content1) {
            Pattern pattern7 = Pattern.compile("<message>计算时间段为");
            Matcher matcher7 = pattern7.matcher(line);
            if (matcher7.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 8) {
          for (String line : content1) {
            Pattern pattern8 = Pattern.compile("<message>比较时间段为");
            Matcher matcher8 = pattern8.matcher(line);
            if (matcher8.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 9) {
          for (String line : content1) {
            Pattern pattern9 = Pattern.compile("<message>计算逻辑距离");
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
        break;
      default:
        System.out.print("输入错误，请重新输入");
        logging.doLog("输入错误请重新输入");
      }
      if (end)
        break;
    }
    legal(c);
    return true;
  }
}
