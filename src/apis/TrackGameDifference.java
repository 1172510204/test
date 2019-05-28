package apis;

import java.util.HashMap;
import java.util.Map;

public class TrackGameDifference extends Difference {

  // AF
  // PersonalAppEcosystemDifference不仅包含tracknums和trackNumberAndObjectsDif，还包含trackNumberAndName
  // RI
  // trackNumberAndName不为空
  // Safety from rep exposure:
  // all the fields are private
  // tracknums是不可变的 trackNumberAndObjectsDif和trackNumberAndName是可变的，做防御性拷贝
  private Map<Integer, String> trackNumberAndName = new HashMap<>();// 每个轨道的序号和对应物体名称的区别

  @Override
  public void checkRep() {
    super.checkRep();
    assert trackNumberAndName != null;
  }

  /**
   * 初始化每个轨道的序号和对应物体名称的区别
   * 
   * @param trackNumberAndName 每个轨道的序号和对应物体名称的区别
   */
  @Override
  public void setTrackNumberAndName(Map<Integer, String> trackNumberAndName) {
    this.trackNumberAndName = trackNumberAndName;
  }

  /**
   * 获取每个轨道的序号和对应物体名称的区别
   * 
   * @return a map key是轨道号 value是此轨道上对应物体的名称
   */
  @Override
  public Map<Integer, String> getTrackNumberAndName() {
    checkRep();
    return new HashMap<>(trackNumberAndName);
  }

}
