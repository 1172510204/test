package applications;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import physicalobject.PhysicalObject;

public class Originator {

  // AF
  // Memento包含原子的一个多轨道系统
  // RI
  // atom不为空
  // Safety from rep exposure:
  // all the fields are private
  // AtomStructure是不可变的
  private AtomStructure<CentralObject, PhysicalObject> atom;

  /**
   * 初始化原子的多轨道系统
   * 
   * @param atom 给定的原子的多轨道系统
   */
  public void setAtom(AtomStructure<CentralObject, PhysicalObject> atom) {
    this.atom = atom;
  }

  /**
   * 获取原子的多轨道系统
   * 
   * @return 原子的多轨道系统
   */
  /*
   * public AtomStructure<CentralObject, PhysicalObject> getAtom() { return atom;
   * }
   */

  /**
   * new一个新的Memento状态
   * 
   * @return 这个新的Memento状态
   */
  public Memento saveStateToMemento() {
    return new Memento(atom);
  }

  /**
   * 改变这个类的原子的多轨道系统
   * 
   * @param Memento 想获得的原子的多轨道系统的状态
   */
  /*
   * public void getStateFromMemento(Memento Memento){ atom = Memento.getState();
   * }
   */
}
