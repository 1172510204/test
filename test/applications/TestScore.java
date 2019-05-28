package applications;

import static org.junit.Assert.assertEquals;

import centralobject.CentralObject;
import circularorbit.CircularOrbit;
import circularorbit.TrackGame;
import java.util.Map;
import org.junit.Test;
import physicalobject.PhysicalObject;

public class TestScore {

  // Testing strategy
  // testCompetitionOrder()
  // �����˶�Ա�Ƿ���Ӧ���ڵ��ܵ���
  @Test
  public void testCompetitionOrder() {
    // �ܵ�����Ϊ����
    CircularOrbit<CentralObject, PhysicalObject> c = new TrackGame<CentralObject, PhysicalObject>();
    try {
      c.readFile("src/txt/TrackGame.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    StrategyContext strategyContext1 = new StrategyContext(new Score());
    TrackGame<CentralObject, PhysicalObject> trackgame8 =
        (TrackGame<CentralObject, PhysicalObject>) c;
    Map<Integer, Map<PhysicalObject, Integer>> group =
        strategyContext1.choose(trackgame8.getAllObjects(), trackgame8.getTracknums());
    for (PhysicalObject each : group.get(1).keySet()) {
      if (each.getName().equals("Chen")) {
        int j = group.get(1).get(each);
        assertEquals(1, j);
      }
    }

    // �ܵ�����Ϊż��
    CircularOrbit<CentralObject, PhysicalObject> p = new TrackGame<CentralObject, PhysicalObject>();
    try {
      p.readFile("src/txt/TGEvenTrackNums.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    StrategyContext strategyContext2 = new StrategyContext(new Score());
    TrackGame<CentralObject, PhysicalObject> trackgame81 =
        (TrackGame<CentralObject, PhysicalObject>) p;
    Map<Integer, Map<PhysicalObject, Integer>> group1 =
        strategyContext2.choose(trackgame81.getAllObjects(), trackgame81.getTracknums());
    for (PhysicalObject each : group1.get(1).keySet()) {
      if (each.getName().equals("Ronaldo")) {
        int j = group1.get(3).get(each);
        assertEquals(3, j);
      }
    }
  }

}
