package applications;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {

  // AF
  // CareTaker�������е�memento
  // RI
  // mementoList��Ϊ��
  // Safety from rep exposure:
  // all the fields are private
  // ��ȻmementoList�ǿɱ�ģ�����û��get mementoList�ķ���
  private List<Memento> mementoList = new ArrayList<>();

  /**
   * ����¼�����״̬
   * 
   * @param state Memento��һ��״̬���ں�һ������ϵͳ��
   */
  public void add(Memento state) {
    mementoList.add(state);
  }

  /**
   * ��ȡһ��Memento���ں�һ������ϵͳ��
   * 
   * @param index Ҫ��ȡ�Ķ���ϵͳ��List�еı��
   * @return Memento���ں�һ������ϵͳ��
   */
  public Memento get(int index) {
    return mementoList.get(index);
  }
}
