package su.izotov.java.objectlr.examples.filename.lang;

import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.examples.filename.lang.token.FileNameToken;

/**
 * the object for beginning the recognition
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public class FNLang
    implements FileNameLang {
  /**
   * this object is not needed anymore, if following implements FileNameLang
   */
  public FileNameLang concat(FileNameToken token)
      throws RecognitionException {
    return new TextFileName(token.toTextRepresentation());
  }

  @Override
  public String toSource() {
    return "";
  }
}
