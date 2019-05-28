package circularobrit;

import static org.junit.Assert.assertEquals;

import centralobject.CentralObject;
import circularorbit.TrackGame;
import exception.OutRules.DecimalsException;
import exception.OutRules.InformationNumIncorrectException;
import exception.OutRules.MetresFalseException;
import exception.OutRules.NotCaptialLetterException;
import exception.OutRules.TransposeException;
import exception.SameLabelException;
import org.junit.Test;
import physicalobject.PhysicalObject;

public class TestTrackGame extends TestConcreteCircularOrbit {

  // Testing strategy

  // testReadFile()
  // 测试能否读取到比赛正确的米数和轨道数

  @Test
  public void testReadFile() {
    TrackGame<CentralObject, PhysicalObject> trackgame = new TrackGame<>();
    try {
      trackgame.readFile("src/txt/TrackGame.txt");
    } catch (MetresFalseException | InformationNumIncorrectException | TransposeException
        | NotCaptialLetterException | DecimalsException | SameLabelException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    assertEquals(100, trackgame.getMetres());
    assertEquals(5, trackgame.getTracknums());

  }

}
