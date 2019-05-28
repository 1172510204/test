package applications;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import physicalobject.PhysicalObject;

public class Memento {

  // AF
  // Memento包含原子的一个多轨道系统
  // RI
  // atom不为空
  // Safety from rep exposure:
  // all the fields are private
  // AtomStructure是不可变的
  private AtomStructure<CentralObject, PhysicalObject> atom;

  /**
   * 初始化Memento
   * 
   * @param atom 原子的一个多轨道系统
   */
  public Memento(AtomStructure<CentralObject, PhysicalObject> atom) {
    this.atom = atom;
  }

  /**
   * 获取原子的多轨道系统
   * 
   * @return 原子的多轨道系统
   */
  public AtomStructure<CentralObject, PhysicalObject> getState() {
    return atom;
  }
}
