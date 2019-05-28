package centralobject;

public class Person extends CentralObject {
  private final String username;

  /**
   * .初始化person
   * 
   * @param username 人的名字 防御策略：assert判断pre-conditions
   */
  public Person(String username) {
    assert username != null;
    this.username = username;
  }

  /**
   * 获取人的名字
   * 
   * @return 人的名字 防御策略：assert判断post-conditions
   */
  /*
   * public String getUsername() { assert username!=null; return username; }
   */
}
