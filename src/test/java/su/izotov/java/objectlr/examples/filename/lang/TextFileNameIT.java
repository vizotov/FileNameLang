package su.izotov.java.objectlr.examples.filename.lang;

import java.util.logging.Logger;
import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.examples.filename.FileName;

/**
 * @author Vladimir Izotov
 */
public class TextFileNameIT {
  @Test public void testtoStringFileName()
      throws Exception, RecognitionException {
    Logger.getGlobal().info("toStringFileName");
    String expResult = "Name(SLASH) of file.(QUESTION) with (STAR) all of (PIPE) "
                       + "symbols (BSLASH) and (COLON) the ; (LANGLE)(RANGLE) line \\ "
                       + "feed \\ symbols";
    FileName instance = new TextFileName(
        "Name/ of file.? with * all of | symbols \\ and : the ; <> line \n feed \n symbols");
    String result = instance.toStringRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testtoStringFileName2()
      throws Exception, RecognitionException {
    Logger.getGlobal().info("toStringFileName2");
    FileName instance = new TextFileName("simple.FileName");
    String expResult = "simple.FileName";
    String result = instance.toStringRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testtoStringFileName3()
      throws Exception, RecognitionException {
    Logger.getGlobal().info("toStringFileName3");
    FileName instance = new TextFileName("star*pipe|bslash\\.slash/colon:");
    String expResult = "star(STAR)pipe(PIPE)bslash(BSLASH).slash(SLASH)colon(COLON)";
    String result = instance.toStringRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testtoStringFileName4()
      throws Exception, RecognitionException {
    Logger.getGlobal().info("toStringFileName4");
    FileName instance = new TextFileName("quot\"langle<rangle.>question?");
    String expResult = "quot(QUOT)langle(LANGLE)rangle.(RANGLE)question(QUESTION)";
    String result = instance.toStringRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testtoStringFileName31()
      throws Exception, RecognitionException {
    Logger.getGlobal().info("toStringFileName31");
    FileName instance = new TextFileName(
        "_star_(STAR)pipe(PIPE)bslash(BSLASH).slash(SLASH)colon(COLON)_");
    String expResult = "__star___(STAR)pipe_(PIPE)bslash_(BSLASH).slash_(SLASH)colon_(COLON)__";
    String result = instance.toStringRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testtoStringFileName41()
      throws Exception, RecognitionException {
    Logger.getGlobal().info("toStringFileName41");
    FileName instance = new TextFileName("quot(QUOT)langle(LANGLE)rangle.(RANGLE)question(QUESTION)");
    String expResult = "quot_(QUOT)langle_(LANGLE)rangle._(RANGLE)question_(QUESTION)";
    String result = instance.toStringRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testtoStringFileName5()
      throws Exception, RecognitionException {
    Logger.getGlobal().info("toStringFileName5");
    FileName instance = new TextFileName("folder\n\\subfolder\n\\filename.extension.ext");
    String expResult = "folder\\(BSLASH)subfolder\\(BSLASH)filename.extension.ext";
    String result = instance.toStringRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }
}
