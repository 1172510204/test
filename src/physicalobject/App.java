package physicalobject;

public class App extends PhysicalObject {

  // AF
  // 除了PhysicalObject中的
  // 还包含APP的名字、公司、版本、功能和使用方面
  // RI
  // all fields都不为空
  // Safety from rep exposure:
  // 除了PhysicalObject中的
  // all the fields are private，都是不可变的
  private final String appname;
  private final String company;
  private final String version;
  private final String function;
  private final String field;

  /**
   * 初始化APP
   * 
   * @param appname APP的名字
   * @param company APP的公司
   * @param version APP的版本
   * @param function APP的功能
   * @param field APP的使用方面 防御策略：assert判断pre-conditions不为空
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
   * 获取APP的名字
   * 
   * @return string APP的名字
   */
  public String getAppName() {
    return appname;
  }

}
