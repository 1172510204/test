package track;

public class TrackFactoryPersonalAppEcosystem extends TrackFactory {

  /**
   * ������
   * 
   * @param n ������
   * @param r ����뾶
   * @return ����Ĺ��
   */
  @Override
  public Track createTrack(int n, int r) {
    return new TrackPersonalAppEcosystem(n, r);
  }

}
