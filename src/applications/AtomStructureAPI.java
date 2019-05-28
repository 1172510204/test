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
   * �˵�
   */
  private static void menu() {
    System.out.println("-----------------------------");
    System.out.println("1.��ȡ�����ļ������ɶ����ṹ");
    System.out.println("2.���ӻ�����ṹ");
    System.out.println("3.�����¹��");
    System.out.println("4.���ض���������ӵ���");
    System.out.println("5.ɾ���������");
    System.out.println("6.���ض������ɾ������");
    System.out.println("7.�������ϵͳ�и�����ϵ��ӷֲ�����ֵ");
    System.out.println("8.ģ�����ԾǨ");
    System.out.println("9.����ʱ��β�ѯ��־");
    System.out.println("10.���ղ������Ͳ�ѯ��־");
    System.out.println("11.��ѯ��־�г��ֵ��쳣");
    System.out.println("12.����");
    System.out.println("���������ѡ�������");
  }

  /**
   * ѡ��˵��еĹ���ִ��
   * 
   * @return true ��ȡ���ļ��������﷨���� false ��ȡ���ļ������﷨����
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
        System.out.print("�������ļ����ƣ�");
        String nameString = in.next();
        logging.doLog("��ȡ�ļ�" + nameString + ".txt");
        try {
          AtomStructure<CentralObject, PhysicalObject> a1 = (AtomStructure<CentralObject, PhysicalObject>) c;
          a1.readFile("src/txt/" + nameString + ".txt");
        } catch (SameLabelException | DependIncorrectException | ElementException e) {
          logging.logException(e);
          e.printStackTrace();
          System.out.println("��ȡ�ļ��﷨����һ���������ѡ���ļ�");
          logging.doLog("��ȡ�ļ��﷨����һ���������ѡ���ļ�");
          return false;
        }
        break;
      case 2:
        logging.doLog("���ӻ�");
        CircularOrbitHelper.visualize(c);
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
        logging.doLog("�ڱ��Ϊ" + n4 + "�뾶Ϊ" + r4 + "�Ĺ������ӵ���");
        AtomStructure<CentralObject, PhysicalObject> atomStructure4 = (AtomStructure<CentralObject, PhysicalObject>) c;
        c.addTrackObject(n4, r4, atomStructure4.createPhysicalObject(n4));
        break;
      case 5:
        System.out.print("������Ҫɾ������İ뾶��");
        int r5 = in.nextInt();
        c.removeTrack(r5);
        logging.doLog("ɾ���뾶Ϊ" + r5 + "�Ĺ��");
        break;
      case 6:
        System.out.print("������Ҫɾ���������ڹ���İ뾶��");
        int r6 = in.nextInt();
        logging.doLog("ɾ���ڰ뾶Ϊ" + r6 + "�Ĺ���ϵ�һ������");
        for (Track track : c.getCircles().keySet()) {
          if (track.getR() == r6) {
            if (c.getCircles().get(track).size() != 0) {
              c.removeObjectFromOneTrack(r6, c.getCircles().get(track).get(0));
              break;
            } else {
              System.out.println("�˹���ϲ����ڵ���");
              logging.doLog("�˹���ϲ����ڵ���");
            }
          }
        }

        break;
      case 7:
        CircularOrbitAPIs api7 = new CircularOrbitAPIs();
        System.out.print("����ֲ�����ֵ" + api7.getObjectDistributionEntropy(c));
        logging.doLog("������ӷֲ���ֵ");
        break;
      case 8:
        Originator originator = new Originator();
        CareTaker careTaker = new CareTaker();
        AtomStructure<CentralObject, PhysicalObject> atomStructure8 = (AtomStructure<CentralObject, PhysicalObject>) c;
        AtomStructure<CentralObject, PhysicalObject> a1 = atomStructure8.copyAS();
        a1.changeCircles(atomStructure8.getCircles());
        originator.setAtom(a1);
        careTaker.add(originator.saveStateToMemento());
        System.out.print("������Ŀ�����ı�ţ�");
        int n8 = in.nextInt();
        System.out.print("������Ŀ�����İ뾶��");
        int r8 = in.nextInt();
        System.out.print("������ҪԾǨ�ĵ������ڵĹ���İ뾶��");
        int r88 = in.nextInt();
        logging.doLog("���ӴӰ뾶Ϊ" + r8 + "�Ĺ��ԾǨ���뾶Ϊ" + r88 + "�Ĺ����");
        for (Track track : c.getCircles().keySet()) {
          if (track.getR() == r88) {
            if (c.getCircles().get(track).size() != 0) {
              PhysicalObject ele = c.getCircles().get(track).get(0);
              atomStructure8.transit(ele, n8, r8);
              break;
            } else {
              System.out.println("ԭ���Ĺ���ϲ����ڵ���");
              logging.doLog("ԭ���Ĺ���ϲ����ڵ���");
            }
          }
        }

        break;
      case 9:
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
      case 10:
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
        System.out.println("4.���ض���������ӵ���");
        System.out.println("5.ɾ���������");
        System.out.println("6.���ض������ɾ������");
        System.out.println("7.�������ϵͳ�и�����ϵ��ӷֲ�����ֵ");
        System.out.println("8.ģ�����ԾǨ");
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
            Pattern pattern4 = Pattern.compile("<message>�ڱ��Ϊ");
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
            Pattern pattern6 = Pattern.compile("<message>ɾ���ڰ뾶Ϊ");
            Matcher matcher6 = pattern6.matcher(line);
            if (matcher6.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 7) {
          for (String line : content1) {
            Pattern pattern7 = Pattern.compile("<message>������ӷֲ���ֵ");
            Matcher matcher7 = pattern7.matcher(line);
            if (matcher7.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else if (o == 8) {
          for (String line : content1) {
            Pattern pattern8 = Pattern.compile("<message>���ӴӰ뾶Ϊ");
            Matcher matcher8 = pattern8.matcher(line);
            if (matcher8.find()) {
              System.out.println(line.substring(9));
            }
          }
        } else {
          System.out.println("������Ŵ���");
        }
        break;
      case 11:
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
      case 12:
        end = true;
        break;
      default:
        System.out.print("�����������������");
        logging.doLog("�����������������");
      }
      if (end)
        break;
    }
    return true;
  }
}
