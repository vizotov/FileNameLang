package su.izotov.java.objectlr.examples.filename.lang.token;

import su.izotov.java.objectlr.examples.filename.lang.FileNameLang;

/**
 * left angle bracket
 * @author Vladimir Izotov
 */
public final class LAngle
    implements FileNameLang // to recognize following unrecognized text
    , FileNameToken // it has the behavior of a token and file name simultaneously
{
  @Override public String toSource() {
    return "(LANGLE)";
  }

  @Override public String toTextRepresentation() {
    return "<";
  }
}
