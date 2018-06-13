package su.izotov.java.objectlr.examples.filename;

import java.util.logging.Logger;
import org.apache.commons.lang3.builder.EqualsBuilder;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import su.izotov.java.objectlr.RecognitionException;

/**
 * @author Vladimir Izotov
 */
public class StringFileNameIT {
  @Test public void testtoText()
      throws RecognitionException {
    FileName instance = new StringFileName("simple.String");
    String expResult = "simple.String";
    String result = instance.toTextRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result,false, null, true));
  }

  @Test public void testtoText2()
      throws RecognitionException {
    Logger.getGlobal().info("toText2");
    FileName instance = new StringFileName("star(STAR)pipe(PIPE)bslash(BSLASH).slash(SLASH)colon(COLON)");
    String expResult ="star*pipe|bslash\\.slash/colon:" ;
    String result = instance.toTextRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result,false, null, true));
  }

  @Test public void testtoText31()
      throws RecognitionException {
    Logger.getGlobal().info("toText31");
    FileName instance = new StringFileName(
        "star___(STAR)pipe_(PIPE)bslash_(BSLASH).slash_(SLASH)colon_(COLON)__" );
    String expResult ="star_(STAR)pipe(PIPE)bslash(BSLASH).slash(SLASH)colon(COLON)_" ;
    String result = instance.toTextRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result,false, null, true));
  }

  @Test public void testtoText32()
      throws RecognitionException {
    Logger.getGlobal().info("toText32");
    FileName instance = new StringFileName(
        "quot_(QUOT)langle_(LANGLE)rangle._(RANGLE)question_(QUESTION)" );
    String expResult ="quot(QUOT)langle(LANGLE)rangle.(RANGLE)question(QUESTION)" ;
    String result = instance.toTextRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
  }

  @Test public void testtoText3()
      throws RecognitionException {
    Logger.getGlobal().info("toText3");
    FileName instance = new StringFileName("quot(QUOT)langle(LANGLE)rangle.(RANGLE)question(QUESTION)");
    String expResult ="quot\"langle<rangle.>question?" ;
    String result = instance.toTextRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result,false, null, true));
  }

  @Test public void testtoText4()
      throws RecognitionException {
    Logger.getGlobal().info("toText4");
    FileName instance = new StringFileName("folder\\(BSLASH)subfolder\\(BSLASH)filename.extension.ext");
    String expResult ="folder\n\\subfolder\n\\filename.extension.ext" ;
    String result = instance.toTextRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result,false, null, true));
  }

  @Test public void testtoText5()
      throws RecognitionException {
    Logger.getGlobal().info("toText5");
    FileName instance = new StringFileName("(BSLASH)");
    String expResult ="\\" ;
    String result = instance.toTextRepresentation();
    assertTrue(EqualsBuilder.reflectionEquals(expResult, result,false, null, true));
  }
}
