package su.izotov.java.objectlr.examples.filename.lang.token;

import su.izotov.java.objectlr.examples.filename.lang.FileNameLang;

/**
 * slash token
 * @author Vladimir Izotov
 */
public final class Slash
    implements FileNameLang // to recognize following unrecognized text
    , FileNameToken // it has the behavior of a token and file name simultaneously
{
  @Override public String toSource() {
    return "(SLASH)";
  }

  @Override public String toTextRepresentation() {
    return "/";
  }
}
