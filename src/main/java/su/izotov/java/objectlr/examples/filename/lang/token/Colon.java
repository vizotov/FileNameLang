package su.izotov.java.objectlr.examples.filename.lang.token;

import su.izotov.java.objectlr.examples.filename.lang.FileNameLang;

/**
 * colon token
 * @author Vladimir Izotov
 */
public final class Colon
    implements FileNameLang // to recognize following unrecognized text
    , FileNameToken // it has the behavior of a token and file name simultaneously
{
  @Override public String toSource() {
    return "(COLON)";
  }

  @Override public String toTextRepresentation() {
    return ":";
  }
}
