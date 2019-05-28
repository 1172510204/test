package circularorbit;

import centralobject.Nucleus;
import exception.DependIncorrectException;
import exception.SameLabelException;
import exception.OutRules.ElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import physicalobject.PhysicalAtomStructureFactory;
import physicalobject.PhysicalObjectFactory;
import track.Track;
import track.TrackFactory;
import track.TrackFactoryAtomStructure;

public class AtomStructure<CentralObject, PhysicalObject>
    extends ConcreteCircularOrbit<CentralObject, PhysicalObject> {

  // AF
  // AtomStructure������������centerObject������Ͷ�Ӧ�Ĺ������circles����������͹������Ĺ�ϵcentreTrackObject��
  // �������֮��Ĺ�ϵtwoObjects�ʹ��ڵ����źõĹ��track
  // ��������������ԭ�Ӻˡ�Ԫ�ص����ơ������Ŀ��ÿ������϶�Ӧ�ĵ�����Ŀ
  // RI
  // ����ConcreteCircularOrbit�еģ��������岻Ϊ��
  // Safety from rep exposure:
  // ����ConcreteCircularOrbit�е�
  // all the fields are private
  // centre��element��trackNum�ǲ��ɱ�ģ�trackElectron�ǿɱ�ģ�������ʽ����
  private CentralObject centre = createCentralObject();
  private String element;
  private int trackNum;
  private Map<Integer, Integer> trackElectron = new HashMap<Integer, Integer>();
  private String file;

  /**
   * ����ԭ�ӵ����ʹ�����������
   * 
   * @return a CentralObject
   */
  @SuppressWarnings("unchecked")
  private CentralObject createCentralObject() {
    CentralObject c = (CentralObject) new Nucleus();
    centerObject = c;
    return c;
  }

  /**
   * ����ԭ�����ʹ����Ӧ�Ĺ�����������
   * 
   * @param n ����Ĺ�������Ӧ�ı��
   * @return a PhysicalObject ���Ǹոմ���Ĺ������
   */
  @SuppressWarnings("unchecked")
  public PhysicalObject createPhysicalObject(int n) {
    PhysicalObjectFactory factory = new PhysicalAtomStructureFactory();
    return (PhysicalObject) factory.createPhysicalObject3(n);
  }

  @Override
  public void addTrack(int n, int r) {
    // TODO Auto-generated method stub
    super.addTrack(n, r);
    TrackFactory factory = new TrackFactoryAtomStructure();
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
   * @throws SameLabelException ԭ�ӹ���ظ�
   * @throws DependIncorrectException NumberOfTracks ����Ĺ�������� NumberOfElectron ��ʹ�õĹ�� ������һ��
   * @throws ElementException Ҫ��Ԫ������� λ��ĸ����һλ��д���ڶ�λСд�����У� �������ԣ�assert�ж��ַ�������Ϊ��
   */
  @Override
  public void readFile(String filename)
      throws SameLabelException, DependIncorrectException, ElementException {
    assert filename != null : "�ļ�����Ϊ��";
    file = filename;
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
    // Map<Integer, Integer> trackElectron=new HashMap<Integer, Integer>();
    Pattern pattern1 = Pattern.compile("(ElementName ::= )([A-Za-z]*)");
    Pattern pattern2 = Pattern.compile("(NumberOfTracks ::= )([1-9]\\d*)");
    Pattern pattern3 = Pattern.compile("NumberOfElectron ::= ");
    for (String each : content) {
      Matcher matcher1 = pattern1.matcher(each);
      Matcher matcher2 = pattern2.matcher(each);
      Matcher matcher3 = pattern3.matcher(each);
      if (matcher1.find()) {
        element = matcher1.group(2);
        Pattern pattern11 = Pattern.compile("[A-Z][a-z]");
        Matcher matcher11 = pattern11.matcher(element);
        Pattern pattern12 = Pattern.compile("[A-Z]");
        Matcher matcher12 = pattern12.matcher(element);
        if (!((matcher11.find() && element.length() == 2)
            || (matcher12.find() && element.length() == 1))) {
          System.out.println("ԭ�ӵ�Ԫ����д������Ҫ��");
          throw new ElementException();
        }
      } else if (matcher2.find()) {
        trackNum = Integer.valueOf(matcher2.group(2)).intValue();
      } else if (matcher3.find()) {
        String rest = each.substring(21);
        Pattern pattern = Pattern.compile("(\\d*)\\/(\\d*)");
        Matcher matcher = pattern.matcher(rest);
        while (matcher.find()) {
          if (trackElectron.containsKey(Integer.valueOf(matcher.group(1)).intValue())) {
            System.out.println("ԭ�ӵĹ���ظ�");
            throw new SameLabelException();
          }
          trackElectron.put(Integer.valueOf(matcher.group(1)).intValue(),
              Integer.valueOf(matcher.group(2)).intValue());
        }
        if (trackElectron.size() != trackNum) {
          System.out.println("NumberOfTracks ����Ĺ�������� NumberOfElectron ��ʹ�õĹ�� ������һ��");
          throw new DependIncorrectException();
        }
      }
    }
    initialModel();
  }

  /**
   * ���ݴ��ı��ж������Ϣ����ʼ��������ڹ������ӵ���
   */
  private void initialModel() {
    for (int i = 1; i <= trackNum; i++) {
      addTrack(i, i);
    }
    for (int i : trackElectron.keySet()) {
      int num = trackElectron.get(i);
      for (int j = 0; j < num; j++) {
        PhysicalObject electron = createPhysicalObject(i);
        addTrackObject(i, i, electron);
      }
    }
    addCenterObject(centre);
  }


  /**
   * ��ȡ��������
   * 
   * @return the CentralObject ��ԭ�ӵ�ԭ�Ӻ�
   */
  /*
   * public CentralObject getCentre() { return centre; }
   */

  /**
   * ��ȡ��ԭ�Ӻ˶�Ӧ��Ԫ��
   * 
   * @return a string Ԫ�ض�Ӧ���ַ���
   */
  public String getElement() {
    assert element != null;
    return element;
  }

  /**
   * ��ȡ�������Ŀ
   * 
   * @return a number ��ԭ�Ӻ��ڹ������Ŀ
   */
  public int getTrackNum() {
    return trackNum;
  }

  public AtomStructure<CentralObject, PhysicalObject> copyAS() {
    AtomStructure<CentralObject, PhysicalObject> newAS = new AtomStructure<>();
    try {
      newAS.readFile(file);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return newAS;
  }



}
