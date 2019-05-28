package applications;

import static org.junit.Assert.assertEquals;

import centralobject.CentralObject;
import circularorbit.CircularOrbit;
import circularorbit.TrackGame;
import java.util.Map;
import org.junit.Test;
import physicalobject.PhysicalObject;

public class TestRandom {

  // Testing strategy
  // testCompetitionOrder()
  // 测试运动员被分成的组数
  @Test
  public void testCompetitionOrder() {
    CircularOrbit<CentralObject, PhysicalObject> c = new TrackGame<CentralObject, PhysicalObject>();
    try {
      c.readFile("src/txt/TrackGame.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    StrategyContext strategyContext2 = new StrategyContext(new Random());
    TrackGame<CentralObject, PhysicalObject> trackgame8 =
        (TrackGame<CentralObject, PhysicalObject>) c;
    Map<Integer, Map<PhysicalObject, Integer>> group =
        strategyContext2.choose(trackgame8.getAllObjects(), trackgame8.getTracknums());
    assertEquals(3, group.size());
  }
}
