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
   * 添加一个轨道
   * 
   * @param n 轨道编号
   * @param r 轨道半径
   */
  public void addTrack(int n, int r);

  /**
   * 删除轨道
   * 
   * @param r 轨道半径
   * @return true if 删除成功，false if 不存在要删除的轨道
   */
  public boolean removeTrack(int r);

  /**
   * 添加中心物体
   * 
   * @param cObject 给定要添加的中心物体
   */
  public void addCenterObject(L cObject);


  /**
   * 向指定的轨道上添加物体
   * 
   * @param n 轨道编号
   * @param r 轨道半径
   * @param object 给定要添加的轨道物体
   * @return true 如果添加成功，false 如果没有添加的物体所指定的轨道
   */
  public boolean addTrackObject(int n, int r, E object);


  /**
   * 将轨道物体从指定轨道上删除
   * 
   * @param r 轨道半径
   * @param object 要删除的轨道上的物体
   * @return true if 指定轨道上原本存在要删除的物体，false if 要删除的物体或者指定得到轨道不存在
   */
  public boolean removeObjectFromOneTrack(int r, E object);


  /**
   * 添加轨道物体和中心物体之间的关系
   * 
   * @param object 与中心物体有关的轨道物体
   */
  public void addCentreTrackObjectRelation(E object);


  /**
   * 添加两个轨道物体之间的关系
   * 
   * @param object1 两个有关系的轨道物体之一
   * @param object2 两个有关系的轨道物体之一
   * @return true if 添加成功，otherwise if 添加不成功
   */
  public boolean addTwoObjectsRelation(E object1, E object2);


  /**
   * 读取文件内容
   * 
   * @param filename 文件的地址形成的字符串
   * @throws Exception
   */
  public void readFile(String filename) throws Exception;


  /**
   * 获得每个轨道与对应轨道物体的信息
   * 
   * @return a map the key includes Track,the value includes 此轨道上轨道物体的信息
   */
  public Map<Track, List<E>> getCircles();


  /**
   * 获取中心物体
   * 
   * @return centralObject 中心物体的信息
   */
  public L getCentreObject();


  /**
   * 获取轨道物体之间的关系
   * 
   * @return a map,the key includes 轨道物体，the value includes 与此key有关系的轨道物体的轨道物体
   */
  public Map<E, List<E>> getTwoObjects();


  /**
   * 获取文件中所包含的轨道物体的信息
   * 
   * @return a list includes all 轨道物体
   */
  public List<E> getCentreTrackObject();


  /**
   * 获取从内到外排列好的轨道
   * 
   * @return a list includes all tracks which descend sort
   */
  public List<Track> getTracks();


  /**
   * 重写迭代器，使遍历轨道时从里向外
   * 
   * @return Iterator 包含类型为track的迭代器
   */
  public Iterator<Track> iterator();

}
