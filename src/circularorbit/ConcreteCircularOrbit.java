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
  // ConcreteCircularOrbit������������centerObject������Ͷ�Ӧ�Ĺ������circles����������͹������Ĺ�ϵcentreTrackObject��
  // �������֮��Ĺ�ϵtwoObjects�ʹ��ڵ����źõĹ��track
  // RI
  // ��ͬ��PhysicalObjectӦ�ò�ͬ
  // circles��Ϊ��
  // Safety from rep exposure:
  // track��private��������field��protected
  // centerObject�ǲ��ɱ�ģ��������ǿɱ�ģ�������ʽ����
  protected CentralObject centerObject;
  protected Map<Track, List<PhysicalObject>> circles = new HashMap<>();
  protected List<PhysicalObject> centreTrackObject = new ArrayList<>();
  protected Map<PhysicalObject, List<PhysicalObject>> twoObjects = new HashMap<>();
  private List<Track> track = new ArrayList<>();

  /**
   * ���һ�����
   * 
   * @param n ������
   * @param r ����뾶 �������ԣ�assert�жϹ���뾶���ڵ���0
   */
  public void addTrack(int n, int r) {
    assert r >= 0 : "��ӹ���İ뾶С��0";
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
   * ɾ�����
   * 
   * @param r ����뾶
   * @return true if ɾ���ɹ���false if ������Ҫɾ���Ĺ�� �������ԣ�assert�жϹ���뾶���ڵ���0
   */
  public boolean removeTrack(int r) {
    assert r >= 0 : "Ҫɾ������İ뾶С��0";
    for (Track each : circles.keySet()) {
      if (each.getR() == r) {
        circles.remove(each);
        return true;
      }
    }
    System.out.println("�����ڴ������");
    return false;
  }

  /**
   * �����������
   * 
   * @param cObject ����Ҫ��ӵ���������
   */
  public void addCenterObject(CentralObject cObject) {
    centerObject = cObject;
  }

  /**
   * ��ָ���Ĺ�����������
   * 
   * @param n ������
   * @param r ����뾶
   * @param object ����Ҫ��ӵĹ������
   * @return true �����ӳɹ���false ���û����ӵ�������ָ���Ĺ�� �������ԣ�assert�жϹ���뾶���ڵ���0
   */
  public boolean addTrackObject(int n, int r, PhysicalObject object) {// ��������ӹ��������ӹ���ϵ�����
    assert r >= 0 : "Ҫɾ������İ뾶С��0";
    for (Track each : circles.keySet()) {
      if (each.getR() == r) {
        circles.get(each).add(object);
        return true;
      }
    }
    System.out.println("�����ڴ������");
    return false;
  }

  /**
   * ����������ָ�������ɾ��
   * 
   * @param r ����뾶
   * @param object Ҫɾ���Ĺ���ϵ�����
   * @return true if ָ�������ԭ������Ҫɾ�������壬false if Ҫɾ�����������ָ���õ���������� �������ԣ�assert�жϹ���뾶���ڵ���0
   */
  public boolean removeObjectFromOneTrack(int r, PhysicalObject object) {
    assert r >= 0 : "Ҫɾ������İ뾶С��0";
    for (Track each : circles.keySet()) {
      if (each.getR() == r) {
        circles.get(each).remove(object);
        return true;
      }
    }
    return false;
  }


  /**
   * ��ӹ���������������֮��Ĺ�ϵ
   * 
   * @param object �����������йصĹ������
   */
  public void addCentreTrackObjectRelation(PhysicalObject object) {
    centreTrackObject.add(object);
  }

  /**
   * ��������������֮��Ĺ�ϵ
   * 
   * @param object1 �����й�ϵ�Ĺ������֮һ
   * @param object2 �����й�ϵ�Ĺ������֮һ
   * @return true if ��ӳɹ���otherwise if ��Ӳ��ɹ�
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
   * ��ȡ�ļ�����
   * 
   * @param filename �ļ��ĵ�ַ�γɵ��ַ���
   * @throws Exception �������ԣ�assert�ж��ַ�������Ϊ��
   */
  public void readFile(String filename) throws Exception {
    assert filename != null : "�ļ�����Ϊ��";
  }

  /**
   * ��������ԾǨ
   * 
   * @param object Ҫ����ԾǨ�Ĺ������
   * @param n ԾǨĿ�Ĺ���ı��
   * @param r ԾǨĿ�Ĺ���İ뾶 �������ԣ�assert�жϹ���뾶���ڵ���0
   */
  public void transit(PhysicalObject object, int n, int r) {
    assert r >= 0 : "Ҫɾ������İ뾶С��0";
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
   * ���ÿ��������Ӧ����������Ϣ
   * 
   * @return a map the key includes Track,the value includes �˹���Ϲ���������Ϣ
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
   * ��ȡ��������
   * 
   * @return centralObject �����������Ϣ
   */
  public CentralObject getCentreObject() {
    return centerObject;
  }

  /**
   * ��ȡ�������֮��Ĺ�ϵ
   * 
   * @return a map,the key includes ������壬the value includes ���key�й�ϵ�Ĺ������Ĺ������
   */
  public Map<PhysicalObject, List<PhysicalObject>> getTwoObjects() {
    return new HashMap<>(twoObjects);
  }

  /**
   * ��ȡ�ļ����������Ĺ���������Ϣ
   * 
   * @return a list includes all �������
   */
  public List<PhysicalObject> getCentreTrackObject() {
    return new ArrayList<>(centreTrackObject);
  }

  /**
   * ��ȡ���ڵ������кõĹ��
   * 
   * @return a list includes all tracks which descend sort �������ԣ�assert �жϷ���ֵ��list���track�Ǵ��ڵ����ź�
   */
  public List<Track> getTracks() {
    for (int i = 0; i < track.size() - 1; i++) {
      for (int j = i + 1; j < track.size(); j++) {
        int r1 = track.get(i).getR();
        int r2 = track.get(j).getR();
        assert r1 < r2 : "���û�д��ڵ����ź�";
      }
    }
    return new ArrayList<Track>(track);
  }

  /**
   * ��д��������ʹ�������ʱ��������
   * 
   * @return Iterator ��������Ϊtrack�ĵ�����
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
