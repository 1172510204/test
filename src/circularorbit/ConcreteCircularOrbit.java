package circularorbit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import track.Track;
import track.TrackFactory;
import track.TrackFactoryTrackGame;

public class ConcreteCircularOrbit<CentralObject, PhysicalObject>
    implements CircularOrbit<CentralObject, PhysicalObject>, java.lang.Iterable<Track> {

  // AF
  // ConcreteCircularOrbit包含中心物体centerObject、轨道和对应的轨道物体circles、中心物体和轨道物体的关系centreTrackObject、
  // 轨道物体之间的关系twoObjects和从内到外排好的轨道track
  // RI
  // 不同的PhysicalObject应该不同
  // circles不为空
  // Safety from rep exposure:
  // track是private，其他的field是protected
  // centerObject是不可变的，其他的是可变的，做防御式拷贝
  protected CentralObject centerObject;
  protected Map<Track, List<PhysicalObject>> circles = new HashMap<>();
  protected List<PhysicalObject> centreTrackObject = new ArrayList<>();
  protected Map<PhysicalObject, List<PhysicalObject>> twoObjects = new HashMap<>();
  private List<Track> track = new ArrayList<>();

  /**
   * 添加一个轨道
   * 
   * @param n 轨道编号
   * @param r 轨道半径 防御策略：assert判断轨道半径大于等于0
   */
  public void addTrack(int n, int r) {
    assert r >= 0 : "添加轨道的半径小于0";
    TrackFactory factory = new TrackFactoryTrackGame();
    Track track = factory.createTrack(n, r);
    for (Track each : circles.keySet()) {
      if (each.getR() == r) {
        return;
      }
    }
    circles.put(track, new ArrayList<>());
  }

  /**
   * 删除轨道
   * 
   * @param r 轨道半径
   * @return true if 删除成功，false if 不存在要删除的轨道 防御策略：assert判断轨道半径大于等于0
   */
  public boolean removeTrack(int r) {
    assert r >= 0 : "要删除轨道的半径小于0";
    for (Track each : circles.keySet()) {
      if (each.getR() == r) {
        circles.remove(each);
        return true;
      }
    }
    System.out.println("不存在此条轨道");
    return false;
  }

  /**
   * 添加中心物体
   * 
   * @param cObject 给定要添加的中心物体
   */
  public void addCenterObject(CentralObject cObject) {
    centerObject = cObject;
  }

  /**
   * 向指定的轨道上添加物体
   * 
   * @param n 轨道编号
   * @param r 轨道半径
   * @param object 给定要添加的轨道物体
   * @return true 如果添加成功，false 如果没有添加的物体所指定的轨道 防御策略：assert判断轨道半径大于等于0
   */
  public boolean addTrackObject(int n, int r, PhysicalObject object) {// 必须先添加轨道才能添加轨道上的物体
    assert r >= 0 : "要删除轨道的半径小于0";
    for (Track each : circles.keySet()) {
      if (each.getR() == r) {
        circles.get(each).add(object);
        return true;
      }
    }
    System.out.println("不存在此条轨道");
    return false;
  }

  /**
   * 将轨道物体从指定轨道上删除
   * 
   * @param r 轨道半径
   * @param object 要删除的轨道上的物体
   * @return true if 指定轨道上原本存在要删除的物体，false if 要删除的物体或者指定得到轨道不存在 防御策略：assert判断轨道半径大于等于0
   */
  public boolean removeObjectFromOneTrack(int r, PhysicalObject object) {
    assert r >= 0 : "要删除轨道的半径小于0";
    for (Track each : circles.keySet()) {
      if (each.getR() == r) {
        circles.get(each).remove(object);
        return true;
      }
    }
    return false;
  }


  /**
   * 添加轨道物体和中心物体之间的关系
   * 
   * @param object 与中心物体有关的轨道物体
   */
  public void addCentreTrackObjectRelation(PhysicalObject object) {
    centreTrackObject.add(object);
  }

  /**
   * 添加两个轨道物体之间的关系
   * 
   * @param object1 两个有关系的轨道物体之一
   * @param object2 两个有关系的轨道物体之一
   * @return true if 添加成功，otherwise if 添加不成功
   */
  public boolean addTwoObjectsRelation(PhysicalObject object1, PhysicalObject object2) {
    for (PhysicalObject each : twoObjects.keySet()) {
      if (each.equals(object1)) {
        twoObjects.get(each).add(object2);
        return true;
      }
    }
    twoObjects.put(object1, new ArrayList<>());
    twoObjects.get(object1).add(object2);
    return true;
  }

  /**
   * 读取文件内容
   * 
   * @param filename 文件的地址形成的字符串
   * @throws Exception 防御策略：assert判断字符串不能为空
   */
  public void readFile(String filename) throws Exception {
    assert filename != null : "文件名字为空";
  }

  /**
   * 轨道物体的跃迁
   * 
   * @param object 要发生跃迁的轨道物体
   * @param n 跃迁目的轨道的编号
   * @param r 跃迁目的轨道的半径 防御策略：assert判断轨道半径大于等于0
   */
  public void transit(PhysicalObject object, int n, int r) {
    assert r >= 0 : "要删除轨道的半径小于0";
    int flag = 0;
    for (Track e : circles.keySet()) {
      if (e.getR() == r) {
        flag = 1;
      }
    }
    if (flag == 0) {
      TrackFactory factory = new TrackFactoryTrackGame();
      Track track = factory.createTrack(n, r);
    }
    for (Track oldtrack : circles.keySet()) {
      for (PhysicalObject each : circles.get(oldtrack)) {
        if (each.equals(object)) {
          circles.get(oldtrack).remove(object);
          break;
        }
      }
    }
    for (Track newtrack : circles.keySet()) {
      if (newtrack.getR() == r) {
        circles.get(newtrack).add(object);
      }
    }
  }

  /**
   * 获得每个轨道与对应轨道物体的信息
   * 
   * @return a map the key includes Track,the value includes 此轨道上轨道物体的信息
   */
  public Map<Track, List<PhysicalObject>> getCircles() {
    Map<Track, List<PhysicalObject>> newcirMap = new HashMap<>();
    for (Track track : circles.keySet()) {
      newcirMap.put(track, new ArrayList<>());
      for (PhysicalObject phy : circles.get(track)) {
        newcirMap.get(track).add(phy);
      }
    }
    return newcirMap;
  }

  /**
   * 获取中心物体
   * 
   * @return centralObject 中心物体的信息
   */
  public CentralObject getCentreObject() {
    return centerObject;
  }

  /**
   * 获取轨道物体之间的关系
   * 
   * @return a map,the key includes 轨道物体，the value includes 与此key有关系的轨道物体的轨道物体
   */
  public Map<PhysicalObject, List<PhysicalObject>> getTwoObjects() {
    return new HashMap<>(twoObjects);
  }

  /**
   * 获取文件中所包含的轨道物体的信息
   * 
   * @return a list includes all 轨道物体
   */
  public List<PhysicalObject> getCentreTrackObject() {
    return new ArrayList<>(centreTrackObject);
  }

  /**
   * 获取从内到外排列好的轨道
   * 
   * @return a list includes all tracks which descend sort 防御策略：assert 判断返回值的list里的track是从内到外排好
   */
  public List<Track> getTracks() {
    for (int i = 0; i < track.size() - 1; i++) {
      for (int j = i + 1; j < track.size(); j++) {
        int r1 = track.get(i).getR();
        int r2 = track.get(j).getR();
        assert r1 < r2 : "轨道没有从内到外排好";
      }
    }
    return new ArrayList<Track>(track);
  }

  /**
   * 重写迭代器，使遍历轨道时从里向外
   * 
   * @return Iterator 包含类型为track的迭代器
   */
  private int c = -1;

  @Override
  public Iterator<Track> iterator() {
    return new Iterator<Track>() {
      private void trackSortList() {
        for (Track each : circles.keySet()) {
          track.add(each);
        }
        int j, i, k;
        for (i = 0; i < track.size() - 1; i++) {
          Track flag = track.get(i);
          k = i;
          for (j = i + 1; j < track.size(); j++) {
            if (track.get(j).getR() < flag.getR()) {
              flag = track.get(j);
              k = j;
            }
          }
          Collections.swap(track, i, k);
        }
      }

      public boolean hasNext() {
        return c + 1 < circles.size();
      }

      public Track next() {
        c++;
        if (c == 0) {
          trackSortList();
        }
        return track.get(c);
      }
    };

  }

  public Map<Track, List<PhysicalObject>> changeCircles(Map<Track, List<PhysicalObject>> cir) {
    circles = cir;
    return circles;
  }


}
