package applications;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import physicalobject.PhysicalObject;

public class Memento {

  // AF
  // Memento����ԭ�ӵ�һ������ϵͳ
  // RI
  // atom��Ϊ��
  // Safety from rep exposure:
  // all the fields are private
  // AtomStructure�ǲ��ɱ��
  private AtomStructure<CentralObject, PhysicalObject> atom;

  /**
   * ��ʼ��Memento
   * 
   * @param atom ԭ�ӵ�һ������ϵͳ
   */
  public Memento(AtomStructure<CentralObject, PhysicalObject> atom) {
    this.atom = atom;
  }

  /**
   * ��ȡԭ�ӵĶ���ϵͳ
   * 
   * @return ԭ�ӵĶ���ϵͳ
   */
  public AtomStructure<CentralObject, PhysicalObject> getState() {
    return atom;
  }
}
