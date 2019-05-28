package physicalobject;

public abstract class PhysicalObjectFactory {

  /**
   * new TrackGame
   * 
   * @param name 运动员的名字
   * @param num 号码
   * @param nation 国籍
   * @param age 年龄
   * @param result 最好成绩
   * @return 轨道物体（运动员）
   */
  public abstract PhysicalObject createPhysicalObject1(String name, int num, String nation, int age,
      double result);

  /**
   * new AtomStructure
   * 
   * @param n 电子所在的轨道
   * @return 轨道物体（电子）
   */
  public abstract PhysicalObject createPhysicalObject3(int n);

  /**
   * new PersonalAppEcosystem
   * 
   * @param appname APP的名字
   * @param company APP的公司
   * @param version APP的版本
   * @param function APP的功能
   * @param field APP的使用方面
   * @return 轨道物体（APP）
   */
  public abstract PhysicalObject createPhysicalObject4(String appname, String company,
      String version, String function, String field);
}
