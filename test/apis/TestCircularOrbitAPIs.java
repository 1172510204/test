package apis;

import static org.junit.Assert.assertEquals;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.PersonalAppEcosystem;
import circularorbit.TrackGame;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.junit.Test;
import physicalobject.Athlete;
import physicalobject.PhysicalObject;

public class TestCircularOrbitAPIs {

  // Testing strategy

  // GetObjectDistributionEntropy()
  // 测试轨道上没有和有物体的时候的熵值

  // GetLogicalDistance()
  // 测试A和A自己的距离、A和没有关系物体的距离、A和直接有关系物体的距离、A和间接有关系物体的距离

  // GetDifference()
  // 测试三个应用对应的difference

  @Test
  public void testGetObjectDistributionEntropy() {
    CircularOrbit<CentralObject, PhysicalObject> trackgame = new TrackGame<>();
    try {
      trackgame.readFile("src/txt/TrackGame.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    CircularOrbitAPIs apis1 = new CircularOrbitAPIs();
    double x1 = apis1.getObjectDistributionEntropy(trackgame);
    assertEquals(0, x1, 0);

    PhysicalObject athlete1 = new Athlete("aa", 22, "UK", 18, 10.22);
    PhysicalObject athlete2 = new Athlete("bb", 23, "UK", 18, 10.22);
    PhysicalObject athlete3 = new Athlete("cc", 24, "UK", 18, 10.22);
    PhysicalObject athlete4 = new Athlete("dd", 25, "UK", 18, 10.22);
    PhysicalObject athlete5 = new Athlete("ff", 26, "UK", 18, 10.22);
    PhysicalObject athlete6 = new Athlete("ee", 27, "UK", 18, 10.22);
    trackgame.addTrackObject(1, 1, athlete1);
    trackgame.addTrackObject(2, 2, athlete2);
    trackgame.addTrackObject(3, 3, athlete3);
    trackgame.addTrackObject(4, 4, athlete4);
    trackgame.addTrackObject(0, 0, athlete5);
    trackgame.addTrackObject(0, 0, athlete6);
    double y1 = apis1.getObjectDistributionEntropy(trackgame);
    assertEquals(2.25, y1, 0.01);


    CircularOrbit<CentralObject, PhysicalObject> atomicstructure = new AtomStructure<>();
    try {
      atomicstructure.readFile("src/txt/AtomicStructure.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    CircularOrbitAPIs apis2 = new CircularOrbitAPIs();
    double x2 = apis2.getObjectDistributionEntropy(atomicstructure);
    assertEquals(1.83, x2, 0.01);


    CircularOrbit<CentralObject, PhysicalObject> personalappecosystem =
        new PersonalAppEcosystem<>();
    try {
      personalappecosystem.readFile("src/txt/PersonalAppEcosystem.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 =
        (PersonalAppEcosystem<CentralObject, PhysicalObject>) personalappecosystem;
    p1.putPhysicalObjectOnCircles("2019-01-02");
    CircularOrbitAPIs apis3 = new CircularOrbitAPIs();
    double x3 = apis3.getObjectDistributionEntropy(p1);
    assertEquals(1.50, x3, 0.01);

  }

  @Test
  public void testGetLogicalDistance() {
    CircularOrbit<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    try {
      p1.readFile("src/txt/PersonalAppEcosystem.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PersonalAppEcosystem<CentralObject, PhysicalObject> p2 =
        (PersonalAppEcosystem<CentralObject, PhysicalObject>) p1;
    List<PhysicalObject> all = p2.getAllObjects();
    PhysicalObject qq = null, wechat = null, eleme = null, didi = null;
    for (PhysicalObject each : all) {
      if (each.getName().equals("QQ"))
        qq = each;
      else if (each.getName().equals("Wechat"))
        wechat = each;
      else if (each.getName().equals("Eleme"))
        eleme = each;
      else if (each.getName().equals("Didi"))
        didi = each;
    }
    CircularOrbitAPIs apis = new CircularOrbitAPIs();
    assertEquals(1, apis.getLogicalDistance(p1, wechat, qq));
    assertEquals(0, apis.getLogicalDistance(p1, qq, qq));
    assertEquals(2, apis.getLogicalDistance(p1, eleme, qq));
    assertEquals(-1, apis.getLogicalDistance(p1, didi, qq));
  }

  @Test
  public void testGetDifference() {
    CircularOrbit<CentralObject, PhysicalObject> p11 = new TrackGame<>();
    CircularOrbit<CentralObject, PhysicalObject> p12 = new TrackGame<>();
    try {
      p11.readFile("src/txt/TrackGame.txt");
      p12.readFile("src/txt/TrackGame_Medium.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PhysicalObject athlete1 = new Athlete("aa", 22, "UK", 18, 10.22);
    PhysicalObject athlete2 = new Athlete("bb", 23, "UK", 18, 10.22);
    PhysicalObject athlete3 = new Athlete("cc", 24, "UK", 18, 10.22);
    PhysicalObject athlete4 = new Athlete("dd", 25, "UK", 18, 10.22);
    PhysicalObject athlete5 = new Athlete("ff", 26, "UK", 18, 10.22);
    PhysicalObject athlete6 = new Athlete("ee", 27, "UK", 18, 10.22);
    p11.addTrackObject(0, 0, athlete1);
    p11.addTrackObject(3, 3, athlete4);
    p11.addTrackObject(4, 4, athlete5);
    p12.addTrackObject(0, 0, athlete1);
    p12.addTrackObject(2, 2, athlete2);
    p12.addTrackObject(4, 4, athlete3);
    p12.addTrackObject(5, 5, athlete4);
    p12.addTrackObject(6, 6, athlete6);

    CircularOrbitAPIs apis = new CircularOrbitAPIs();
    Difference d1 = apis.getDifference(p11, p12);
    assertEquals(-2, d1.getTrackNums());
    Map<Integer, Integer> a = d1.getTrackNumberAndObjectsDif();
    for (Map.Entry<Integer, Integer> entry : a.entrySet()) {
      if (entry.getKey() == 0 || entry.getKey() == 1 || entry.getKey() == 4) {
        int aa = entry.getValue();
        assertEquals(0, aa);
      } else if (entry.getKey() == 2 || entry.getKey() == 5 || entry.getKey() == 6) {
        int aa = entry.getValue();
        assertEquals(-1, aa);
      } else if (entry.getKey() == 3) {
        int aa = entry.getValue();
        assertEquals(1, aa);
      }
    }
    Map<Integer, String> b = d1.getTrackNumberAndName();
    for (Entry<Integer, String> entry : b.entrySet()) {
      if (entry.getKey() == 0) {
        String aa = entry.getValue();
        assertEquals("无", aa);
      } else if (entry.getKey() == 1) {
        String aa = entry.getValue();
        assertEquals("无", aa);
      } else if (entry.getKey() == 2) {
        String aa = entry.getValue();
        assertEquals("无-bb", aa);
      } else if (entry.getKey() == 3) {
        String aa = entry.getValue();
        assertEquals("dd-无", aa);
      } else if (entry.getKey() == 4) {
        String aa = entry.getValue();
        assertEquals("ff-cc", aa);
      } else if (entry.getKey() == 5) {
        String aa = entry.getValue();
        assertEquals("无-dd", aa);
      } else if (entry.getKey() == 6) {
        String aa = entry.getValue();
        assertEquals("无-ee", aa);
      }
    }

    CircularOrbitAPIs apis11 = new CircularOrbitAPIs();
    Difference d11 = apis11.getDifference(p12, p11);
    assertEquals(2, d11.getTrackNums());
    Map<Integer, Integer> a11 = d11.getTrackNumberAndObjectsDif();
    for (Map.Entry<Integer, Integer> entry : a11.entrySet()) {
      if (entry.getKey() == 0 || entry.getKey() == 1 || entry.getKey() == 4) {
        int aa = entry.getValue();
        assertEquals(0, aa);
      } else if (entry.getKey() == 2 || entry.getKey() == 5 || entry.getKey() == 6) {
        int aa = entry.getValue();
        assertEquals(1, aa);
      } else if (entry.getKey() == 3) {
        int aa = entry.getValue();
        assertEquals(-1, aa);
      }
    }
    Map<Integer, String> b11 = d11.getTrackNumberAndName();
    for (Entry<Integer, String> entry : b11.entrySet()) {
      if (entry.getKey() == 0) {
        String aa = entry.getValue();
        assertEquals("无", aa);
      } else if (entry.getKey() == 1) {
        String aa = entry.getValue();
        assertEquals("无", aa);
      } else if (entry.getKey() == 2) {
        String aa = entry.getValue();
        assertEquals("bb-无", aa);
      } else if (entry.getKey() == 3) {
        String aa = entry.getValue();
        assertEquals("无-dd", aa);
      } else if (entry.getKey() == 4) {
        String aa = entry.getValue();
        assertEquals("cc-ff", aa);
      } else if (entry.getKey() == 5) {
        String aa = entry.getValue();
        assertEquals("dd", aa);
      } else if (entry.getKey() == 6) {
        String aa = entry.getValue();
        assertEquals("ee", aa);
      }
    }

    CircularOrbit<CentralObject, PhysicalObject> p21 = new AtomStructure<>();
    CircularOrbit<CentralObject, PhysicalObject> p22 = new AtomStructure<>();
    try {
      p21.readFile("src/txt/AtomicStructure.txt");
      p22.readFile("src/txt/AtomicStructure_Medium.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    CircularOrbitAPIs apis1 = new CircularOrbitAPIs();
    Difference d2 = apis1.getDifference(p21, p22);
    assertEquals(-1, d2.getTrackNums());
    Map<Integer, Integer> a1 = d2.getTrackNumberAndObjectsDif();
    for (Map.Entry<Integer, Integer> entry : a1.entrySet()) {
      if (entry.getKey() == 1 || entry.getKey() == 2 || entry.getKey() == 3) {
        int aa = entry.getValue();
        assertEquals(0, aa);
      } else if (entry.getKey() == 4) {
        int aa = entry.getValue();
        assertEquals(-22, aa);
      } else if (entry.getKey() == 5) {
        int aa = entry.getValue();
        assertEquals(-7, aa);
      } else if (entry.getKey() == 6) {
        int aa = entry.getValue();
        assertEquals(-2, aa);
      }
    }

    CircularOrbit<CentralObject, PhysicalObject> p31 = new PersonalAppEcosystem<>();
    CircularOrbit<CentralObject, PhysicalObject> p32 = new PersonalAppEcosystem<>();
    try {
      p31.readFile("src/txt/PersonalAppEcosystem.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PersonalAppEcosystem<CentralObject, PhysicalObject> p311 =
        (PersonalAppEcosystem<CentralObject, PhysicalObject>) p31;
    p311.putPhysicalObjectOnCircles("2019-01-02");
    p31 = p311;
    try {
      p32.readFile("src/txt/PersonalAppEcosystem.txt");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PersonalAppEcosystem<CentralObject, PhysicalObject> p322 =
        (PersonalAppEcosystem<CentralObject, PhysicalObject>) p32;
    p322.putPhysicalObjectOnCircles("2019-01-03");
    p32 = p322;
    CircularOrbitAPIs apis2 = new CircularOrbitAPIs();
    Difference d3 = apis2.getDifference(p31, p32);
    assertEquals(0, d3.getTrackNums());
    Map<Integer, Integer> a2 = d3.getTrackNumberAndObjectsDif();
    for (Map.Entry<Integer, Integer> entry : a2.entrySet()) {
      int aa = entry.getValue();
      assertEquals(0, aa);
    }

  }
}
