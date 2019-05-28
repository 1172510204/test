package physicalobject;

public abstract class PhysicalObjectFactory {

  /**
   * new TrackGame
   * 
   * @param name �˶�Ա������
   * @param num ����
   * @param nation ����
   * @param age ����
   * @param result ��óɼ�
   * @return ������壨�˶�Ա��
   */
  public abstract PhysicalObject createPhysicalObject1(String name, int num, String nation, int age,
      double result);

  /**
   * new AtomStructure
   * 
   * @param n �������ڵĹ��
   * @return ������壨���ӣ�
   */
  public abstract PhysicalObject createPhysicalObject3(int n);

  /**
   * new PersonalAppEcosystem
   * 
   * @param appname APP������
   * @param company APP�Ĺ�˾
   * @param version APP�İ汾
   * @param function APP�Ĺ���
   * @param field APP��ʹ�÷���
   * @return ������壨APP��
   */
  public abstract PhysicalObject createPhysicalObject4(String appname, String company,
      String version, String function, String field);
}
