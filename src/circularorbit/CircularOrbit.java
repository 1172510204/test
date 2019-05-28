package circularorbit;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import track.Track;

public interface CircularOrbit<L, E> {

  public static <L, E> CircularOrbit<L, E> empty() {
    @SuppressWarnings("unchecked")
    CircularOrbit<L, E> e = (CircularOrbit<L, E>) new ConcreteCircularOrbit();
    return e;
  }

  /**
   * ���һ�����
   * 
   * @param n ������
   * @param r ����뾶
   */
  public void addTrack(int n, int r);

  /**
   * ɾ�����
   * 
   * @param r ����뾶
   * @return true if ɾ���ɹ���false if ������Ҫɾ���Ĺ��
   */
  public boolean removeTrack(int r);

  /**
   * �����������
   * 
   * @param cObject ����Ҫ��ӵ���������
   */
  public void addCenterObject(L cObject);


  /**
   * ��ָ���Ĺ�����������
   * 
   * @param n ������
   * @param r ����뾶
   * @param object ����Ҫ��ӵĹ������
   * @return true �����ӳɹ���false ���û����ӵ�������ָ���Ĺ��
   */
  public boolean addTrackObject(int n, int r, E object);


  /**
   * ����������ָ�������ɾ��
   * 
   * @param r ����뾶
   * @param object Ҫɾ���Ĺ���ϵ�����
   * @return true if ָ�������ԭ������Ҫɾ�������壬false if Ҫɾ�����������ָ���õ����������
   */
  public boolean removeObjectFromOneTrack(int r, E object);


  /**
   * ��ӹ���������������֮��Ĺ�ϵ
   * 
   * @param object �����������йصĹ������
   */
  public void addCentreTrackObjectRelation(E object);


  /**
   * ��������������֮��Ĺ�ϵ
   * 
   * @param object1 �����й�ϵ�Ĺ������֮һ
   * @param object2 �����й�ϵ�Ĺ������֮һ
   * @return true if ��ӳɹ���otherwise if ��Ӳ��ɹ�
   */
  public boolean addTwoObjectsRelation(E object1, E object2);


  /**
   * ��ȡ�ļ�����
   * 
   * @param filename �ļ��ĵ�ַ�γɵ��ַ���
   * @throws Exception
   */
  public void readFile(String filename) throws Exception;


  /**
   * ���ÿ��������Ӧ����������Ϣ
   * 
   * @return a map the key includes Track,the value includes �˹���Ϲ���������Ϣ
   */
  public Map<Track, List<E>> getCircles();


  /**
   * ��ȡ��������
   * 
   * @return centralObject �����������Ϣ
   */
  public L getCentreObject();


  /**
   * ��ȡ�������֮��Ĺ�ϵ
   * 
   * @return a map,the key includes ������壬the value includes ���key�й�ϵ�Ĺ������Ĺ������
   */
  public Map<E, List<E>> getTwoObjects();


  /**
   * ��ȡ�ļ����������Ĺ���������Ϣ
   * 
   * @return a list includes all �������
   */
  public List<E> getCentreTrackObject();


  /**
   * ��ȡ���ڵ������кõĹ��
   * 
   * @return a list includes all tracks which descend sort
   */
  public List<Track> getTracks();


  /**
   * ��д��������ʹ�������ʱ��������
   * 
   * @return Iterator ��������Ϊtrack�ĵ�����
   */
  public Iterator<Track> iterator();

}
