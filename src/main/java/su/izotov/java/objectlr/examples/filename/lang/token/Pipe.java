package su.izotov.java.objectlr.examples.filename.lang.token;

import su.izotov.java.objectlr.examples.filename.lang.FileNameLang;

/**
 * pipe token
 * @author Vladimir Izotov
 */
public final class Pipe
    implements FileNameLang // to recognize following unrecognized text
    , FileNameToken // it has the behavior of a token and file name simultaneously
{
  @Override public String toSource() {
    return "(PIPE)";
  }

  @Override public String toTextRepresentation() {
    return "|";
  }
}
