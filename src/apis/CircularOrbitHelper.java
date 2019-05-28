package apis;

import centralobject.CentralObject;
import centralobject.Nucleus;
import centralobject.Person;
import circularorbit.CircularOrbit;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;
import physicalobject.PhysicalObject;
import track.Track;

public class CircularOrbitHelper {

  /**
   * 将多轨道系统可视化
   * 
   * @param c 多轨道系统
   */
  public static void visualize(CircularOrbit<CentralObject, PhysicalObject> c) {
    Map<Track, List<PhysicalObject>> s = c.getCircles();
    Map<PhysicalObject, List<PhysicalObject>> relations = c.getTwoObjects();
    JFrame jFrame = new JFrame();
    JPanel jpanel = new JPanel() {
      private static final long serialVersionUID = 1L;

      @Override
      public void paint(Graphics graphics) {
        super.paint(graphics);
        Map<PhysicalObject, List<Integer>> pos = new HashMap<PhysicalObject, List<Integer>>();
        int width = getWidth(); // total width
        int height = getHeight(); // total height
        if (c.getCentreObject() instanceof Nucleus || c.getCentreObject() instanceof Person) {
          graphics.fillOval(width / 2 - 20, height / 2 - 20, 20, 20);
        }
        for (int i = 1; i < s.size() + 1; i++) {// 画轨道
          graphics.drawOval(width / 2 - (i + 1) * 20, height / 2 - (i + 1) * 20, 20 + 40 * i,
              20 + 40 * i);
        }
        int i = 0;
        while (c.iterator().hasNext()) {
          Track t = c.iterator().next();
          for (Track track : s.keySet()) {
            if (t.equals(track)) {
              i++;
              int n = s.get(track).size();
              double angle = 360.0 / n;
              for (int j = 0; j < n; j++) {
                int x = width / 2 - 10 + (int) ((10 + 20 * i) * Math.cos(i * 10 + angle * j)) - 3;
                int y = height / 2 - 10 + (int) ((10 + 20 * i) * Math.sin(i * 10 + angle * j)) - 3;
                graphics.setColor(Color.blue);
                graphics.fillOval(x, y, 7, 7);
                if (relations != null) {
                  pos.put(s.get(track).get(j), new ArrayList<>());
                  pos.get(s.get(track).get(j)).add(x + 2);
                  pos.get(s.get(track).get(j)).add(y + 2);
                }
              }
            }
          }
        }
        if (relations != null) {
          for (PhysicalObject p1 : relations.keySet()) {
            PhysicalObject p11 = null;
            for (PhysicalObject p : pos.keySet()) {
              if (p.getName().equals(p1.getName())) {
                p11 = p;
              }
            }
            if (p11 == null)
              break;
            for (PhysicalObject p2 : relations.get(p1)) {
              PhysicalObject p22 = null;
              for (PhysicalObject p : pos.keySet()) {
                if (p.getName().equals(p2.getName())) {
                  p22 = p;
                }
              }
              if (p22 == null)
                break;
              graphics.setColor(Color.RED);
              graphics.drawLine(pos.get(p11).get(0), pos.get(p11).get(1), pos.get(p22).get(0),
                  pos.get(p22).get(1));
            }
          }
        }

      }
    };
    jFrame.add(jpanel);
    jFrame.setSize(600, 600);
    // jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭操作
    jFrame.setVisible(true);

  }

}
