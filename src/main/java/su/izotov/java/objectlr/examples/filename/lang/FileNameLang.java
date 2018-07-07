package su.izotov.java.objectlr.examples.filename.lang;


import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.examples.filename.lang.token.BSlash;
import su.izotov.java.objectlr.examples.filename.lang.token.Colon;
import su.izotov.java.objectlr.examples.filename.lang.token.LAngle;
import su.izotov.java.objectlr.examples.filename.lang.token.NewLine;
import su.izotov.java.objectlr.examples.filename.lang.token.Pipe;
import su.izotov.java.objectlr.examples.filename.lang.token.Question;
import su.izotov.java.objectlr.examples.filename.lang.token.Quot;
import su.izotov.java.objectlr.examples.filename.lang.token.RAngle;
import su.izotov.java.objectlr.examples.filename.lang.token.Slash;
import su.izotov.java.objectlr.examples.filename.lang.token.Star;
import su.izotov.java.objectlr.examples.filename.lang.token.UnderScore;
import su.izotov.java.objectlr.tokens.Tokens;
import su.izotov.java.objectlr.tokens.TokensOf;

/**
 * the filename language
 * <p>Created with IntelliJ IDEA.</p>
 * @author Vladimir Izotov
 * @version $Id$
 * @since 1.0
 */
public interface FileNameLang
    extends Sense {
  /**
   * It is an example without any optimisations
   */
  @Override default Tokens tokens() {
    return new TokensOf(
        new BSlash()//
        , new Colon()//
        , new LAngle()//
        , new NewLine()//
        , new Pipe()//
        , new Question()//
        , new Quot()//
        , new RAngle()//
        , new Slash()//
        , new Star()//
        , new UnderScore());
  }

  @Override default Sense textToken(String text) {
    return new TextFileName(text);
  }
}
