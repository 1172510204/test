package applications;

import java.util.List;
import java.util.Map;
import physicalobject.PhysicalObject;

public class StrategyContext {

  private Strategy strategy;

  /**
   * 初始化策略
   * 
   * @param strategy 选择的编排比赛的策略 防御策略：assert判断pre-condition的类型正确
   */
  public StrategyContext(Strategy strategy) {
    boolean a1 = strategy instanceof Score;
    boolean a2 = strategy instanceof Strategy;
    assert a1 || a2 : "选择的策略不正确";
    this.strategy = strategy;
  }

  // 组 运动员，所在跑道数
  /**
   * 调用编排比赛的策略
   * 
   * @param allAthlete a list 包含所有要比赛的运动员
   * @param tracknums  比赛的跑道数目
   * @return a map key 组数 value a map key运动员 value 跑道编号
   */
  public Map<Integer, Map<PhysicalObject, Integer>> choose(List<PhysicalObject> allAthlete,
      int tracknums) {
    return strategy.competitionOrder(allAthlete, tracknums);
  }
}
