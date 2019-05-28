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
   * �˵�
   */
  private static void menu() {
    System.out.println("-----------------------------");
    System.out.println("1.��ȡ�����ļ������ɶ����ṹ");
    System.out.println("2.���ӻ�����ṹ");
    System.out.println("3.�����¹��");
    System.out.println("4.���ض��������������");
    System.out.println("5.ɾ���������");
    System.out.println("6.���ض������ɾ������");
    System.out.println("7.�������ϵͳ�и����������ֲ�����ֵ");
    System.out.println("8.��ȡ����ʱ��εĹ��ϵͳ�ṹ����");
    System.out.println("9.�������� App ֮����߼�����");
    System.out.println("10.����ʱ��β�ѯ��־");
    System.out.println("11.���ղ������Ͳ�ѯ��־");
    System.out.println("12.��ѯ��־�г��ֵ��쳣");
    System.out.println("13.����");
    System.out.println("���������ѡ�������");
  }

  /**
   * �ж϶���ϵͳ�ĺϷ��ԣ��жϰ�װж�ص�APP�Ƿ���ȷ���жϹ����APP�����ܶ��Ƿ���ȷ��
   * 
   * @param c ����ϵͳ
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
            System.out.println("���ڿ��û�������APP�����ܶ�С�ڿ��û���Զ��APP�����ܶ�");
            logging.doLog("���ڿ��û�������APP�����ܶ�С�ڿ��û���Զ��APP�����ܶ�");
          }
        }
      }
      for (Integer integer : eachTrack) {
        inside.add(integer);
      }
    }
  }

  /**
   * ѡ��˵��еĹ���ִ��
   * 
   * @return true ��ȡ���ļ��������﷨���� false ��ȡ���ļ������﷨����
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
        System.out.print("�������ļ����ƣ�");
        nameString = in.next();
        logging.doLog("��ȡ�ļ���" + nameString + ".txt");
        try {
          PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = (PersonalAppEcosystem<CentralObject, PhysicalObject>) c;
          p1.readFile("src/txt/" + nameString + ".txt");
        } catch (SameLabelException | DependIncorrectException e) {
          logging.logException(e);
          e.printStackTrace();
          System.out.println("��ȡ�ļ��﷨����һ���������ѡ���ļ�");
          logging.doLog("��ȡ�ļ��﷨����һ���������ѡ���ļ�");
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
        System.out.print("������Ҫ���ӻ���ʱ���");
        String timeString = in.next();
        logging.doLog("���ӻ�" + timeString + "��ʱ���");
        s.putPhysicalObjectOnCircles(timeString);
        CircularOrbitHelper.visualize(s);
        break;
      case 3:
        System.out.print("������Ҫ���ӹ���ı�ţ�");
        int n3 = in.nextInt();
        System.out.print("������Ҫ���ӹ���İ뾶��");
        int r3 = in.nextInt();
        logging.doLog("��ӹ���ı��Ϊ" + n3 + "�뾶Ϊ" + r3);
        c.addTrack(n3, r3);
        break;
      case 4:
        System.out.print("������Ŀ�����ı�ţ�");
        int n4 = in.nextInt();
        System.out.print("������Ŀ�����İ뾶��");
        int r4 = in.nextInt();
        System.out.print("������Ҫ���APP�����֣�");
        String appname4 = in.next();
        System.out.print("������APP�Ĺ�˾��");
        String company4 = in.next();
        System.out.print("������APP�İ汾��");
        String version4 = in.next();
        System.out.print("������APP�Ĺ��ܣ�");
        String function4 = in.next();
        System.out.print("������APP����������");
        String field4 = in.next();
        logging.doLog("����Ϊ" + n4 + "�뾶Ϊ" + r4 + "�Ĺ�����������Ϊ" + appname4 + "��APP��˾" + company4 + "�汾"
            + version4 + "����" + function4 + "��������" + field4);
        PersonalAppEcosystem<CentralObject, PhysicalObject> personalAppEcosystem4 = (PersonalAppEcosystem<CentralObject, PhysicalObject>) c;
        c.addTrackObject(n4, r4, personalAppEcosystem4.createPhysicalObject(appname4, company4,
            version4, function4, field4));
        break;
      case 5:
        System.out.print("������Ҫɾ������İ뾶��");
        int r5 = in.nextInt();
        logging.doLog("ɾ���뾶Ϊ" + r5 + "�Ĺ��");
        c.removeTrack(r5);
        break;
      case 6:
        System.out.print("������Ҫɾ��APP���ڹ���İ뾶��");
        int r6 = in.nextInt();
        System.out.print("������Ҫɾ��APP�����֣�");
        String appname6 = in.next();
        logging.doLog("ɾ����app" + appname6 + "�ڰ뾶Ϊ" + r6 + "�Ĺ���ϵ�");
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
        System.out.print("������Ҫ������ֵ��ʱ���");
        String timeString7 = in.next();
        l.putPhysicalObjectOnCircles(timeString7);
        System.out.print("����ֲ�����ֵ��" + api7.getObjectDistributionEntropy(l));
        logging.doLog("����ʱ���Ϊ" + timeString7 + "app�ֲ���ֵ");
        break;
      case 8:
        CircularOrbitAPIs api8 = new CircularOrbitAPIs();
        PersonalAppEcosystem<CentralObject, PhysicalObject> t81 = new PersonalAppEcosystem<>();
        PersonalAppEcosystem<CentralObject, PhysicalObject> t82 = new PersonalAppEcosystem<>();
        System.out.print("������Ҫ�Ƚϲ���ĵ�һ��ʱ��Σ�");
        String time81 = in.next();
        System.out.print("������Ҫ�Ƚϲ���ĵڶ���ʱ��Σ�");
        String time82 = in.next();
        logging.doLog("�Ƚ�ʱ���Ϊ" + time81 + "��ʱ���Ϊ" + time82 + "��������ϵͳ�Ĳ���");
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
        System.out.println("���������" + diff.getTrackNums());
        Map<Integer, Integer> eachNumsMap = diff.getTrackNumberAndObjectsDif();
        Map<Integer, String> eachNameMap = diff.getTrackNumberAndName();
        for (int i = 1; i <= eachNameMap.size(); i++) {
          System.out
              .println("���" + i + "�������������죺" + eachNumsMap.get(i) + "  ������죺" + eachNameMap.get(i));
        }
        break;
      case 9:
        CircularOrbitAPIs api9 = new CircularOrbitAPIs();
        System.out.print("�������һ��APP�����֣�");
        String appname91 = in.next();
        System.out.print("������ڶ���APP�����֣�");
        String appname92 = in.next();
        logging.doLog("�����߼�����" + appname91 + "��" + appname92);
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
          System.out.println("����App���߼�����Ϊ��" + api9.getLogicalDistance(c, app91, app92));
        } else {
          System.out.println("������APP�в����ڵ�");
          logging.doLog("������APP�в����ڵ�");
        }
        break;
      case 10:
        List<String> content = new ArrayList<>();
        try {
          FileReader fr = new FileReader(logging.getFileName());
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
        String startTime = null, finishTime = null;
        Matcher matcherStart, matcherFinish;
        while (true) {
          System.out.println("������Ҫ��ѯ����ʼʱ��(����2019-01-01T00:00:00)");
          startTime = in.next();
          Pattern patternStart = Pattern
              .compile("([0-9]{4}-[0-9]{2}-[0-9]{2})T([0-9]{2}:[0-9]{2}:[0-9]{2})");
          matcherStart = patternStart.matcher(startTime);
          if (matcherStart.find())
            break;
          else {
            System.out.println("�����ʽ��������������");
          }
        }
        while (true) {
          System.out.println("������Ҫ��ѯ�Ľ���ʱ��(����2019-01-01T00:00:00),���������ڿ�ʼ��ʱ��");
          finishTime = in.next();
          Pattern patternFinish = Pattern
              .compile("([0-9]{4}-[0-9]{2}-[0-9]{2})T([0-9]{2}:[0-9]{2}:[0-9]{2})");
          matcherFinish = patternFinish.matcher(finishTime);
          if (matcherFinish.find())
            break;
          else {
            System.out.println("�����ʽ��������������");
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
          System.out.println("��ʱ���û��ƥ��Ĳ���");
        break;
      case 11:
        List<String> content1 = new ArrayList<>();
        try {
          FileReader fr = new FileReader(logging.getFileName());
          BufferedReader bf = new BufferedReader(fr);
          String str; // ���ж�ȡ�ַ���
          while ((str = bf.readLine()) != null) {
            content1.add(str);
          }
          bf.close();
          fr.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
        System.out.println("1.��ȡ�����ļ������ɶ����ṹ");
        System.out.println("2.���ӻ�����ṹ");
        System.out.println("3.�����¹��");
        System.out.println("4.���ض��������������");
        System.out.println("5.ɾ���������");
        System.out.println("6.���ض������ɾ������");
        System.out.println("7.�������ϵͳ�и����������ֲ�����ֵ");
        System.out.println("8.��ȡ����ʱ��εĹ��ϵͳ�ṹ����");
        System.out.println("9.�������� App ֮����߼�����");
        System.out.println("������Ҫ��ѯ�Ĳ��������");
        int o = in.nextInt();
        if (o == 1) {
          for (String line : content1) {
            Pattern pattern1 = Pattern.compile("<message>��ȡ�ļ�");
            Matcher matcher1 = pattern1.matcher(line);
            if (matcher1.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 2) {
          for (String line : content1) {
            Pattern pattern2 = Pattern.compile("<message>����");
            Matcher matcher2 = pattern2.matcher(line);
            if (matcher2.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 3) {
          for (String line : content1) {
            Pattern pattern3 = Pattern.compile("<message>��ӹ���ı��Ϊ");
            Matcher matcher3 = pattern3.matcher(line);
            if (matcher3.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 4) {
          for (String line : content1) {
            Pattern pattern4 = Pattern.compile("<message>����Ϊ");
            Matcher matcher4 = pattern4.matcher(line);
            if (matcher4.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 5) {
          for (String line : content1) {
            Pattern pattern5 = Pattern.compile("<message>ɾ���뾶Ϊ");
            Matcher matcher5 = pattern5.matcher(line);
            if (matcher5.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 6) {
          for (String line : content1) {
            Pattern pattern6 = Pattern.compile("<message>ɾ����app");
            Matcher matcher6 = pattern6.matcher(line);
            if (matcher6.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 7) {
          for (String line : content1) {
            Pattern pattern7 = Pattern.compile("<message>����ʱ���Ϊ");
            Matcher matcher7 = pattern7.matcher(line);
            if (matcher7.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 8) {
          for (String line : content1) {
            Pattern pattern8 = Pattern.compile("<message>�Ƚ�ʱ���Ϊ");
            Matcher matcher8 = pattern8.matcher(line);
            if (matcher8.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 9) {
          for (String line : content1) {
            Pattern pattern9 = Pattern.compile("<message>�����߼�����");
            Matcher matcher9 = pattern9.matcher(line);
            if (matcher9.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else {
          System.out.println("������Ŵ���");
        }
        break;
      case 12:
        List<String> content2 = new ArrayList<>();
        try {
          FileReader fr = new FileReader(logging.getFileName());
          BufferedReader bf = new BufferedReader(fr);
          String str; // ���ж�ȡ�ַ���
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
            System.out.println("�����ļ��и�Ԫ��֮���������ϵ���쳣");
          }
          if (matcher2.find()) {
            System.out.println("�����ļ��б�ǩ��ȫһ����Ԫ�ص��쳣");
          }
          if (matcher3.find()) {
            System.out.println("�����ļ��������ļ��д��ڲ������﷨����������쳣");
          }
        }
        break;
      case 13:
        end = true;
        break;
      default:
        System.out.print("�����������������");
        logging.doLog("�����������������");
      }
      if (end)
        break;
    }
    legal(c);
    return true;
  }
}
