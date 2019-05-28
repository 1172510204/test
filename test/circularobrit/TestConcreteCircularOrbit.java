package circularobrit;

import static org.junit.Assert.assertEquals;

import circularorbit.CircularOrbit;
import circularorbit.ConcreteCircularOrbit;
import org.junit.Test;
import track.Track;

public class TestConcreteCircularOrbit extends TestCircularOrbit {

  // Testing strategy

  // testTransit()
  // 测试电子跃迁后每个轨道的电子个数是否正确

  @Override
  public CircularOrbit<String, String> emptyInstance() {
    return new ConcreteCircularOrbit<>();
  }

  @Test
  public void testTransit() {
    ConcreteCircularOrbit<String, String> circularorbit = new ConcreteCircularOrbit<>();
    circularorbit.addTrack(1, 1);
    circularorbit.addTrack(2, 2);
    circularorbit.addTrackObject(1, 1, "aa");
    circularorbit.addTrackObject(1, 1, "dd");
    circularorbit.addTrackObject(1, 1, "bb");
    circularorbit.addTrackObject(2, 2, "cc");
    circularorbit.transit("bb", 2, 2);
    circularorbit.transit("dd", 3, 3);
    for (Track each : circularorbit.getCircles().keySet()) {
      Track track = each;
      if (track.getR() == 1) {
        assertEquals(1, circularorbit.getCircles().get(track).size());
      } else if (track.getR() == 2) {
        assertEquals(2, circularorbit.getCircles().get(track).size());
      }
    }
  }
}
