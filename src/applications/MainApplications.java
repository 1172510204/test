package applications;

import java.util.Scanner;

public class MainApplications {

  public static void main(String[] args) {
    System.out.print("共有三种应用：1.TrackGame 2.AtomStructure 3.PersonalAppEcosystem,请输入序号选择：");
    Scanner in = new Scanner(System.in);
    int num = in.nextInt();
    // in.close();
    if (num == 1) {
      TrackGameAPI a1 = new TrackGameAPI();
      System.out.println("选择的应用为TrackGame");
      boolean c = a1.execute();
      while (!c) {
        c = a1.execute();
      }
    } else if (num == 2) {
      AtomStructureAPI a2 = new AtomStructureAPI();
      System.out.println("选择的应用为AtomStructure");
      boolean c = a2.execute();
      while (!c) {
        c = a2.execute();
      }
    } else if (num == 3) {
      PersonalAppEcosystemAPI a3 = new PersonalAppEcosystemAPI();
      System.out.println("选择的应用为PersonalAppEcosystem");
      boolean c = a3.execute();
      while (!c) {
        c = a3.execute();
      }
    }
  }

}
