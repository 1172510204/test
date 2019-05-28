package circularobrit;

import static org.junit.Assert.assertEquals;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import exception.DependIncorrectException;
import exception.OutRules.ElementException;
import exception.SameLabelException;
import org.junit.Test;
import physicalobject.PhysicalObject;

public class TestAtomStructure {


  // Testing strategy

  // testReadFile()
  // 测试是否能正确读取元素名称和轨道个数
  @Test
  public void testReadFile() {
    AtomStructure<CentralObject, PhysicalObject> atomstructure = new AtomStructure<>();
    try {
      atomstructure.readFile("src/txt/AtomicStructure.txt");
    } catch (SameLabelException | DependIncorrectException | ElementException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals("Rb", atomstructure.getElement());
    assertEquals(5, atomstructure.getTrackNum());
  }

  @Test
  public void testAddTrack() {
    AtomStructure<CentralObject, PhysicalObject> atomstructure = new AtomStructure<>();
    try {
      atomstructure.readFile("src/txt/AtomicStructure.txt");
    } catch (SameLabelException | DependIncorrectException | ElementException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    atomstructure.addTrack(10, 10);
    assertEquals(6, atomstructure.getCircles().size());
  }

}
