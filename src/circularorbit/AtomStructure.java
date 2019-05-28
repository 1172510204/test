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
  // AtomStructure包含中心物体centerObject、轨道和对应的轨道物体circles、中心物体和轨道物体的关系centreTrackObject、
  // 轨道物体之间的关系twoObjects和从内到外排好的轨道track
  // 还包含中心物体原子核、元素的名称、轨道数目和每个轨道上对应的电子数目
  // RI
  // 除了ConcreteCircularOrbit中的，中心物体不为空
  // Safety from rep exposure:
  // 除了ConcreteCircularOrbit中的
  // all the fields are private
  // centre、element、trackNum是不可变的，trackElectron是可变的，做防御式拷贝
  private CentralObject centre = createCentralObject();
  private String element;
  private int trackNum;
  private Map<Integer, Integer> trackElectron = new HashMap<Integer, Integer>();
  private String file;

  /**
   * 根据原子的类型创建中心物体
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
   * 根据原子类型创造对应的轨道物体的类型
   * 
   * @param n 创造的轨道物体对应的编号
   * @return a PhysicalObject 就是刚刚创造的轨道物体
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
   * 读取文件内容
   * 
   * @param filename 文件的地址形成的字符串
   * @throws SameLabelException 原子轨道重复
   * @throws DependIncorrectException NumberOfTracks 定义的轨道数量和 NumberOfElectron 中使用的轨道 数量不一致
   * @throws ElementException 要求元素最多两 位字母，第一位大写，第二位小写（若有） 防御策略：assert判断字符串不能为空
   */
  @Override
  public void readFile(String filename)
      throws SameLabelException, DependIncorrectException, ElementException {
    assert filename != null : "文件名字为空";
    file = filename;
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
          System.out.println("原子的元素书写不符合要求");
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
            System.out.println("原子的轨道重复");
            throw new SameLabelException();
          }
          trackElectron.put(Integer.valueOf(matcher.group(1)).intValue(),
              Integer.valueOf(matcher.group(2)).intValue());
        }
        if (trackElectron.size() != trackNum) {
          System.out.println("NumberOfTracks 定义的轨道数量和 NumberOfElectron 中使用的轨道 数量不一致");
          throw new DependIncorrectException();
        }
      }
    }
    initialModel();
  }

  /**
   * 根据从文本中读入的信息，初始化轨道和在轨道内添加电子
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
   * 获取中心物体
   * 
   * @return the CentralObject 此原子的原子核
   */
  /*
   * public CentralObject getCentre() { return centre; }
   */

  /**
   * 获取此原子核对应的元素
   * 
   * @return a string 元素对应的字符串
   */
  public String getElement() {
    assert element != null;
    return element;
  }

  /**
   * 获取轨道的数目
   * 
   * @return a number 此原子核内轨道的数目
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
