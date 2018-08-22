package su.izotov.java.objectlr.examples.filename;

import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.Sentence;
import su.izotov.java.objectlr.examples.filename.lang.FNLang;
import su.izotov.java.objectlr.examples.filename.lang.FileNameLang;
import su.izotov.java.objectlr.examples.filename.lang.TextFileName;
import su.izotov.java.objectlr.tokens.Tokens;

/**
 * the file name, backed by the simple string, which must correspond filesystem's naming
 * restrictions.
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public class StringFileName
    extends Sentence<FileNameLang, TextFileName>
    implements FileName {
  /**
   * @param fileName must be a correct file name
   */
  public StringFileName(final String fileName) {
    this(fileName, new FNLang());
  }

  protected StringFileName(
      final String text, final FileNameLang language) {
    super(text, language);
  }

  /**
   * restore arbitrary text, which has been encoded to correct file name by TextFileName
   * .toStringRepresentation()
   */
  @Override public String toTextRepresentation()
      throws RecognitionException {
    // here we must parse file name and convert it to text
    return this.toObject().toTextRepresentation();
  }

  /**
   * just return file name. Nothing special
   */
  @Override public String toStringRepresentation()
      throws RecognitionException {
    return this.toObject().toTextRepresentation();
  }

  @Override public Tokens tokens() {
    return new FNLang().tokens();
  }

  @Override public Sense textToken(final String text) {
    return new FNLang().textToken(text);
  }

  @Override
  public String toSource() {
    return this.toString();
  }
}
