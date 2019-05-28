package applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import physicalobject.PhysicalObject;

public class Random implements Strategy {

  /**
   * 编排比赛方案
   * 
   * @param allAthlete a list 包含所有要比赛的运动员
   * @param tracknums  比赛的跑道数目
   * @return a map key 组数 value a map key运动员 value 跑道编号 防御策略：pre-condition
   *         tracknums为4-10 allAthlete不为空 post-condition 返回的map的组数正确
   */
  @Override
  public Map<Integer, Map<PhysicalObject, Integer>> competitionOrder(
      List<PhysicalObject> allAthlete, int tracknums) {
    assert tracknums >= 4 && tracknums <= 10;
    assert allAthlete != null;
    int athleteNum = allAthlete.size();
    Map<Integer, Map<PhysicalObject, Integer>> order = new HashMap<Integer, Map<PhysicalObject, Integer>>();
    int flag = -1;
    for (int i = 0; i < athleteNum; i++) {
      int group = i / tracknums;
      int trackindex = i % tracknums + 1;
      if (flag != group) {
        order.put(group, new HashMap<>());
      }
      flag = group;
      order.get(group).put(allAthlete.get(i), trackindex);
    }
    int nn = (int) Math.ceil(allAthlete.size() / (double) tracknums);
    assert order.size() == nn;
    return order;
  }

}
