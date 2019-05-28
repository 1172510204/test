package applications;

import java.util.List;
import java.util.Map;
import physicalobject.PhysicalObject;

public interface Strategy {
  /**
   * 编排比赛方案
   * 
   * @param allAthlete a list 包含所有要比赛的运动员
   * @param tracknums  比赛的跑道数目
   * @return a map key 组数 value a map key运动员 value 跑道编号
   */
  public Map<Integer, Map<PhysicalObject, Integer>> competitionOrder(
      List<PhysicalObject> allAthlete, int tracknums);
  // map< 组数，map<运动员,跑道编号> >
}
