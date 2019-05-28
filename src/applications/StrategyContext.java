package applications;

import java.util.List;
import java.util.Map;
import physicalobject.PhysicalObject;

public class StrategyContext {

  private Strategy strategy;

  /**
   * ��ʼ������
   * 
   * @param strategy ѡ��ı��ű����Ĳ��� �������ԣ�assert�ж�pre-condition��������ȷ
   */
  public StrategyContext(Strategy strategy) {
    boolean a1 = strategy instanceof Score;
    boolean a2 = strategy instanceof Strategy;
    assert a1 || a2 : "ѡ��Ĳ��Բ���ȷ";
    this.strategy = strategy;
  }

  // �� �˶�Ա�������ܵ���
  /**
   * ���ñ��ű����Ĳ���
   * 
   * @param allAthlete a list ��������Ҫ�������˶�Ա
   * @param tracknums  �������ܵ���Ŀ
   * @return a map key ���� value a map key�˶�Ա value �ܵ����
   */
  public Map<Integer, Map<PhysicalObject, Integer>> choose(List<PhysicalObject> allAthlete,
      int tracknums) {
    return strategy.competitionOrder(allAthlete, tracknums);
  }
}
