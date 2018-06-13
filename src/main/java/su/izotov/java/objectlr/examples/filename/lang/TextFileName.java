package su.izotov.java.objectlr.examples.filename.lang;

import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.examples.filename.FileName;
import su.izotov.java.objectlr.examples.filename.lang.token.FileNameToken;

/**
 * the file name, backed by the arbitrary text, which has no content restrictions!
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public class TextFileName
    implements FileNameToken, FileNameLang
{
  private final String text;

  public TextFileName(final String text) {
    this.text = text;
  }

  @Override public String toTextRepresentation() {
    return text;
  }

  /**
   * the reverse conversion from text to filename is trivial, thus it is realized without
   * language library
   */
  @Override public String toStringRepresentation() {
    // it is not allowed to use in file names:
    // Star: '(STAR)' ; // *
    // Pipe: '(PIPE)' ; // |
    // Bslash: '(BSLASH)' ; // \
    // Colon: '(COLON)' ; // :
    // Quot: '(QUOT)' ; // "
    // Langle: '(LANGLE)' ; // <
    // Rangle: '(RANGLE)' ; // >
    // Question: '(QUESTION)' ; // ?
    // folder splitter:
    // Slash: '(SLASH)' ; // /
    //
    // our special character will be undercsore - '_'
    //
    // marking tokens, explicitly entered by user
    String str = text;
    // firstly, mark special character itself
    str = str.replace("_", "__");
    // restricted character representation must be marked too
    str = str.replace("(STAR)", "_(STAR)");
    str = str.replace("(PIPE)", "_(PIPE)");
    str = str.replace("(BSLASH)", "_(BSLASH)");
    str = str.replace("(COLON)", "_(COLON)");
    str = str.replace("(QUOT)", "_(QUOT)");
    str = str.replace("(LANGLE)", "_(LANGLE)");
    str = str.replace("(RANGLE)", "_(RANGLE)");
    str = str.replace("(QUESTION)", "_(QUESTION)");
    str = str.replace("(SLASH)", "_(SLASH)");
    // replacing restricted symbols by tokens
    str = str.replace("*", "(STAR)");
    str = str.replace("|", "(PIPE)");
    str = str.replace("\\", "(BSLASH)");
    str = str.replace(":", "(COLON)");
    str = str.replace("\"", "(QUOT)");
    str = str.replace("<", "(LANGLE)");
    str = str.replace(">", "(RANGLE)");
    str = str.replace("?", "(QUESTION)");
    str = str.replace("/", "(SLASH)");
    str = str.replace("\n", "\\");
    return str;
  }

  public TextFileName concat(TextFileName textFileName) {
    return new TextFileName(this.text + textFileName.text);
  }

  @Override public String toSource() {
    return this.text;
  }
}
