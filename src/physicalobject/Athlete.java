package physicalobject;

public class Athlete extends PhysicalObject {

  // AF
  // ����PhysicalObject�е�
  // �������˶�Ա�����֡����롢�������������óɼ�
  // RI
  // all fields ����Ϊ��
  // Safety from rep exposure:
  // ����PhysicalObject�е�
  // all the fields are private�����ǲ��ɱ��
  private final String name;
  private final int num;
  private final String nation;
  private final int age;
  private final double result;

  /**
   * ��ʼ���˶�Ա
   * 
   * @param name �˶�Ա������
   * @param num ����
   * @param nation ����
   * @param age ����
   * @param result ��óɼ� �������ԣ�assert�ж�pre-conditions��Ϊ��
   */
  public Athlete(String name, int num, String nation, int age, double result) {
    assert name != null;
    assert nation != null;
    assert age > 0;
    assert result > 0;
    addName(name);
    this.name = name;
    this.num = num;
    this.nation = nation;
    this.age = age;
    this.result = result;
  }

  /**
   * ��ȡ���˶�Ա����óɼ�
   * 
   * @return �˶�Ա����óɼ�
   */
  public double getResult() {
    return result;
  }

}
