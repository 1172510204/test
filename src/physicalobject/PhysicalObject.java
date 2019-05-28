package physicalobject;

public abstract class PhysicalObject {

  // AF
  // 包含轨道物体的名字
  // RI
  // 名字不为空
  // Safety from rep exposure:
  // all the fields are private
  // name是不可变的
  private String name;

  /**
   * 添加轨道物体的名字
   * 
   * @param name 物体的名字 防御策略：assert判断pre-conditions
   */
  protected void addName(String name) {
    assert name != null;
    this.name = name;
  }

  /**
   * 获取轨道物体的名字
   * 
   * @return string 物体的名字 防御策略：assert判断post-conditions
   */
  public String getName() {
    assert name != null;
    return name;
  }
}
