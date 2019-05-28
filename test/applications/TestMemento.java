package applications;

import static org.junit.Assert.assertEquals;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import org.junit.Test;
import physicalobject.PhysicalObject;
import track.Track;

public class TestMemento {
  @Test
  public void testmemento() {
    Originator originator = new Originator();
    CareTaker careTaker = new CareTaker();
    AtomStructure<CentralObject, PhysicalObject> a1 = new AtomStructure<>();
    try {
      a1.readFile("src/txt/AtomicStructure.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    originator.setAtom(a1);
    careTaker.add(originator.saveStateToMemento());


    AtomStructure<CentralObject, PhysicalObject> a2 = a1.copyAS();
    for (Track track : a2.getCircles().keySet()) {
      if (track.getR() == 1) {
        if (a2.getCircles().get(track).size() != 0) {
          PhysicalObject ele = a2.getCircles().get(track).get(0);
          a2.transit(ele, 2, 2);
          break;
        } else {
          System.out.println("原来的轨道上不存在电子");
        }
      }
    }
    originator.setAtom(a2);
    careTaker.add(originator.saveStateToMemento());


    AtomStructure<CentralObject, PhysicalObject> a3 = a2.copyAS();
    a3.changeCircles(a2.getCircles());
    for (Track track : a3.getCircles().keySet()) {
      if (track.getR() == 3) {
        if (a3.getCircles().get(track).size() != 0) {
          PhysicalObject ele = a3.getCircles().get(track).get(0);
          a3.transit(ele, 4, 4);
          break;
        } else {
          System.out.println("原来的轨道上不存在电子");
        }
      }
    }
    originator.setAtom(a3);
    careTaker.add(originator.saveStateToMemento());
    for (Track track : careTaker.get(0).getState().getCircles().keySet()) {
      if (track.getR() == 2) {
        assertEquals(8, careTaker.get(0).getState().getCircles().get(track).size());
      }
    }
    for (Track track : careTaker.get(1).getState().getCircles().keySet()) {
      if (track.getR() == 2) {
        assertEquals(9, careTaker.get(1).getState().getCircles().get(track).size());
      }
      if (track.getR() == 1) {
        assertEquals(1, careTaker.get(1).getState().getCircles().get(track).size());
      }
      if (track.getR() == 3) {
        assertEquals(18, careTaker.get(1).getState().getCircles().get(track).size());
      }
      if (track.getR() == 4) {
        assertEquals(8, careTaker.get(1).getState().getCircles().get(track).size());
      }
    }
    for (Track track : careTaker.get(2).getState().getCircles().keySet()) {
      if (track.getR() == 2) {
        assertEquals(9, careTaker.get(2).getState().getCircles().get(track).size());
      }
      if (track.getR() == 1) {
        assertEquals(1, careTaker.get(2).getState().getCircles().get(track).size());
      }
      if (track.getR() == 3) {
        assertEquals(17, careTaker.get(2).getState().getCircles().get(track).size());
      }
      if (track.getR() == 4) {
        assertEquals(9, careTaker.get(2).getState().getCircles().get(track).size());
      }
    }
  }

}
