package physicalobject;

public class Electron extends PhysicalObject {

  // AF
  // ����PhysicalObject�е�
  // ���������ӵ����֡��������ڵĹ��
  // RI
  // ����Ϊ��
  // Safety from rep exposure:
  // ����PhysicalObject�е�
  // all the fields are private�����ǲ��ɱ��
  private final String name = "electron";
  private final int n;

  /**
   * ��ʼ������
   * 
   * @param n �������ڵĹ��
   */
  public Electron(int n) {
    addName("electron");
    this.n = n;
  }

}
