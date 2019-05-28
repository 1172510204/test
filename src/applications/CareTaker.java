package applications;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

  // AF
  // CareTaker包含所有的memento
  // RI
  // mementoList不为空
  // Safety from rep exposure:
  // all the fields are private
  // 虽然mementoList是可变的，但是没有get mementoList的方法
  private List<Memento> mementoList = new ArrayList<>();

  /**
   * 向备忘录中添加状态
   * 
   * @param state Memento的一个状态（内含一个多轨道系统）
   */
  public void add(Memento state) {
    mementoList.add(state);
  }

  /**
   * 获取一个Memento（内含一个多轨道系统）
   * 
   * @param index 要获取的多轨道系统在List中的标号
   * @return Memento（内含一个多轨道系统）
   */
  public Memento get(int index) {
    return mementoList.get(index);
  }
}
