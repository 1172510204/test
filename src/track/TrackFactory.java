package track;

public abstract class TrackFactory {

  /**
   * 创造轨道
   * 
   * @param n 轨道编号
   * @param r 轨道半径
   * @return 创造的轨道
   */
  public abstract Track createTrack(int n, int r);
}
