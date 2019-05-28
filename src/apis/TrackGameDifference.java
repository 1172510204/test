package apis;

import java.util.HashMap;
import java.util.Map;

public class TrackGameDifference extends Difference {

  // AF
  // PersonalAppEcosystemDifference��������tracknums��trackNumberAndObjectsDif��������trackNumberAndName
  // RI
  // trackNumberAndName��Ϊ��
  // Safety from rep exposure:
  // all the fields are private
  // tracknums�ǲ��ɱ�� trackNumberAndObjectsDif��trackNumberAndName�ǿɱ�ģ��������Կ���
  private Map<Integer, String> trackNumberAndName = new HashMap<>();// ÿ���������źͶ�Ӧ�������Ƶ�����

  @Override
  public void checkRep() {
    super.checkRep();
    assert trackNumberAndName != null;
  }

  /**
   * ��ʼ��ÿ���������źͶ�Ӧ�������Ƶ�����
   * 
   * @param trackNumberAndName ÿ���������źͶ�Ӧ�������Ƶ�����
   */
  @Override
  public void setTrackNumberAndName(Map<Integer, String> trackNumberAndName) {
    this.trackNumberAndName = trackNumberAndName;
  }

  /**
   * ��ȡÿ���������źͶ�Ӧ�������Ƶ�����
   * 
   * @return a map key�ǹ���� value�Ǵ˹���϶�Ӧ���������
   */
  @Override
  public Map<Integer, String> getTrackNumberAndName() {
    checkRep();
    return new HashMap<>(trackNumberAndName);
  }

}
