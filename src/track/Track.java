package track;

public abstract class Track {

  // AF
  // 轨道包含轨道编号和半径
  // RI
  // n和r都有值
  // Safety from rep exposure:
  // all the fields are private，并且是不可变的
  private final int n;
  private final int r;

  /**
   * 初始化轨道
   * 
   * @param n 轨道编号
   * @param r 轨道半径 防御策略：assert判断pre-condition的半径是否正确
   */
  public Track(int n, int r) {
    assert r >= 0;
    this.n = n;
    this.r = r;
  }

  /**
   * 获取轨道编号
   * 
   * @return 轨道编号
   */
  public int getN() {
    return n;
  }

  /**
   * 获取轨道半径
   * 
   * @return 轨道半径
   */
  public int getR() {
    return r;
  }
}
