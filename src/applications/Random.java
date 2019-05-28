package applications;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import physicalobject.PhysicalObject;

public class Random implements Strategy {

  /**
   * ���ű�������
   * 
   * @param allAthlete a list ��������Ҫ�������˶�Ա
   * @param tracknums  �������ܵ���Ŀ
   * @return a map key ���� value a map key�˶�Ա value �ܵ���� �������ԣ�pre-condition
   *         tracknumsΪ4-10 allAthlete��Ϊ�� post-condition ���ص�map��������ȷ
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
