package exception;

import centralobject.CentralObject;
import circularorbit.AtomStructure;
import circularorbit.TrackGame;
import exception.OutRules.DecimalsException;
import exception.OutRules.ElementException;
import exception.OutRules.InformationNumIncorrectException;
import exception.OutRules.MetresFalseException;
import exception.OutRules.NotCaptialLetterException;
import exception.OutRules.TransposeException;
import org.junit.Test;
import physicalobject.PhysicalObject;

public class TestOutRules {

  @Test(expected = DecimalsException.class)
  public void testDecimalsException() throws MetresFalseException, InformationNumIncorrectException,
      TransposeException, NotCaptialLetterException, DecimalsException, SameLabelException {
    TrackGame<CentralObject, PhysicalObject> t1 = new TrackGame<>();
    t1.readFile("src/txt/TGFalseDecimals.txt");
  }


  @Test(expected = MetresFalseException.class)
  public void testMetresFalseException()
      throws MetresFalseException, InformationNumIncorrectException, TransposeException,
      NotCaptialLetterException, DecimalsException, SameLabelException {
    TrackGame<CentralObject, PhysicalObject> t1 = new TrackGame<>();
    t1.readFile("src/txt/TGFalseMetresFalse.txt");
  }


  @Test(expected = NotCaptialLetterException.class)
  public void testNotCaptialLetterException()
      throws MetresFalseException, InformationNumIncorrectException, TransposeException,
      NotCaptialLetterException, DecimalsException, SameLabelException {
    TrackGame<CentralObject, PhysicalObject> t1 = new TrackGame<>();
    t1.readFile("src/txt/TGFalseNotCaptialLetter.txt");
  }


  @Test(expected = InformationNumIncorrectException.class)
  public void testInformationNumIncorrectException()
      throws MetresFalseException, InformationNumIncorrectException, TransposeException,
      NotCaptialLetterException, DecimalsException, SameLabelException {
    TrackGame<CentralObject, PhysicalObject> t1 = new TrackGame<>();
    t1.readFile("src/txt/TGFalseInformationNumIncorrect.txt");
  }


  @Test(expected = TransposeException.class)
  public void testTransposeException()
      throws MetresFalseException, InformationNumIncorrectException, TransposeException,
      NotCaptialLetterException, DecimalsException, SameLabelException {
    TrackGame<CentralObject, PhysicalObject> t1 = new TrackGame<>();
    t1.readFile("src/txt/TGFalseTranspose.txt");
  }


  @Test(expected = ElementException.class)
  public void testElementException()
      throws SameLabelException, DependIncorrectException, ElementException {
    AtomStructure<CentralObject, PhysicalObject> a1 = new AtomStructure<>();
    a1.readFile("src/txt/ASFalseElement.txt");
  }
}
