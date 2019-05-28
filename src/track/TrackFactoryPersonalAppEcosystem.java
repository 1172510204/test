package track;

public class TrackFactoryPersonalAppEcosystem extends TrackFactory {

  /**
   * 创造轨道
   * 
   * @param n 轨道编号
   * @param r 轨道半径
   * @return 创造的轨道
   */
  @Override
  public Track createTrack(int n, int r) {
    return new TrackPersonalAppEcosystem(n, r);
  }

}
