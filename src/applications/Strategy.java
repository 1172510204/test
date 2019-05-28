package applications;

import java.util.List;
import java.util.Map;
import physicalobject.PhysicalObject;

public interface Strategy {
  /**
   * ���ű�������
   * 
   * @param allAthlete a list ��������Ҫ�������˶�Ա
   * @param tracknums  �������ܵ���Ŀ
   * @return a map key ���� value a map key�˶�Ա value �ܵ����
   */
  public Map<Integer, Map<PhysicalObject, Integer>> competitionOrder(
      List<PhysicalObject> allAthlete, int tracknums);
  // map< ������map<�˶�Ա,�ܵ����> >
}
