package circularorbit;

import centralobject.Person;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.App;
import physicalobject.PhysicalObjectFactory;
import physicalobject.PhysicalPersonalAppEcosystemFactory;
import track.Track;
import track.TrackFactory;
import track.TrackFactoryPersonalAppEcosystem;

public class PersonalAppEcosystem<CentralObject, PhysicalObject>
    extends ConcreteCircularOrbit<CentralObject, PhysicalObject> {

  // AF
  // 除了ConcreteCircularOrbit中的
  // 包含APP的安装inStall和卸载upStall信息、APP的使用时间和日期usage、
  // 生成模型的范围period、app之间的关系relation和所有涉及到的APP allObjects
  // 符合要求的时间内存在于轨道上的app exit
  // 在规定要求时间内使用的app及对应的时间 intimacy
  // RI
  // 更靠近中心点用户的轨道上的app的亲密度应该比外面的轨道大（在PersonalAppEcosystemAPI中通过legal()方法处理）
  // 如果某个应用在某个时间段内被安装或被卸 载，那么它应该出现在该时间段的轨道系统中；
  // 如果某个应用在某个时 间段之前已被卸载，则不应出现在该时间段的轨道系统中；
  // 如果某个应 用在某个时间点被安装且后续没有被卸载，那么它就应始终出现在后续 各个时间段的轨道系统中（在exception中已经处理）
  // 都不为空（checkrep检查）
  // Safety from rep exposure:
  // all the fields are private
  // period是不可变的，其他是可变的，做防御式拷贝
  private CentralObject centre;

  // 设置10条轨道，没有在规定期间内使用的app在最外层轨道上，使用的app分布在1-9的轨道上

  private Map<String, List<Date>> inStall = new HashMap<>();
  private Map<String, List<Date>> upStall = new HashMap<>();

  private Map<String, Map<Date, Integer>> usage = new HashMap<>();

  private Map<String, List<String>> relation = new HashMap<>();

  private String period;

  private List<PhysicalObject> allObjects = new ArrayList<>();

  private Map<String, Date> exit = new HashMap<>();// 符合要求的时间内存在于轨道上的app
  // 自定义亲密度=使用时间+使用次数*5
  private Map<String, Integer> intimacy = new HashMap<>();// 在规定要求时间内使用的app及对应的时间


  public void checkRep() {
    assert inStall != null;
    assert upStall != null;
    assert usage != null;
    assert relation != null;
    assert allObjects != null;
  }

  /**
   * 创建中心物体也就是人，初始化人的名字
   * 
   * @param username 中心物体（人）的名字
   * @return CentralObject 人
   */
  @SuppressWarnings("unchecked")
  private CentralObject createCentralObject(String username) {
    CentralObject c = (CentralObject) new Person(username);
    centerObject = c;
    return c;
  }

  /**
   * 创建轨道物体也就是APP
   * 
   * @param appname APP的名字
   * @param company APP公司的名字
   * @param version APP的版本
   * @param function APP的功能
   * @param field APP适合的领域
   * @return
   */
  @SuppressWarnings("unchecked")
  public PhysicalObject createPhysicalObject(String appname, String company, String version,
      String function, String field) {
    PhysicalObjectFactory factory = new PhysicalPersonalAppEcosystemFactory();
    return (PhysicalObject) factory.createPhysicalObject4(appname, company, version, function,
        field);
  }

  @Override
  public void addTrack(int n, int r) {
    // TODO Auto-generated method stub
    super.addTrack(n, r);
    TrackFactory factory = new TrackFactoryPersonalAppEcosystem();
    Track track = factory.createTrack(n, r);
    /*
     * for(Track each:circles.keySet()) { if(each.getR()==r) return; } circles.put(track, new
     * ArrayList<>());
     */
  }

  /**
   * 读取文件内容
   * 
   * @param filename 文件的地址形成的字符串
   * @throws SameLabelException app的名字重复
   * @throws DependIncorrectException 没有使用App定义就在UsageLog、 InstallLog、UninstallLog、Relation 中使用 App
   *         的标签 防御策略：assert判断字符串不能为空
   */
  @Override
  public void readFile(String filename) throws SameLabelException, DependIncorrectException {
    assert filename != null : "文件名字为空";
    List<String> content = new ArrayList<>();
    try {
      FileReader fr = new FileReader(filename);
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
    Pattern pattern1 = Pattern.compile("(User ::= )([A-Za-z0-9]+)");
    Pattern pattern2 = Pattern.compile(
        "(App ::= <)([A-Za-z0-9]+),([A-Za-z0-9]+),([A-Za-z0-9.-_]+),\"([A-Za-z0-9 ]+)\",\"([A-Za-z0-9 ]+)\"");
    Pattern pattern3 = Pattern.compile(
        "(InstallLog ::= <)([0-9]{4}-[0-9]{2}-[0-9]{2}),([0-9]{2}:[0-9]{2}:[0-9]{2}),([A-Za-z0-9]+)");
    Pattern pattern4 = Pattern.compile(
        "(UsageLog ::= <)([0-9]{4}-[0-9]{2}-[0-9]{2}),([0-9]{2}:[0-9]{2}:[0-9]{2}),([A-Za-z0-9]+),([1-9][0-9]*)");
    Pattern pattern5 = Pattern.compile(
        "(UninstallLog ::= <)([0-9]{4}-[0-9]{2}-[0-9]{2}),([0-9]{2}:[0-9]{2}:[0-9]{2}),([A-Za-z0-9]+)");
    Pattern pattern6 = Pattern.compile("(Relation ::= <)([A-Za-z0-9]+),([A-Za-z0-9]+)");
    Pattern pattern7 = Pattern.compile("Period ::= ");
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    for (String each : content) {
      Matcher matcher1 = pattern1.matcher(each);
      Matcher matcher2 = pattern2.matcher(each);
      Matcher matcher3 = pattern3.matcher(each);
      Matcher matcher4 = pattern4.matcher(each);
      Matcher matcher5 = pattern5.matcher(each);
      Matcher matcher6 = pattern6.matcher(each);
      Matcher matcher7 = pattern7.matcher(each);
      if (matcher1.find()) {
        String username = matcher1.group(2);
        centre = createCentralObject(username);
      } else if (matcher2.find()) {
        String appname = matcher2.group(2);
        for (PhysicalObject each1 : allObjects) {
          String nameString = ((physicalobject.PhysicalObject) each1).getName();
          if (nameString.equals(appname)) {
            System.out.println("APP的名字" + appname + "重复");
            throw new SameLabelException();

          }
        }
        String company = matcher2.group(3);
        String version = matcher2.group(4);
        String function = matcher2.group(5);
        String field = matcher2.group(6);
        PhysicalObject app = createPhysicalObject(appname, company, version, function, field);
        allObjects.add(app);
      } else if (matcher3.find()) {
        String date = matcher3.group(2);
        String time = matcher3.group(3);
        String appname = matcher3.group(4);
        int flag = 0;
        for (String app : inStall.keySet()) {
          if (app.equals(appname)) {
            flag = 1;
            break;
          }
        }
        if (flag == 0) {
          inStall.put(appname, new ArrayList<>());
        }
        Date date1 = null;
        try {
          date1 = sf.parse(date + " " + time);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        inStall.get(appname).add(date1);
      } else if (matcher4.find()) {
        String date = matcher4.group(2);
        String time = matcher4.group(3);
        String appname = matcher4.group(4);
        int duration = Integer.valueOf(matcher4.group(5)).intValue();
        int flag = 0;
        for (String app : usage.keySet()) {
          if (app.equals(appname)) {
            flag = 1;
            break;
          }
        }
        if (flag == 0) {
          usage.put(appname, new HashMap<>());
        }
        Date date2 = null;
        try {
          date2 = sf.parse(date + " " + time);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        usage.get(appname).put(date2, duration);
      } else if (matcher5.find()) {
        String date = matcher5.group(2);
        String time = matcher5.group(3);
        String appname = matcher5.group(4);
        int flag = 0;
        for (String app : upStall.keySet()) {
          if (app.equals(appname)) {
            flag = 1;
            break;
          }
        }
        if (flag == 0) {
          upStall.put(appname, new ArrayList<>());
        }
        Date date3 = null;
        try {
          date3 = sf.parse(date + " " + time);
        } catch (ParseException e) {
          e.printStackTrace();
        }
        upStall.get(appname).add(date3);
      } else if (matcher6.find()) {
        String appname1 = matcher6.group(2);
        String appname2 = matcher6.group(3);
        if (appname1.equals(appname2)) {
          System.out.println("出现自己与自己关联的app：" + appname1);
          throw new SameLabelException();
        }
        int flag1 = 0;
        for (String app : relation.keySet()) {
          if (app.equals(appname1))
            flag1 = 1;
          // if(app.equals(appname2))
          // flag2=1;
        }
        if (flag1 == 0)
          relation.put(appname1, new ArrayList<>());
        if (!relation.get(appname1).contains(appname2)) {
          relation.get(appname1).add(appname2);
        }
        /*
         * if(flag2==0) relation.put(appname2, new ArrayList<>());
         * if(!relation.get(appname2).contains(appname1)) { relation.get(appname2).add(appname1); }
         */
      } else if (matcher7.find()) {
        period = each.substring(11);
      }
    }
    testDependenceCorrect();
    initialModel();
    checkRep();
  }

  private void testDependenceCorrect() throws DependIncorrectException {
    for (String s1 : inStall.keySet()) {
      boolean flag = false;
      for (PhysicalObject p : allObjects) {
        App p1 = (App) p;
        if (p1.getName().equals(s1)) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        System.out.println("\n没有使用App定义就在 InstallLog中使用 App:" + s1 + " 的标签");
        throw new DependIncorrectException();

      }
    }
    for (String s1 : upStall.keySet()) {
      boolean flag = false;
      for (PhysicalObject p : allObjects) {
        App p1 = (App) p;
        if (p1.getName().equals(s1)) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        System.out.println("\n没有使用App定义就在UninstallLog中使用 App :" + s1 + " 的标签");
        throw new DependIncorrectException();

      }
    }
    for (String s1 : usage.keySet()) {
      boolean flag = false;
      for (PhysicalObject p : allObjects) {
        App p1 = (App) p;
        if (p1.getName().equals(s1)) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        System.out.println("\n没有使用App定义就在UsageLog中使用 App" + s1 + " 的标签");
        throw new DependIncorrectException();

      }
    }
    for (String s1 : relation.keySet()) {
      boolean flag = false;
      for (PhysicalObject p : allObjects) {
        App p1 = (App) p;
        if (p1.getName().equals(s1)) {
          flag = true;
          break;
        }
      }
      if (!flag) {
        System.out.println("\n没有使用App定义就在Relation中使用 App" + s1 + " 的标签");
        throw new DependIncorrectException();

      }
      for (String s2 : relation.get(s1)) {
        boolean flag0 = false;
        for (PhysicalObject p : allObjects) {
          App p1 = (App) p;
          if (p1.getName().equals(s2)) {
            flag0 = true;
            break;
          }
        }
        if (!flag0) {
          System.out.println("\n没有使用App定义就在Relation中使用 App" + s2 + " 的标签");
          throw new DependIncorrectException();

        }
      }
    }

  }

  /**
   * 根据读入的数据创建轨道，并且建立APP之间的联系
   */
  @SuppressWarnings("unchecked")
  private void initialModel() {
    addCenterObject(centre);
    for (int i = 1; i <= 10; i++) {// 自定义10条轨道
      addTrack(i, i);
    }
    for (String app1 : relation.keySet()) {
      for (String app2 : relation.get(app1)) {
        int flag1 = 0, flag2 = 0;
        App newapp1 = null, newapp2 = null;
        for (PhysicalObject each : allObjects) {
          newapp1 = (App) each;
          if (app1.equals(newapp1.getAppName())) {
            flag1 = 1;
            break;
          }
        }
        for (PhysicalObject each : allObjects) {
          newapp2 = (App) each;
          if (app2.equals(newapp2.getAppName())) {
            flag2 = 1;
            break;
          }
        }
        if (flag1 == 1 && flag2 == 1) {
          addTwoObjectsRelation((PhysicalObject) newapp1, (PhysicalObject) newapp2);// 无向图，没有箭头
          addTwoObjectsRelation((PhysicalObject) newapp2, (PhysicalObject) newapp1);
        }
      }
    }

  }

  /**
   * 根据输入的时间段将APP放在对应的轨道上
   * 
   * @param exact a string 输入的要截取的时间段 防御策略：根据period判断exact输入的截取时间的格式是否正确
   */
  public void putPhysicalObjectOnCircles(String exact) {
    assert exact != null;
    // Pattern pattern=Pattern.compile("([0-9]{4})-([0-9]{2})-([0-9]{2})
    // ([0-9]{2}):([0-9]{2}):([0-9]{2})");
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    if (period.equals("Month")) {// exact:2019-03
      Date standard = null, start = null;;
      Pattern pattern = Pattern.compile("[0-9]{4}-([0-9]{2})");
      Matcher matcher = pattern.matcher(exact);
      int month = 0;
      if (matcher.find()) {
        month = Integer.valueOf(matcher.group(1)).intValue() + 1;
      } else {
        System.out.println("Month为截取时间周期，截取时间输入错误");
        return;
      }
      try {
        standard = sf.parse("2019-" + month + "-01 00:00:00");
        start = sf.parse("2019-" + Integer.valueOf(matcher.group(1)).intValue() + "-01 00:00:00");
      } catch (NumberFormatException | ParseException e) {
        e.printStackTrace();
      }
      if (standard != null && start != null) {
        updateExitIntimacy(start, standard);
      }
      layout();
    } else if (period.equals("Week")) {// exact:2019-01-01 获得此日期所在的周的模型（周一到周日为一周）
      Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
      Matcher matcher = pattern.matcher(exact);
      assert matcher.find() : "Week为截取时间周期，截取时间输入错误";
      Calendar cal = Calendar.getInstance();
      try {
        cal.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(exact));
      } catch (ParseException e) {
        e.printStackTrace();
      }
      int d = 0;
      if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
        d = -6;
      } else {
        d = 2 - cal.get(Calendar.DAY_OF_WEEK);
      }
      cal.add(Calendar.DAY_OF_WEEK, d);
      // 所在周开始日期
      String data1 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
      cal.add(Calendar.DAY_OF_WEEK, 6);
      // 所在周结束日期
      String data2 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
      Date startDate = null, endDate = null;
      try {
        startDate = sf.parse(data1 + " 00:00:00");
        endDate = sf.parse(data2 + " 23:59:59");
      } catch (ParseException e) {
        e.printStackTrace();
      }
      if (startDate != null && endDate != null) {
        updateExitIntimacy(startDate, endDate);
      }
      layout();
    } else if (period.equals("Hour")) {// exact:2019-01-01 15-16
      Pattern pattern = Pattern.compile("([0-9]{4}-[0-9]{2}-[0-9]{2}) ([0-9]{2})-([0-9]{2})");
      Matcher matcher = pattern.matcher(exact);
      int startHour = 0, endHour = 0;
      if (matcher.find()) {
        startHour = Integer.valueOf(matcher.group(2)).intValue() - 1;
        endHour = Integer.valueOf(matcher.group(3)).intValue();
      } else {
        System.out.println("Hour为截取时间周期，截取时间输入错误");
        return;
      }
      Date startDate = null, endDate = null;
      try {
        startDate = sf.parse(matcher.group(1) + " " + startHour + ":59:59");
        endDate = sf.parse(matcher.group(1) + " " + endHour + ":00:00");
      } catch (ParseException e) {
        e.printStackTrace();
      }
      if (startDate != null && endDate != null) {
        updateExitIntimacy(startDate, endDate);
      }
      layout();
    } else if (period.equals("Day")) {// exact:2019-01-01
      Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
      Matcher matcher = pattern.matcher(exact);
      boolean yy = matcher.find();
      assert yy : "Day为截取时间周期，截取时间输入错误";
      Date startDate = null, endDate = null;
      try {
        startDate = sf.parse(exact + " 00:00:00");
        endDate = sf.parse(exact + " 23:59:59");
      } catch (ParseException e) {
        e.printStackTrace();
      }
      if (startDate != null && endDate != null) {
        updateExitIntimacy(startDate, endDate);
      }
      layout();
    }
    checkRep();
  }

  /**
   * 确定要截取时间内存在的APP
   * 
   * @param start Date 时间段开始的时间
   * @param end Date 时间段结束的时间 防御策略：用assert判断pre-conditions的正确
   */
  private void updateExitIntimacy(Date start, Date end) {
    assert start.before(end) : "时间段的开始时间没有早于结束时间";
    for (String app : inStall.keySet()) {
      for (Date d : inStall.get(app)) {
        if (d.before(end)) {// d的操作时间在提取模型的时间之前
          if (exit.containsKey(app)) {
            Date newdate = exit.get(app);
            if (d.after(newdate)) {// d是最新一次安装时间
              exit.put(app, d);
            }
          } else {
            exit.put(app, d);
          }
        }
      }
    }
    for (String app : upStall.keySet()) {
      for (Date d : upStall.get(app)) {
        if (d.before(end)) {
          for (Entry<String, Date> entry : exit.entrySet()) {
            if (entry.getKey().equals(app) && entry.getValue().before(d)) {// 最新的安装时间在卸载之前
              exit.remove(app);
              break;
            }
          }
        }
      }
    }
    for (String app : usage.keySet()) {
      if (exit.containsKey(app)) {
        for (Entry<Date, Integer> entry : usage.get(app).entrySet()) {
          if (entry.getKey().before(end) && entry.getKey().after(start)) {// 在规定的月内
            if (intimacy.containsKey(app)) {
              intimacy.put(app, intimacy.get(app) + entry.getValue() + 5);
            } else {
              intimacy.put(app, entry.getValue() + 5);
            }
          }
        }
      }
    }
  }

  /**
   * 根据亲密度值域设置轨道上物体分布
   */
  private void layout() {
    int min = 0, max = 0, i = -1;
    for (Entry<String, Integer> entry : intimacy.entrySet()) {
      i++;
      if (i == 0) {
        min = entry.getValue();
        max = min;
      }
      if (entry.getValue() > max) {
        max = entry.getValue();
      }
      if (entry.getValue() < min) {
        min = entry.getValue();
      }
    }
    double dur = (max - min) / 9.0;
    for (Entry<String, Integer> entry : intimacy.entrySet()) {
      int f = 0;
      for (int j = 1; j <= 9; j++) {
        if (j < 9 && entry.getValue() >= min + (j - 1) * dur && entry.getValue() < min + j * dur
            || j == 9 && entry.getValue() >= min + 8 * dur && entry.getValue() <= max) {
          int k = 10 - j;
          for (PhysicalObject app : allObjects) {
            App app1 = (App) app;
            if (app1.getAppName().equals(entry.getKey())) {
              addTrackObject(k, k, app);
              f = 1;
              break;
            }
          }
        }
        if (f == 1) {
          break;
        }
      }
    }
    for (String unused : exit.keySet()) {
      if (!intimacy.containsKey(unused)) {
        for (PhysicalObject app : allObjects) {
          App app1 = (App) app;
          if (app1.getAppName().equals(unused)) {
            addTrackObject(10, 10, app);
            break;
          }
        }
      }
    }
  }

  /**
   * 获取中心物体，即人
   * 
   * @return CentralObject 中心物体
   */
  /*
   * public CentralObject getCentre() { return centre; }
   */

  /*
   * public Map<String, List<Date>> getInStall() { return inStall; }
   * 
   * public Map<String, List<Date>> getUpStall() { return upStall; }
   * 
   * public Map<String, Map<Date, Integer>> getUsage() { return usage; }
   */

  /**
   * 获取App之间的关系
   * 
   * @return a map,the key includes APP的名字，the value includes 与key'APP有联系的APP
   */
  public Map<String, List<String>> getRelation() {
    return relation;
  }

  /**
   * 获取这个轨道模型所截取的时间段类型
   * 
   * @return a string 代表截取时间段的类型
   */
  public String getPeriod() {
    return period;
  }

  /**
   * 获取涉及的所有的APP的信息
   * 
   * @return list includes all App information
   */
  public List<PhysicalObject> getAllObjects() {
    return new ArrayList<>(allObjects);
  }
  /*
   * public Map<String, Date> getExit() { return exit; }
   */

  public Map<String, Integer> getIntimacy() {
    return intimacy;
  }



}
