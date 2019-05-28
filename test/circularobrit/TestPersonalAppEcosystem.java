package circularobrit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import centralobject.CentralObject;
import circularorbit.PersonalAppEcosystem;
import exception.DependIncorrectException;
import exception.SameLabelException;
import org.junit.Test;
import physicalobject.PhysicalObject;
import track.Track;

public class TestPersonalAppEcosystem {


  // Testing strategy

  // testReadFileAndPutPhysicalObjectOnCircles()
  // 测试根据规定的时间段看APP是否在正确的轨道上
  @Test
  public void testReadFileAndPutPhysicalObjectOnCircles() {
    PersonalAppEcosystem<CentralObject, PhysicalObject> personalappecosystem =
        new PersonalAppEcosystem<>();
    PersonalAppEcosystem<CentralObject, PhysicalObject> personalappecosystem1 =
        new PersonalAppEcosystem<>();
    PersonalAppEcosystem<CentralObject, PhysicalObject> personalappecosystem2 =
        new PersonalAppEcosystem<>();
    PersonalAppEcosystem<CentralObject, PhysicalObject> personalappecosystem3 =
        new PersonalAppEcosystem<>();
    PersonalAppEcosystem<CentralObject, PhysicalObject> personalappecosystem4 =
        new PersonalAppEcosystem<>();
    PersonalAppEcosystem<CentralObject, PhysicalObject> personalappecosystem5 =
        new PersonalAppEcosystem<>();
    try {
      personalappecosystem.readFile("src/txt/PersonalAppEcosystem.txt");
      personalappecosystem1.readFile("src/txt/PAEReapeatedInstall.txt");
      personalappecosystem2.readFile("src/txt/PAEReapeatedUninstall.txt");
      personalappecosystem3.readFile("src/txt/PAEPeriodMonth.txt");
      personalappecosystem4.readFile("src/txt/PAEPeriodWeek.txt");
      personalappecosystem5.readFile("src/txt/PAEPeriodHour.txt");
    } catch (SameLabelException | DependIncorrectException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    personalappecosystem.putPhysicalObjectOnCircles("2019-01-02");
    personalappecosystem3.putPhysicalObjectOnCircles("2019-01");
    personalappecosystem3.putPhysicalObjectOnCircles("01-02");
    personalappecosystem4.putPhysicalObjectOnCircles("2019-01-02");
    personalappecosystem4.putPhysicalObjectOnCircles("2018-12-30");
    personalappecosystem5.putPhysicalObjectOnCircles("2019-01-01 15-16");
    personalappecosystem5.putPhysicalObjectOnCircles("2019-01-01");
    assertEquals("Day", personalappecosystem.getPeriod());
    for (Track each : personalappecosystem.getCircles().keySet()) {
      for (PhysicalObject app : personalappecosystem.getCircles().get(each)) {
        assertFalse(app.getName().equals("Eleme"));
        assertFalse(app.getName().equals("Didi"));
        if (app.getName().equals("Wechat")) {
          assertEquals(9, each.getR());
        } else if (app.getName().equals("QQ")) {
          assertEquals(1, each.getR());
        } else if (app.getName().equals("Weibo")) {
          assertEquals(10, each.getR());
        } else if (app.getName().equals("BaiduMap")) {
          assertEquals(10, each.getR());
        }
      }
    }
  }
}
