package apis;

import centralobject.CentralObject;
import centralobject.CentralTrackGame;
import centralobject.Nucleus;
import centralobject.Person;
import circularorbit.CircularOrbit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import physicalobject.PhysicalObject;
import track.Track;

public class CircularOrbitAPIs {

  /**
   * 计算多轨道系统中各轨道上物体分布的熵值
   * 
   * @param c 多轨道系统
   * @return 熵值 防御策略：assert判断pre-condition
   */
  public double getObjectDistributionEntropy(CircularOrbit<CentralObject, PhysicalObject> c) {
    assert c != null;
    Map<Track, List<PhysicalObject>> cir = c.getCircles();
    int n = 0, x = 0;
    double h = 0;
    for (Track track : cir.keySet()) {
      n = n + cir.get(track).size();
    }
    if (n != 0) {
      for (Track track : cir.keySet()) {
        x = cir.get(track).size();
        double pi = (double) x / (double) n;
        if (pi != 0) {
          h = h - pi * (Math.log(pi) / Math.log(2.0));
        }
      }
    }
    return h;
  }

  /**
   * 计算任意两个物体之间的最短逻辑距离
   * 
   * @param c  多轨道系统
   * @param e1 第一个物体
   * @param e2 第二个物体
   * @return 最短的逻辑距离
   */
  public int getLogicalDistance(CircularOrbit<CentralObject, PhysicalObject> c, PhysicalObject e1,
      PhysicalObject e2) {
    assert e1 != null;
    assert e2 != null;
    if (e1.getName().equals(e2.getName()))/* 判断名字是否一样 */
      return 0;
    Map<PhysicalObject, List<PhysicalObject>> adj = c.getTwoObjects();
    Queue<PhysicalObject> queue = new LinkedBlockingQueue<>();
    Map<String, Boolean> visited = new HashMap<>();
    Map<String, Integer> distance = new HashMap<>();
    for (PhysicalObject po : adj.keySet())
      visited.put(po.getName(), false);
    visited.put(e1.getName(), true);
    distance.put(e1.getName(), 0);
    queue.add(e1);
    while (queue.isEmpty() == false) {
      PhysicalObject nextfirst = queue.poll();
      for (Iterator<PhysicalObject> it = adj.get(nextfirst).iterator(); it.hasNext();) {
        PhysicalObject tmp = it.next();
        if (!visited.get(tmp.getName())) {
          if (!tmp.getName().equals(e2.getName()))/* 还没访问到name2 */
          {
            visited.put(tmp.getName(), true);
            queue.add(tmp);
            distance.put(tmp.getName(), distance.get(nextfirst.getName()) + 1);
          } else/* 访问到name2 */
          {
            return distance.get(nextfirst.getName()) + 1;
          }
        }
      }
    }
    return -1;
  }

  /**
   * 计算两个多轨道系统之间的差异
   * 
   * @param c1 第一个多轨道系统
   * @param c2 第二个多轨道系统
   * @return 系统之间的差异 防御策略：assert判断pre-condition为同一类别
   */
  public Difference getDifference(CircularOrbit<CentralObject, PhysicalObject> c1,
      CircularOrbit<CentralObject, PhysicalObject> c2) {
    assert (c1.getCentreObject() instanceof CentralTrackGame
        && c2.getCentreObject() instanceof CentralTrackGame)
        || c1.getCentreObject() instanceof Nucleus && c2.getCentreObject() instanceof Nucleus
        || c1.getCentreObject() instanceof Person
            && c2.getCentreObject() instanceof Person : "两个多轨道系统不是同一个类别";
    Map<Track, List<PhysicalObject>> cir1 = c1.getCircles();
    Map<Track, List<PhysicalObject>> cir2 = c2.getCircles();
    Map<Integer, Integer> trackNumberAndObjectsDif = new HashMap<>();
    Difference difference = null;
    if ((c1.getCentreObject() instanceof CentralTrackGame)
        && (c2.getCentreObject() instanceof CentralTrackGame)) {
      List<PhysicalObject> repeated = new ArrayList<>();
      difference = new TrackGameDifference();
      difference.setTracknums(cir1.size() - cir2.size());
      Map<Integer, String> trackNumberAndName = new HashMap<>();
      if (cir1.size() > cir2.size()) {
        for (Track t : cir2.keySet()) {
          for (Track r : cir1.keySet()) {
            if (t.getN() == r.getN()) {
              for (int i = 0; i < cir2.get(t).size(); i++) {
                for (int j = 0; j < cir1.get(r).size(); j++) {
                  if (cir2.get(t).get(i).equals(cir1.get(r).get(j))) {
                    repeated.add(cir2.get(t).get(i));
                  }
                }
              }
            }
          }
        }
      } else {
        for (Track t : cir1.keySet()) {
          for (Track r : cir2.keySet()) {
            if (t.getN() == r.getN()) {
              for (int i = 0; i < cir1.get(t).size(); i++) {
                for (int j = 0; j < cir2.get(r).size(); j++) {
                  if (cir1.get(t).get(i).equals(cir2.get(r).get(j))) {
                    repeated.add(cir1.get(t).get(i));
                  }
                }
              }
            }
          }
        }
      }
      for (Track t : cir1.keySet()) {
        trackNumberAndObjectsDif.put(t.getN(), cir1.get(t).size());
        String s = null;
        if (cir1.get(t).size() == 1) {
          for (PhysicalObject each : repeated) {
            if (!each.equals(cir1.get(t).get(0))) {
              s = cir1.get(t).get(0).getName();
            }
          }
        }
        /*
         * else if(cir1.get(t).size()>1){ s="{"; for(int i=0;i<cir1.get(t).size();i++) {
         * for(PhysicalObject each:repeated) { if(!each.equals(cir1.get(t).get(i))) {
         * s=s+cir1.get(t).get(i); if(i<cir1.size()-1) { s=s+","; } else { s=s+"}"; } }
         * } } }
         */
        if (s == "{") {
          s = null;
        }
        trackNumberAndName.put(t.getN(), s);
      }
      if (cir1.size() < cir2.size()) {
        for (int i = cir1.size(); i < cir2.size(); i++) {
          trackNumberAndObjectsDif.put(i, 0);
          trackNumberAndName.put(i, null);
        }
      }
      for (Track t : cir2.keySet()) {
        String s = null;
        if (cir2.get(t).size() == 0) {
          if (trackNumberAndName.get(t.getN()) == null) {
            s = "无";
          } else {
            s = "-无";
          }
        } else if (cir2.get(t).size() == 1) {
          if (trackNumberAndName.get(t.getN()) == null) {
            for (PhysicalObject each : repeated) {
              if (!each.equals(cir2.get(t).get(0))) {
                s = "无-" + cir2.get(t).get(0).getName();
              } else {
                s = "无";
              }
            }
          } else {
            for (PhysicalObject each : repeated) {
              if (!each.equals(cir2.get(t).get(0))) {
                s = "-" + cir2.get(t).get(0).getName();
              } else {
                s = "-无";
              }
            }
          }
        }
        /*
         * else { if(trackNumberAndName.get(t.getN())==null) { s="无-{"; } else { s="-{";
         * } for(PhysicalObject each:repeated) { for(int i=0;i<cir2.get(t).size();i++) {
         * if(!each.equals(cir2.get(t).get(i))) { s=s+cir2.get(t).get(i);
         * if(i<cir2.size()-1) { s=s+","; } else { s=s+"}"; } } } } if(s=="无-{") {
         * s="无"; } else if(s=="-{") { s="-无"; } }
         */
        for (int n : trackNumberAndObjectsDif.keySet()) {
          if (n == t.getN()) {
            int size = trackNumberAndObjectsDif.get(n) - cir2.get(t).size();
            trackNumberAndObjectsDif.put(t.getN(), size);
          }
        }
        for (int n : trackNumberAndName.keySet()) {
          if (n == t.getN()) {
            if (trackNumberAndName.get(t.getN()) == null) {
              trackNumberAndName.put(t.getN(), s);
            } else {
              String h = trackNumberAndName.get(n) + s;
              trackNumberAndName.put(t.getN(), h);
            }
            break;
          }
        }
      }
      difference.setTrackNumberAndName(trackNumberAndName);
    } else if ((c1.getCentreObject() instanceof Nucleus)
        && (c2.getCentreObject() instanceof Nucleus)) {
      difference = new AtomStructureDifference();
      difference.setTracknums(cir1.size() - cir2.size());
      for (Track t : cir1.keySet()) {
        trackNumberAndObjectsDif.put(t.getN(), cir1.get(t).size());
      }
      for (Track t : cir2.keySet()) {
        if (!trackNumberAndObjectsDif.containsKey(t.getN())) {
          trackNumberAndObjectsDif.put(t.getN(), 0 - cir2.get(t).size());
        } else {
          for (int n : trackNumberAndObjectsDif.keySet()) {
            if (n == t.getN()) {
              int size = trackNumberAndObjectsDif.get(n) - cir2.get(t).size();
              trackNumberAndObjectsDif.put(t.getN(), size);
            }
          }
        }
      }

    } else if ((c1.getCentreObject() instanceof Person)
        && (c2.getCentreObject() instanceof Person)) {
      difference = new PersonalAppEcosystemDifference();
      difference.setTracknums(cir1.size() - cir2.size());
      Map<Integer, String> trackNumberAndName = new HashMap<>();
      for (Track t : cir1.keySet()) {
        String s = "{";
        for (int i = 0; i < cir1.get(t).size(); i++) {
          s = s + cir1.get(t).get(i).getName();
          if (i < cir1.get(t).size() - 1) {
            s = s + ",";
          }
        }
        s = s + "}";
        trackNumberAndObjectsDif.put(t.getN(), cir1.get(t).size());
        trackNumberAndName.put(t.getN(), s);
      }
      for (Track t : cir2.keySet()) {
        String s = "-{";
        for (int i = 0; i < cir2.get(t).size(); i++) {
          s = s + cir2.get(t).get(i).getName();
          if (i < cir2.get(t).size() - 1) {
            s = s + ",";
          }
        }
        s = s + "}";
        if (!trackNumberAndObjectsDif.containsKey(t.getN())) {
          trackNumberAndObjectsDif.put(t.getN(), 0 - cir2.get(t).size());
          trackNumberAndName.put(t.getN(), s);
        } else {
          for (int n : trackNumberAndObjectsDif.keySet()) {
            if (n == t.getN()) {
              int size = trackNumberAndObjectsDif.get(n) - cir2.get(t).size();
              trackNumberAndObjectsDif.put(t.getN(), size);
            }
          }
          for (int n : trackNumberAndName.keySet()) {
            if (n == t.getN()) {
              String h = trackNumberAndName.get(n) + s;
              trackNumberAndName.put(t.getN(), h);
            }
          }
        }
      }
      difference.setTrackNumberAndName(trackNumberAndName);
    }
    if (trackNumberAndObjectsDif != null) {
      difference.setTrackNumberAndObjectsDif(trackNumberAndObjectsDif);
    }
    return difference;
  }

}
