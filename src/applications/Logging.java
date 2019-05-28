package applications;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class Logging {

  private Logger logger = Logger.getLogger(Logging.class.getName());
  private FileHandler fh = null;
  private String filename;

  public Logging() {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    try {
      filename = "src/logs/" + format.format(new Date()) + ".log";
      fh = new FileHandler(filename);
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (fh != null) {
      logger.addHandler(fh);
    }

  }

  public void doLog(String sentence) {
    logger.info(sentence);
  }

  public void logException(Exception e) {
    StringWriter trace = new StringWriter();
    e.printStackTrace(new PrintWriter(trace));
    logger.severe(trace.toString());
    logger.info("“Ï≥£");
  }

  public String getFileName() {
    return filename;
  }
}
