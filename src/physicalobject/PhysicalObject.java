package physicalobject;

public abstract class PhysicalObject {

  // AF
  // ����������������
  // RI
  // ���ֲ�Ϊ��
  // Safety from rep exposure:
  // all the fields are private
  // name�ǲ��ɱ��
  private String name;

  /**
   * ��ӹ�����������
   * 
   * @param name ��������� �������ԣ�assert�ж�pre-conditions
   */
  protected void addName(String name) {
    assert name != null;
    this.name = name;
  }

  /**
   * ��ȡ������������
   * 
   * @return string ��������� �������ԣ�assert�ж�post-conditions
   */
  public String getName() {
    assert name != null;
    return name;
  }
}
