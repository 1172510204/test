package exception;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import circularorbit.PersonalAppEcosystem;
import exception.OutRules.ElementException;
import org.junit.Test;
import physicalobject.PhysicalObject;

public class TestDependIncorrectException {

  @Test(expected = DependIncorrectException.class)
  public void testDependIncorrectExceptionAS()
      throws SameLabelException, DependIncorrectException, ElementException {
    AtomStructure<CentralObject, PhysicalObject> a1 = new AtomStructure<>();
    a1.readFile("src/txt/ASFalseDependTrackNum.txt");
  }

  @Test(expected = DependIncorrectException.class)
  public void testDependIncorrectExceptionPAEUninstallLog()
      throws SameLabelException, DependIncorrectException {
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    p1.readFile("src/txt/PAEFalseUninstallLog.txt");
  }

  @Test(expected = DependIncorrectException.class)
  public void testDependIncorrectExceptionPAEInstallLog()
      throws SameLabelException, DependIncorrectException {
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    p1.readFile("src/txt/PAEFalseInstallLog.txt");
  }

  @Test(expected = DependIncorrectException.class)
  public void testDependIncorrectExceptionPAEUsageLog()
      throws SameLabelException, DependIncorrectException {
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    p1.readFile("src/txt/PAEFalseUsageLog.txt");
  }

  @Test(expected = DependIncorrectException.class)
  public void testDependIncorrectExceptionPAERelation()
      throws SameLabelException, DependIncorrectException {
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    p1.readFile("src/txt/PAEFalseDependRelation.txt");
  }

  @Test(expected = DependIncorrectException.class)
  public void testDependIncorrectExceptionPAERelation1()
      throws SameLabelException, DependIncorrectException {
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    p1.readFile("src/txt/PAEFalseDependRelation1.txt");
  }
}
