package track;

public class TrackFactoryTrackGame extends TrackFactory {

  /**
   * ������
   * 
   * @param n ������
   * @param r ����뾶
   * @return ����Ĺ��
   */
  @Override
  public Track createTrack(int n, int r) {
    return new TrackTrackGame(n, r);
  }

}
