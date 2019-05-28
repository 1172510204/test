package apis;

import java.util.HashMap;
import java.util.Map;

public abstract class Difference {

  // AF
  // difference包含轨道数量的差异和每个轨道的序号和对应物体数量的差异
  // RI
  // trackNumberAndObjectsDif不为空
  // Safety from rep exposure:
  // all the fields are private
  // tracknums是不可变的 trackNumberAndObjectsDif是可变的，做防御性拷贝
  private int tracknums;// 轨道数量差异
  private Map<Integer, Integer> trackNumberAndObjectsDif = new HashMap<>();// 每个轨道的序号和对应物体数量的差异
  // private Map<Integer, String> trackNumberAndName=new
  // HashMap<>();//每个轨道的序号和对应物体名称的区别

  public void checkRep() {
    assert trackNumberAndObjectsDif != null;
  }

  /**
   * 初始化轨道数量差异
   * 
   * @param n 轨道数量差异
   */
  public void setTracknums(int n) {
    tracknums = n;
  }

  /**
   * 初始化每个轨道的序号和对应物体数量的差异
   * 
   * @param trackNumberAndObjectsDif 每个轨道的序号和对应物体数量的差异
   */
  public void setTrackNumberAndObjectsDif(Map<Integer, Integer> trackNumberAndObjectsDif) {
    this.trackNumberAndObjectsDif = trackNumberAndObjectsDif;
  }

  /**
   * 获取轨道数量的差异
   * 
   * @return 轨道数量的差异
   */
  public int getTrackNums() {
    checkRep();
    return tracknums;
  }

  /**
   * 获取每个轨道的序号和对应物体数量的差异
   * 
   * @return key是轨道号 value是此轨道对应物体的数量
   */
  public Map<Integer, Integer> getTrackNumberAndObjectsDif() {
    return new HashMap<>(trackNumberAndObjectsDif);
  }

  /**
   * 初始化每个轨道的序号和对应物体名称的区别
   * 
   * @param trackNumberAndName 每个轨道的序号和对应物体名称的区别
   */
  public abstract void setTrackNumberAndName(Map<Integer, String> trackNumberAndName);

  /**
   * 获取每个轨道的序号和对应物体名称的区别
   * 
   * @return a map key是轨道号 value是此轨道上对应物体的名称
   */
  public abstract Map<Integer, String> getTrackNumberAndName();

}
