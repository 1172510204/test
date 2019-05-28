package physicalobject;

public class Athlete extends PhysicalObject {

  // AF
  // 除了PhysicalObject中的
  // 还包含运动员的名字、号码、国籍、年龄和最好成绩
  // RI
  // all fields 都不为空
  // Safety from rep exposure:
  // 除了PhysicalObject中的
  // all the fields are private，都是不可变的
  private final String name;
  private final int num;
  private final String nation;
  private final int age;
  private final double result;

  /**
   * 初始化运动员
   * 
   * @param name 运动员的名字
   * @param num 号码
   * @param nation 国籍
   * @param age 年龄
   * @param result 最好成绩 防御策略：assert判断pre-conditions不为空
   */
  public Athlete(String name, int num, String nation, int age, double result) {
    assert name != null;
    assert nation != null;
    assert age > 0;
    assert result > 0;
    addName(name);
    this.name = name;
    this.num = num;
    this.nation = nation;
    this.age = age;
    this.result = result;
  }

  /**
   * 获取该运动员的最好成绩
   * 
   * @return 运动员的最好成绩
   */
  public double getResult() {
    return result;
  }

}
