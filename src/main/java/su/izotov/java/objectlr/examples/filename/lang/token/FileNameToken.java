package su.izotov.java.objectlr.examples.filename.lang.token;

import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.examples.filename.FileName;
import su.izotov.java.objectlr.examples.filename.lang.TextFileName;
import su.izotov.java.objectlr.token.Token;

/**
 * it has the behavior of a token and file name simultaneously
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public interface FileNameToken
    extends Token, FileName {
  @Override default String toStringRepresentation() {
    return toSource();
  }

  /**
   * creating text representation of the filename.
   */
  default FileName concat(FileNameToken token)
      throws RecognitionException {
    return new TextFileName(this.toTextRepresentation() + token.toTextRepresentation());
  }
}
