package applications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import physicalobject.Athlete;
import physicalobject.PhysicalObject;

public class Score implements Strategy {

  /*
   * 1 2 3 4 5 五 三 一 二 四
   * 
   * 1 2 3 4 5 6 五 三 一 二 四 六
   */

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
    List<PhysicalObject> descendingOrder = new ArrayList<>(allAthlete);
    for (int i = 0; i < descendingOrder.size() - 1; i++) {
      for (int j = 0; j < descendingOrder.size() - i - 1; j++) {
        Athlete flagAthlete = (Athlete) descendingOrder.get(j);
        Athlete nowAthlete = (Athlete) descendingOrder.get(j + 1);
        if (flagAthlete.getResult() < nowAthlete.getResult()) {
          Collections.swap(descendingOrder, j, j + 1);
        }
      }
    }
    int athleteNum = descendingOrder.size();
    Map<Integer, Map<PhysicalObject, Integer>> order = new HashMap<Integer, Map<PhysicalObject, Integer>>();
    int k;
    if (tracknums % 2 == 0) {// 轨道数为偶数
      k = tracknums / 2;
    } else {// 轨道数为奇数
      k = tracknums / 2 + 1;
    }
    int i = k;
    int j = k;
    int n;
    for (n = tracknums; n <= athleteNum; n = n + tracknums) {
      int flag = 0;
      for (int m = n - 1; m >= n - tracknums; m--) {
        if (m == 9) {
          int x = 0;
        }
        int group = m / tracknums;
        if (flag == 0) {
          i = k;
          j = k;
          order.put(group, new HashMap<>());
        }
        flag = 1;
        int h = m % tracknums;
        if (h >= 0) {
          if (h % 2 == 1) {
            j++;
            order.get(group).put(descendingOrder.get(m), j);
          } else {
            order.get(group).put(descendingOrder.get(m), i);
            i--;
          }
        }
      }
    }
    int flag = 0;
    for (int m = athleteNum - 1; m >= n - tracknums; m--) {
      int group = m / tracknums;
      if (flag == 0) {
        i = k;
        j = k;
        order.put(group, new HashMap<>());
      }
      flag = 1;
      int h = m % tracknums;
      if (h >= 0) {
        if (h % 2 == 1) {
          j++;
          order.get(group).put(descendingOrder.get(m), j);
        } else {
          order.get(group).put(descendingOrder.get(m), i);
          i--;
        }
      }
    }
    int nn = (int) Math.ceil(allAthlete.size() / (double) tracknums);
    assert order.size() == nn;
    return order;
  }

}
