package physicalobject;

public class Electron extends PhysicalObject {

  // AF
  // 除了PhysicalObject中的
  // 还包含电子的名字、电子所在的轨道
  // RI
  // 都不为空
  // Safety from rep exposure:
  // 除了PhysicalObject中的
  // all the fields are private，都是不可变的
  private final String name = "electron";
  private final int n;

  /**
   * 初始化电子
   * 
   * @param n 电子所在的轨道
   */
  public Electron(int n) {
    addName("electron");
    this.n = n;
  }

}
