package applications;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import physicalobject.PhysicalObject;

public class Originator {

  // AF
  // Memento����ԭ�ӵ�һ������ϵͳ
  // RI
  // atom��Ϊ��
  // Safety from rep exposure:
  // all the fields are private
  // AtomStructure�ǲ��ɱ��
  private AtomStructure<CentralObject, PhysicalObject> atom;

  /**
   * ��ʼ��ԭ�ӵĶ���ϵͳ
   * 
   * @param atom ������ԭ�ӵĶ���ϵͳ
   */
  public void setAtom(AtomStructure<CentralObject, PhysicalObject> atom) {
    this.atom = atom;
  }

  /**
   * ��ȡԭ�ӵĶ���ϵͳ
   * 
   * @return ԭ�ӵĶ���ϵͳ
   */
  /*
   * public AtomStructure<CentralObject, PhysicalObject> getAtom() { return atom;
   * }
   */

  /**
   * newһ���µ�Memento״̬
   * 
   * @return ����µ�Memento״̬
   */
  public Memento saveStateToMemento() {
    return new Memento(atom);
  }

  /**
   * �ı�������ԭ�ӵĶ���ϵͳ
   * 
   * @param Memento ���õ�ԭ�ӵĶ���ϵͳ��״̬
   */
  /*
   * public void getStateFromMemento(Memento Memento){ atom = Memento.getState();
   * }
   */
}
