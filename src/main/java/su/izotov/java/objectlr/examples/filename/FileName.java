package su.izotov.java.objectlr.examples.filename;

import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.Sense;

/**
 * the file name representation
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public interface FileName extends Sense {
  /**
   * convert file name into arbitrary text without any restrictions on it's content
   * @return text
   */
  String toTextRepresentation()
      throws RecognitionException;

  /**
   * represent file name as simple string with restrictions of filesystem's naming of files
   */
  String toStringRepresentation()
      throws RecognitionException;
}
