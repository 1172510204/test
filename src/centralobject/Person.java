package centralobject;

public class Person extends CentralObject {
  private final String username;

  /**
   * .��ʼ��person
   * 
   * @param username �˵����� �������ԣ�assert�ж�pre-conditions
   */
  public Person(String username) {
    assert username != null;
    this.username = username;
  }

  /**
   * ��ȡ�˵�����
   * 
   * @return �˵����� �������ԣ�assert�ж�post-conditions
   */
  /*
   * public String getUsername() { assert username!=null; return username; }
   */
}
