package su.izotov.java.objectlr.examples.filename.lang.token;

import su.izotov.java.objectlr.examples.filename.lang.FileNameLang;

/**
 * star token
 * @author Vladimir Izotov
 */
public final class Star
    implements FileNameLang // to recognize following unrecognized text
    , FileNameToken // it has the behavior of a token and file name simultaneously
{
  @Override public String toSource() {
    return "(STAR)";
  }

  @Override public String toTextRepresentation() {
    return "*";
  }
}
