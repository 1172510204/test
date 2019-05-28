package physicalobject;

public class App extends PhysicalObject {

  // AF
  // ����PhysicalObject�е�
  // ������APP�����֡���˾���汾�����ܺ�ʹ�÷���
  // RI
  // all fields����Ϊ��
  // Safety from rep exposure:
  // ����PhysicalObject�е�
  // all the fields are private�����ǲ��ɱ��
  private final String appname;
  private final String company;
  private final String version;
  private final String function;
  private final String field;

  /**
   * ��ʼ��APP
   * 
   * @param appname APP������
   * @param company APP�Ĺ�˾
   * @param version APP�İ汾
   * @param function APP�Ĺ���
   * @param field APP��ʹ�÷��� �������ԣ�assert�ж�pre-conditions��Ϊ��
   */
  public App(String appname, String company, String version, String function, String field) {
    assert appname != null;
    assert company != null;
    assert version != null;
    assert function != null;
    assert field != null;
    addName(appname);
    this.appname = appname;
    this.company = company;
    this.version = version;
    this.function = function;
    this.field = field;
  }

  /**
   * ��ȡAPP������
   * 
   * @return string APP������
   */
  public String getAppName() {
    return appname;
  }

}
