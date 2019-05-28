package physicalobject;

public class PhysicalPersonalAppEcosystemFactory extends PhysicalObjectFactory {

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
  @Override
  public PhysicalObject createPhysicalObject4(String appname, String company, String version,
      String function, String field) {
    return new App(appname, company, version, function, field);
  }

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
  @Override
  public PhysicalObject createPhysicalObject1(String name, int num, String nation, int age,
      double result) {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * new AtomStructure
   * 
   * @param n �������ڵĹ��
   * @return ������壨���ӣ�
   */
  @Override
  public PhysicalObject createPhysicalObject3(int n) {
    // TODO Auto-generated method stub
    return null;
  }

}
