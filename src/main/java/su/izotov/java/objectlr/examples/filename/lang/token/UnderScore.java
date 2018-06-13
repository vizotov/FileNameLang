package su.izotov.java.objectlr.examples.filename.lang.token;

import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.examples.filename.lang.FileNameLang;
import su.izotov.java.objectlr.examples.filename.lang.TextFileName;
import su.izotov.java.objectlr.token.Token;

/**
 * ignore a sense of following token and understand it as a TextFileName. if following
 * sense is not a token, then this token becomes a TextFileName
 * @author Vladimir Izotov
 */
public final class UnderScore
    implements FileNameLang // to recognize following unrecognized TextFileName
    , Token // it has the behavior of a token
    // IT DOES NOT IMPLEMENTS FileNameToken, BECAUSE IT HAS NOT INTERACT WITH PRECEDING SENSE AND
    // HAS TO WAIT NEXT SENSE TO INTERACT WITH IT
{
  @Override public String toSource() {
    return "_";
  }

  /**
   * convert the following token into text without a translating
   */
  public TextFileName concat(FileNameToken token) {
    return new TextFileName(token.toSource());
  }

  /**
   * convert the following token into text without a translating
   */
  public Sense concat(UnderScore sense) {
    return new TextFileName(sense.toSource());
  }

}
