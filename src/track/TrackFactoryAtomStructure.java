package track;

public class TrackFactoryAtomStructure extends TrackFactory {

  /**
   * ������
   * 
   * @param n ������
   * @param r ����뾶
   * @return ����Ĺ��
   */
  @Override
  public Track createTrack(int n, int r) {
    return new TrackAtomStructure(n, r);
  }

}
