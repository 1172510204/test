package apis;

import java.util.HashMap;
import java.util.Map;

public abstract class Difference {

  // AF
  // difference������������Ĳ����ÿ���������źͶ�Ӧ���������Ĳ���
  // RI
  // trackNumberAndObjectsDif��Ϊ��
  // Safety from rep exposure:
  // all the fields are private
  // tracknums�ǲ��ɱ�� trackNumberAndObjectsDif�ǿɱ�ģ��������Կ���
  private int tracknums;// �����������
  private Map<Integer, Integer> trackNumberAndObjectsDif = new HashMap<>();// ÿ���������źͶ�Ӧ���������Ĳ���
  // private Map<Integer, String> trackNumberAndName=new
  // HashMap<>();//ÿ���������źͶ�Ӧ�������Ƶ�����

  public void checkRep() {
    assert trackNumberAndObjectsDif != null;
  }

  /**
   * ��ʼ�������������
   * 
   * @param n �����������
   */
  public void setTracknums(int n) {
    tracknums = n;
  }

  /**
   * ��ʼ��ÿ���������źͶ�Ӧ���������Ĳ���
   * 
   * @param trackNumberAndObjectsDif ÿ���������źͶ�Ӧ���������Ĳ���
   */
  public void setTrackNumberAndObjectsDif(Map<Integer, Integer> trackNumberAndObjectsDif) {
    this.trackNumberAndObjectsDif = trackNumberAndObjectsDif;
  }

  /**
   * ��ȡ��������Ĳ���
   * 
   * @return ��������Ĳ���
   */
  public int getTrackNums() {
    checkRep();
    return tracknums;
  }

  /**
   * ��ȡÿ���������źͶ�Ӧ���������Ĳ���
   * 
   * @return key�ǹ���� value�Ǵ˹����Ӧ���������
   */
  public Map<Integer, Integer> getTrackNumberAndObjectsDif() {
    return new HashMap<>(trackNumberAndObjectsDif);
  }

  /**
   * ��ʼ��ÿ���������źͶ�Ӧ�������Ƶ�����
   * 
   * @param trackNumberAndName ÿ���������źͶ�Ӧ�������Ƶ�����
   */
  public abstract void setTrackNumberAndName(Map<Integer, String> trackNumberAndName);

  /**
   * ��ȡÿ���������źͶ�Ӧ�������Ƶ�����
   * 
   * @return a map key�ǹ���� value�Ǵ˹���϶�Ӧ���������
   */
  public abstract Map<Integer, String> getTrackNumberAndName();

}
