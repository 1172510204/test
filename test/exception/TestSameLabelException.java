package exception;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import circularorbit.PersonalAppEcosystem;
import circularorbit.TrackGame;
import exception.OutRules.DecimalsException;
import exception.OutRules.ElementException;
import exception.OutRules.InformationNumIncorrectException;
import exception.OutRules.MetresFalseException;
import exception.OutRules.NotCaptialLetterException;
import exception.OutRules.TransposeException;
import org.junit.Test;
import physicalobject.PhysicalObject;

public class TestSameLabelException {

  @Test(expected = SameLabelException.class)
  public void testSameLabelExceptionTG()
      throws MetresFalseException, InformationNumIncorrectException, TransposeException,
      NotCaptialLetterException, DecimalsException, SameLabelException {
    TrackGame<CentralObject, PhysicalObject> t1 = new TrackGame<>();
    t1.readFile("src/txt/TGFalseSameName.txt");
  }

  @Test(expected = SameLabelException.class)
  public void testSameLabelExceptionAS()
      throws SameLabelException, DependIncorrectException, ElementException {
    AtomStructure<CentralObject, PhysicalObject> a1 = new AtomStructure<>();
    a1.readFile("src/txt/ASFalseSameTrack.txt");
  }

  @Test(expected = SameLabelException.class)
  public void testSameLabelExceptionPAESameNameAPP()
      throws SameLabelException, DependIncorrectException {
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    p1.readFile("src/txt/PAEFalseSameNameAPP.txt");
  }

  @Test(expected = SameLabelException.class)
  public void testSameLabelExceptionPAELabelRelation()
      throws SameLabelException, DependIncorrectException {
    PersonalAppEcosystem<CentralObject, PhysicalObject> p1 = new PersonalAppEcosystem<>();
    p1.readFile("src/txt/PAEFalseLabelRelation.txt");
  }
}
