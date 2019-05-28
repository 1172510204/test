package circularobrit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import circularorbit.CircularOrbit;
import circularorbit.ConcreteCircularOrbit;
import exception.DependIncorrectException;
import exception.OutRules.ElementException;
import exception.SameLabelException;
import java.util.Collections;
import org.junit.Test;
import physicalobject.PhysicalObject;
import track.Track;

public abstract class TestCircularOrbit {


  // Testing strategy

  // testCirclesEmpty()
  // ����û�г�ʼ��ʱcircles�Ƿ�Ϊ��

  // testAddTrack()
  // ������ӵĹ���İ뾶�Ƿ���ȷ

  // testRemoveTrack()
  // ����remove���ڵĹ���Ͳ����ڵĹ��

  // testAddCenterObject()
  // ������ӵ����������Ƿ�ɹ�

  // testAddTrackObject()
  // ������ӹ�������ÿ�����������ĸ����Ƿ���ȷ

  // testRemoveTrackObject()
  // ����remove��ÿ�����������ĸ����Ƿ���ȷ

  // testAddCentreTrackObjectRelation()
  // �����������������������Ĺ�ϵ�Ƿ�ɹ�

  // testAddTwoObjectRelation()
  // ������ӹ������֮��Ĺ�ϵÿ�������Ӧ��֮�й�ϵ������ĸ����Ƿ���ȷ

  // testIterator()
  // ���Ե������Ƿ��������������
  public abstract CircularOrbit<String, String> emptyInstance();// ����ֻ��Ϊ�˲��Թ��ܵ���ȷ�ԣ�L��E�����ͼ���ΪString

  @Test
  public void testCirclesEmpty() {
    assertEquals("expected empty circles", Collections.emptyMap(),
        CircularOrbit.empty().getCircles());
  }

  @Test
  public void testAddTrack() {
    CircularOrbit<String, String> circularorbit = emptyInstance();
    circularorbit.addTrack(1, 1);
    circularorbit.addTrack(1, 1);
    Track track = null;
    for (Track each : circularorbit.getCircles().keySet()) {
      track = each;
    }
    assertEquals(1, track.getR());
  }

  @Test
  public void testRemoveTrack() {
    CircularOrbit<String, String> circularorbit = emptyInstance();
    circularorbit.addTrack(1, 1);
    circularorbit.addTrack(2, 2);
    circularorbit.removeTrack(2);
    circularorbit.removeTrack(3);
    assertEquals(1, circularorbit.getCircles().size());
  }

  @Test
  public void testAddCenterObject() {
    CircularOrbit<String, String> circularorbit = emptyInstance();
    circularorbit.addCenterObject("wang");
    assertTrue(circularorbit.getCentreObject().equals("wang"));
  }

  @Test
  public void testAddTrackObject() {
    CircularOrbit<String, String> circularorbit = emptyInstance();
    circularorbit.addTrack(1, 1);
    assertTrue(circularorbit.addTrackObject(1, 1, "aa"));
    assertTrue(circularorbit.addTrackObject(1, 1, "bb"));
    assertFalse(circularorbit.addTrackObject(2, 2, "cc"));
    Track track = null;
    for (Track each : circularorbit.getCircles().keySet()) {
      track = each;
    }
    assertEquals(2, circularorbit.getCircles().get(track).size());
  }

  @Test
  public void testRemoveTrackObject() {
    CircularOrbit<String, String> circularorbit = emptyInstance();
    circularorbit.addTrack(1, 1);
    circularorbit.addTrackObject(1, 1, "aa");
    circularorbit.addTrackObject(1, 1, "bb");
    circularorbit.removeObjectFromOneTrack(1, "bb");
    circularorbit.removeObjectFromOneTrack(2, "bb");
    Track track = null;
    for (Track each : circularorbit.getCircles().keySet()) {
      track = each;
    }
    assertEquals(1, circularorbit.getCircles().get(track).size());
  }

  @Test
  public void testAddCentreTrackObjectRelation() {
    CircularOrbit<String, String> circularorbit = emptyInstance();
    circularorbit.addTrack(1, 1);
    circularorbit.addTrackObject(1, 1, "bb");
    circularorbit.addCentreTrackObjectRelation("bb");
    assertTrue(circularorbit.getCentreTrackObject().contains("bb"));
  }

  @Test
  public void testAddTwoObjectRelation() {
    CircularOrbit<String, String> circularorbit = emptyInstance();
    circularorbit.addTrack(1, 1);
    circularorbit.addTrack(2, 2);
    circularorbit.addTrackObject(1, 1, "aa");
    circularorbit.addTrackObject(1, 1, "bb");
    circularorbit.addTrackObject(2, 2, "cc");
    assertTrue(circularorbit.addTwoObjectsRelation("aa", "bb"));
    assertTrue(circularorbit.addTwoObjectsRelation("aa", "cc"));
    assertTrue(circularorbit.addTwoObjectsRelation("aa", "dd"));
    assertEquals(3, circularorbit.getTwoObjects().get("aa").size());
  }

  @Test
  public void testIterator() {
    AtomStructure<CentralObject, PhysicalObject> atomstructure = new AtomStructure<>();
    try {
      atomstructure.readFile("src/txt/AtomicStructure.txt");
    } catch (SameLabelException | DependIncorrectException | ElementException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    int i = 0;
    while (atomstructure.iterator().hasNext()) {
      Track track = atomstructure.iterator().next();
      i++;
      assertEquals(i, track.getR());
    }
    assertEquals(5, atomstructure.getTracks().size());
  }

  @Test
  public void testReadFile() {
    ConcreteCircularOrbit<CentralObject, PhysicalObject> aCircularOrbit =
        new ConcreteCircularOrbit<>();
    try {
      aCircularOrbit.readFile("");
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }


}
