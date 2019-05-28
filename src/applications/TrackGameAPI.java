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
    System.out.println("8.���ű�������");
    System.out.println("9.�ֹ���������������Ϊ����ѡ�ָ��������������飩");
    System.out.println("10.����ʱ��β�ѯ��־");
    System.out.println("11.���ղ������Ͳ�ѯ��־");
    System.out.println("12.��ѯ��־�г��ֵ��쳣");
    System.out.println("13.����");
    System.out.println("���������ѡ�������");
  }

  /**
   * �ж϶���ϵͳ�ĺϷ��ԣ��ܵ���Ϊ 4-10 ֮�䣻ÿһ����������ܳ� ���ܵ�����ÿһ���ÿ���ܵ������ 1 λ�˶�Ա ��������û���˶�Ա��������� n
   * ������������ܵ�������� 0 ���� n-1 ������������������ ������ͬһ���˶�Աֻ�ܳ�����һ������У�
   * 
   * @param makeGroup a map key������ value�Ǵ����Ӧ�Ķ���ϵͳ
   * @return true ����ϵͳ�Ϸ� false ����ϵͳ���Ϸ� �������ԣ�assert �ж�pre-condition ÿ�鶼����һ������ϵͳ
   */
  private boolean legal(Map<Integer, CircularOrbit<CentralObject, PhysicalObject>> makeGroup) {
    for (Integer kk : makeGroup.keySet()) {
      assert makeGroup.get(kk) != null;
    }
    List<String> allAth = new ArrayList<>();
    for (Integer integer : makeGroup.keySet()) {
      Map<Track, List<PhysicalObject>> cirMap = makeGroup.get(integer).getCircles();
      if (cirMap.size() < 4 || cirMap.size() > 10) {
        logging.doLog("�ܵ������� 4��10 ֮��");
        System.out.println("�ܵ������� 4-10 ֮��");
        return false;
      }
      if (integer < makeGroup.size() - 1) {// 0~n-1��
        for (Track track : cirMap.keySet()) {
          if (cirMap.get(track).size() != 1) {
            System.out.println("�� 0 ���� n-1 ����������������ܵ���");
            logging.doLog("�� 0 ���� n��1 ����������������ܵ���");
            return false;
          } else {
            if (!allAth.contains(cirMap.get(track).get(0).getName())) {
              allAth.add(cirMap.get(track).get(0).getName());
            } else {
              System.out.println("ͬһ���˶�Ա��ֹֻ������һ�������");
              logging.doLog("ͬһ���˶�Ա��ֹֻ������һ�������");
              return false;
            }
          }
        }
      } else {// n��
        for (Track track : cirMap.keySet()) {
          if (cirMap.get(track).size() != 1 && cirMap.get(track).size() != 0) {
            System.out.println("��n�������������С�ڵ����ܵ���");
            logging.doLog("��n�������������С�ڵ����ܵ���");
            return false;
          } else if (cirMap.get(track).size() == 1) {
            if (!allAth.contains(cirMap.get(track).get(0).getName())) {
              allAth.add(cirMap.get(track).get(0).getName());
            } else {
              System.out.println("ͬһ���˶�Ա��ֹֻ������һ�������");
              logging.doLog("ͬһ���˶�Ա��ֹֻ������һ�������");
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  /**
   * ѡ��˵��еĹ���ִ��
   * 
   * @return true ��ȡ���ļ��������﷨���� false ��ȡ���ļ������﷨����
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
        System.out.print("�������ļ�����:");
        nameString = in.next();
        logging.doLog("��ȡ�ļ�" + nameString + ".txt");
        try {
          TrackGame<CentralObject, PhysicalObject> t1 = (TrackGame<CentralObject, PhysicalObject>) c;
          t1.readFile("src/txt/" + nameString + ".txt");
        } catch (MetresFalseException | InformationNumIncorrectException | TransposeException
            | NotCaptialLetterException | DecimalsException | SameLabelException e) {
          logging.logException(e);
          e.printStackTrace();
          System.out.println("��ȡ�ļ��﷨����һ���������ѡ���ļ�");
          logging.doLog("��ȡ�ļ��﷨����һ���������ѡ���ļ�");
          return false;
        }
        break;
      case 2:
        System.out.print("���ӻ������У�");
        for (int i = 0; i < makeGroup.size(); i++) {
          System.out.print(i + "��  ");
        }
        System.out.print("��������ӻ�����:");
        int n2 = in.nextInt();
        logging.doLog("���ӻ���" + n2 + "��");
        CircularOrbitHelper.visualize(makeGroup.get(n2));
        break;
      case 3:
        System.out.print("������Ҫ���ӹ���ı�ţ�");
        int n3 = in.nextInt();
        System.out.print("������Ҫ���ӹ���İ뾶��");
        int r3 = in.nextInt();
        logging.doLog("��ӹ���ı��Ϊ" + n3 + "�뾶Ϊ" + r3);
        for (Integer x : makeGroup.keySet()) {
          assert makeGroup.get(x).getCircles().size() + 1 <= 10 : "��Ӻ�������10��";
          makeGroup.get(x).addTrack(n3, r3);
        }
        break;
      case 4:
        System.out.print("������Ҫ������ı�ţ�");
        int g4 = in.nextInt();
        System.out.print("������Ŀ�����ı�ţ�");
        int n4 = in.nextInt();
        System.out.print("������Ŀ�����İ뾶��");
        int r4 = in.nextInt();
        System.out.print("��������˶�Ա�����֣�");
        String name4 = in.next();
        System.out.print("��������˶�Ա�ĺ��룺");
        int num4 = in.nextInt();
        System.out.print("��������˶�Ա�Ĺ�����");
        String nation4 = in.next();
        System.out.print("��������˶�Ա�����䣺");
        int age4 = in.nextInt();
        System.out.print("��������˶�Ա����óɼ���");
        double result4 = in.nextDouble();
        logging.doLog("����˶�Ա" + name4 + "�ڵ�" + g4 + "��ı��Ϊ" + n4 + "���ܵ��Ϻ���Ϊ" + num4 + "����Ϊ"
            + nation4 + "����Ϊ" + age4 + "��óɼ�Ϊ" + result4);
        TrackGame<CentralObject, PhysicalObject> trackgame4 = (TrackGame<CentralObject, PhysicalObject>) makeGroup
            .get(g4);
        c.addTrackObject(n4, r4,
            trackgame4.createPhysicalObject(name4, num4, nation4, age4, result4));
        break;
      case 5:
        System.out.print("������Ҫɾ������İ뾶��");
        int r5 = in.nextInt();
        for (Integer x : makeGroup.keySet()) {
          assert makeGroup.get(x).getCircles().size() - 1 >= 4 : "ɾ��������С��4";
          makeGroup.get(x).removeTrack(r5);
        }
        logging.doLog("ɾ���뾶Ϊ" + r5 + "���ܵ�");
        break;
      case 6:
        System.out.print("������Ҫɾ���˶�Ա������ı�ţ�");
        int g6 = in.nextInt();
        System.out.print("������Ҫɾ���˶�Ա���ڹ���İ뾶��");
        int r6 = in.nextInt();
        System.out.print("��������˶�Ա�����֣�");
        String name6 = in.next();
        System.out.print("��������˶�Ա�ĺ��룺");
        int num6 = in.nextInt();
        System.out.print("��������˶�Ա�Ĺ�����");
        String nation6 = in.next();
        System.out.print("��������˶�Ա�����䣺");
        int age6 = in.nextInt();
        System.out.print("��������˶�Ա����óɼ���");
        double result6 = in.nextDouble();
        logging.doLog("ɾ���˶�Ա" + name6 + "�ڵ�" + g6 + "��İ뾶Ϊ" + r6 + "���ܵ��Ϻ���Ϊ" + num6 + "����Ϊ"
            + nation6 + "����Ϊ" + age6 + "��óɼ�Ϊ" + result6);
        TrackGame<CentralObject, PhysicalObject> trackgame6 = (TrackGame<CentralObject, PhysicalObject>) makeGroup
            .get(g6);
        c.removeObjectFromOneTrack(r6,
            trackgame6.createPhysicalObject(name6, num6, nation6, age6, result6));
        break;
      case 7:
        CircularOrbitAPIs api7 = new CircularOrbitAPIs();
        System.out.print("������Ҫ������ı�ţ�����" + makeGroup.size() + "�飬��Ŵ�0��ʼ����");
        int g7 = in.nextInt();
        System.out.print("����ֲ�����ֵ��" + api7.getObjectDistributionEntropy(makeGroup.get(g7)));
        logging.doLog("�����" + g7 + "�������ֲ���ֵ");
        break;
      case 8:
        System.out.print("����1���ɼ��ţ�����2����ţ���������ţ�");
        Map<Integer, Map<PhysicalObject, Integer>> groupMap;
        while (true) {
          int n8 = in.nextInt();
          if (n8 == 1) {
            logging.doLog("ѡ�񰴳ɼ����ų���˳��");
            StrategyContext strategyContext1 = new StrategyContext(new Score());
            TrackGame<CentralObject, PhysicalObject> trackgame8 = (TrackGame<CentralObject, PhysicalObject>) c;
            groupMap = strategyContext1.choose(trackgame8.getAllObjects(),
                trackgame8.getTracknums());
            break;
          } else if (n8 == 2) {
            logging.doLog("ѡ��������ų���˳��");
            StrategyContext strategyContext2 = new StrategyContext(new Random());
            TrackGame<CentralObject, PhysicalObject> trackgame8 = (TrackGame<CentralObject, PhysicalObject>) c;
            groupMap = strategyContext2.choose(trackgame8.getAllObjects(),
                trackgame8.getTracknums());
            break;
          } else {
            logging.doLog("��������������������");
            System.out.print("�������������������ţ�");
          }
        }
        for (Integer num : groupMap.keySet()) {
          for (Entry<PhysicalObject, Integer> entry : groupMap.get(num).entrySet()) {
            System.out.println(
                "��ţ�" + num + "  �˶�Ա��" + entry.getKey().getName() + "  �ܵ���" + entry.getValue());
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
        System.out.print("�������˶�Ա1�����֣�");
        String name1 = in.next();
        System.out.print("�������˶�Ա2�����֣�");
        String name2 = in.next();
        logging.doLog("�����˶�Ա" + name1 + "�˶�Ա" + name2 + "��λ��");
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
          System.out.println("�������˶�Ա�в����ڵ�");
          logging.doLog("�������˶�Ա�в����ڵ�");
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
        System.out.println("1.��ȡ�����ļ�");
        System.out.println("2.���ӻ�����ṹ");
        System.out.println("3.�����¹��");
        System.out.println("4.���ض��������������");
        System.out.println("5.ɾ���������");
        System.out.println("6.���ض������ɾ������");
        System.out.println("7.�������ϵͳ�и����������ֲ�����ֵ");
        System.out.println("8.���ű�������");
        System.out.println("9.�ֹ�������������");
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
            Pattern pattern4 = Pattern.compile("<message>����˶�Ա");
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
            Pattern pattern6 = Pattern.compile("<message>ɾ���˶�Ա");
            Matcher matcher6 = pattern6.matcher(line);
            if (matcher6.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 7) {
          for (String line : content1) {
            Pattern pattern7 = Pattern.compile("<message>�����");
            Matcher matcher7 = pattern7.matcher(line);
            if (matcher7.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 8) {
          for (String line : content1) {
            Pattern pattern8 = Pattern.compile("<message>ѡ��");
            Matcher matcher8 = pattern8.matcher(line);
            if (matcher8.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 9) {
          for (String line : content1) {
            Pattern pattern9 = Pattern.compile("<message>�����˶�Ա");
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
        logging.doLog("����");
        break;
      default:
        System.out.print("�����������������");
        logging.doLog("�����������������");
      }
      if (end)
        break;
    }
    if (!legal(makeGroup)) {
      System.out.println("����ϵͳ���Ϸ�");
      logging.doLog("����ϵͳ���Ϸ� ");
    }
    return true;
  }
}
