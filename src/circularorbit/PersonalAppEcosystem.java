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
  // ����ConcreteCircularOrbit�е�
  // ����APP�İ�װinStall��ж��upStall��Ϣ��APP��ʹ��ʱ�������usage��
  // ����ģ�͵ķ�Χperiod��app֮��Ĺ�ϵrelation�������漰����APP allObjects
  // ����Ҫ���ʱ���ڴ����ڹ���ϵ�app exit
  // �ڹ涨Ҫ��ʱ����ʹ�õ�app����Ӧ��ʱ�� intimacy
  // RI
  // ���������ĵ��û��Ĺ���ϵ�app�����ܶ�Ӧ�ñ�����Ĺ������PersonalAppEcosystemAPI��ͨ��legal()��������
  // ���ĳ��Ӧ����ĳ��ʱ����ڱ���װ��ж �أ���ô��Ӧ�ó����ڸ�ʱ��εĹ��ϵͳ�У�
  // ���ĳ��Ӧ����ĳ��ʱ ���֮ǰ�ѱ�ж�أ���Ӧ�����ڸ�ʱ��εĹ��ϵͳ�У�
  // ���ĳ��Ӧ ����ĳ��ʱ��㱻��װ�Һ���û�б�ж�أ���ô����Ӧʼ�ճ����ں��� ����ʱ��εĹ��ϵͳ�У���exception���Ѿ�����
  // ����Ϊ�գ�checkrep��飩
  // Safety from rep exposure:
  // all the fields are private
  // period�ǲ��ɱ�ģ������ǿɱ�ģ�������ʽ����
  private CentralObject centre;

  // ����10�������û���ڹ涨�ڼ���ʹ�õ�app����������ϣ�ʹ�õ�app�ֲ���1-9�Ĺ����

  private Map<String, List<Date>> inStall = new HashMap<>();
  private Map<String, List<Date>> upStall = new HashMap<>();

  private Map<String, Map<Date, Integer>> usage = new HashMap<>();

  private Map<String, List<String>> relation = new HashMap<>();

  private String period;

  private List<PhysicalObject> allObjects = new ArrayList<>();

  private Map<String, Date> exit = new HashMap<>();// ����Ҫ���ʱ���ڴ����ڹ���ϵ�app
  // �Զ������ܶ�=ʹ��ʱ��+ʹ�ô���*5
  private Map<String, Integer> intimacy = new HashMap<>();// �ڹ涨Ҫ��ʱ����ʹ�õ�app����Ӧ��ʱ��


  public void checkRep() {
    assert inStall != null;
    assert upStall != null;
    assert usage != null;
    assert relation != null;
    assert allObjects != null;
  }

  /**
   * ������������Ҳ�����ˣ���ʼ���˵�����
   * 
   * @param username �������壨�ˣ�������
   * @return CentralObject ��
   */
  @SuppressWarnings("unchecked")
  private CentralObject createCentralObject(String username) {
    CentralObject c = (CentralObject) new Person(username);
    centerObject = c;
    return c;
  }

  /**
   * �����������Ҳ����APP
   * 
   * @param appname APP������
   * @param company APP��˾������
   * @param version APP�İ汾
   * @param function APP�Ĺ���
   * @param field APP�ʺϵ�����
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
   * ��ȡ�ļ�����
   * 
   * @param filename �ļ��ĵ�ַ�γɵ��ַ���
   * @throws SameLabelException app�������ظ�
   * @throws DependIncorrectException û��ʹ��App�������UsageLog�� InstallLog��UninstallLog��Relation ��ʹ�� App
   *         �ı�ǩ �������ԣ�assert�ж��ַ�������Ϊ��
   */
  @Override
  public void readFile(String filename) throws SameLabelException, DependIncorrectException {
    assert filename != null : "�ļ�����Ϊ��";
    List<String> content = new ArrayList<>();
    try {
      FileReader fr = new FileReader(filename);
      BufferedReader bf = new BufferedReader(fr);
      String str; // ���ж�ȡ�ַ���
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
            System.out.println("APP������" + appname + "�ظ�");
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
          System.out.println("�����Լ����Լ�������app��" + appname1);
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
        System.out.println("\nû��ʹ��App������� InstallLog��ʹ�� App:" + s1 + " �ı�ǩ");
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
        System.out.println("\nû��ʹ��App�������UninstallLog��ʹ�� App :" + s1 + " �ı�ǩ");
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
        System.out.println("\nû��ʹ��App�������UsageLog��ʹ�� App" + s1 + " �ı�ǩ");
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
        System.out.println("\nû��ʹ��App�������Relation��ʹ�� App" + s1 + " �ı�ǩ");
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
          System.out.println("\nû��ʹ��App�������Relation��ʹ�� App" + s2 + " �ı�ǩ");
          throw new DependIncorrectException();

        }
      }
    }

  }

  /**
   * ���ݶ�������ݴ�����������ҽ���APP֮�����ϵ
   */
  @SuppressWarnings("unchecked")
  private void initialModel() {
    addCenterObject(centre);
    for (int i = 1; i <= 10; i++) {// �Զ���10�����
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
          addTwoObjectsRelation((PhysicalObject) newapp1, (PhysicalObject) newapp2);// ����ͼ��û�м�ͷ
          addTwoObjectsRelation((PhysicalObject) newapp2, (PhysicalObject) newapp1);
        }
      }
    }

  }

  /**
   * ���������ʱ��ν�APP���ڶ�Ӧ�Ĺ����
   * 
   * @param exact a string �����Ҫ��ȡ��ʱ��� �������ԣ�����period�ж�exact����Ľ�ȡʱ��ĸ�ʽ�Ƿ���ȷ
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
        System.out.println("MonthΪ��ȡʱ�����ڣ���ȡʱ���������");
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
    } else if (period.equals("Week")) {// exact:2019-01-01 ��ô��������ڵ��ܵ�ģ�ͣ���һ������Ϊһ�ܣ�
      Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
      Matcher matcher = pattern.matcher(exact);
      assert matcher.find() : "WeekΪ��ȡʱ�����ڣ���ȡʱ���������";
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
      // �����ܿ�ʼ����
      String data1 = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
      cal.add(Calendar.DAY_OF_WEEK, 6);
      // �����ܽ�������
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
        System.out.println("HourΪ��ȡʱ�����ڣ���ȡʱ���������");
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
      assert yy : "DayΪ��ȡʱ�����ڣ���ȡʱ���������";
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
   * ȷ��Ҫ��ȡʱ���ڴ��ڵ�APP
   * 
   * @param start Date ʱ��ο�ʼ��ʱ��
   * @param end Date ʱ��ν�����ʱ�� �������ԣ���assert�ж�pre-conditions����ȷ
   */
  private void updateExitIntimacy(Date start, Date end) {
    assert start.before(end) : "ʱ��εĿ�ʼʱ��û�����ڽ���ʱ��";
    for (String app : inStall.keySet()) {
      for (Date d : inStall.get(app)) {
        if (d.before(end)) {// d�Ĳ���ʱ������ȡģ�͵�ʱ��֮ǰ
          if (exit.containsKey(app)) {
            Date newdate = exit.get(app);
            if (d.after(newdate)) {// d������һ�ΰ�װʱ��
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
            if (entry.getKey().equals(app) && entry.getValue().before(d)) {// ���µİ�װʱ����ж��֮ǰ
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
          if (entry.getKey().before(end) && entry.getKey().after(start)) {// �ڹ涨������
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
   * �������ܶ�ֵ�����ù��������ֲ�
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
   * ��ȡ�������壬����
   * 
   * @return CentralObject ��������
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
   * ��ȡApp֮��Ĺ�ϵ
   * 
   * @return a map,the key includes APP�����֣�the value includes ��key'APP����ϵ��APP
   */
  public Map<String, List<String>> getRelation() {
    return relation;
  }

  /**
   * ��ȡ������ģ������ȡ��ʱ�������
   * 
   * @return a string �����ȡʱ��ε�����
   */
  public String getPeriod() {
    return period;
  }

  /**
   * ��ȡ�漰�����е�APP����Ϣ
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
