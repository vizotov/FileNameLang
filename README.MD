# Convert an arbitrary string to a file name and back

This project is an example of creating a language using 
[ObjectLR](https://github.com/vizotov/ObjectLR),
 library - construction of formal languages recognizers.
 
The result of the language creation is in the source code of this project, and its 
creaction sequence - in this text below.

Suppose we want to give the files we create arbitrary names. In this case we stumble
on file system restrictions according to which characters '*' '|' '\' ':' '"' '<' '>' '?''\n' 
cannot be used in the file name.
 
When creating a file name from an arbitrary string, we will have to replace the forbidden characters with 
a special sequence of valid characters, and the reverse transformation to replace the back.
  
   ---------------
   |Symbol|Replacement|
   |:---:|:---:|
   |*|(STAR)|
   |&#124;|(PIPE)|
   |\\|(BSLASH)|
   |:|(COLON)|
   |\"|(QUOT)|
   |<|(LANGLE)|
   |>|(RANGLE)|
   |?|(QUESTION)|
   |/|(SLASH)|
   |new line|\\|
   
  If such a sequence appears in the source string, it must be marked 
    a special symbol to ignore it when converting back.
   
  We will use the character "_" (underscore) as a special character.
   
  In addition, the special character itself can also be found in the source string, so it should 
  also be marked.
  
  For example, such an arbitrary string
  
 `text with symbols *_| and (LANGLE) plus just _ underscore`
 
 should look like the following as a file name
 
 `text with symbols (STAR)__(PIPE) and _(LANGLE) plus just __ underscore`
 
 The conversion from string to file name is very simple. The implementation is presented in the class 
 TextFileName.java in the toStringRepresentation method.
     
 Converting a file name to a string is not so easy, because the values of the characters depend on 
 context. Therefore, we implement it by creating the appropriate language.
  
## Language creation

Let's create the interface **FileName**. The objects that implement this interface will represent 
 a file name that can be obtained in text form, or in the form of a file name.
 
  ```java
import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.Sense;

public interface FileName
    extends Sense {
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
```

 ## Allowed characters
All characters that are not tokens are the correct representation of the file name.
  Therefore, create an object that contains them and implements the *FileName* interface. This 
  object
   will be a wrapper for unrecognized language characters:
  ```java
public class TextFileName
    implements FileName {
  private final String text;

  public TextFileName(final String text) {
    this.text = text;
  }

  @Override public String toTextRepresentation() {
    return text;
  }

  @Override public String toStringRepresentation() {
    // At this point, implementation is not important
    throw new UnsupportedOperationException("#toStringRepresentation");
  }
}
  ```
 ## Language
Now we implement the language itself:
  ```java

import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.tokens.Tokens;

public interface FileNameLang
    extends Sense {
  @Override default Tokens tokens() {
    // We do not have tokens yet
    throw new UnsupportedOperationException("#tokens");
  }

  @Override default Sense textToken(String text) {
    return new TextFileName(text);
  }
}
  ```
 ## Token interface
 Create a set of tokens of the recognized language. These are replaced characters, 
  which are not allowed in file names. Each character in the above table must have an object that
 implements the **Token** interface. Each **Token** recognizes 
  the following text, implementing the interface **Sense**.
  
  All tokens of our language must recognize each other, that is, the language as a whole. 
  Therefore, they must implement the **FileNameLang** interface:
    
  *Back slash*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class BSlash
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(BSLASH)";
  }
}
 ``` 
 *Colon*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Colon
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(COLON)";
  }
}
 ``` 
 *Left angle bracket*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class LAngle
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(LANGLE)";
  }
}
 ``` 
 *Linefeed*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class NewLine
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "\\";
  }
}
 ``` 
 *Pipe*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Pipe
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(PIPE)";
  }
}
 ``` 
 *Question*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Question
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(QUESTION)";
  }
}
 ``` 
 *Double quote*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Quot
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(QUOT)";
  }
}
 ``` 
 *Right angle bracket*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class RAngle
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(RANGLE)";
  }
}
 ``` 
 *Slash*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Slash
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(SLASH)";
  }
}
 ``` 
 *Star*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Star
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(STAR)";
  }
}
 ``` 
 *Underscore*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class UnderScore
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "_";
  }
}
 ```
Now we add the list of tokens to the language. The interface takes the following form:
 ```java
public interface FileNameLang
    extends Sense {
  /**
   * It is an example without any optimisations
   */
  @Override default Tokens tokens() {
    return new TokensSet(
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
 ```
Implement this interface to create an object:    
 ```java
 public class FNLang
     implements FileNameLang {
 }
 ```
To use the file name as the source data, create a class:
 ```java
import su.izotov.java.objectlr.RecognitionException;
import su.izotov.java.objectlr.Sentence;

/**
 * the file name, backed by the simple string, which must correspond filesystem's naming
 * restrictions.
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
}
 ``` 
 ## First-run
Check the string recognition by creating test:
 ```java
 import org.apache.commons.lang3.builder.EqualsBuilder;
 import static org.junit.Assert.assertTrue;
 import org.junit.Test;
 import su.izotov.java.objectlr.RecognitionException;
 
 public class StringFileNameTest {
   @Test public void testToTextRepresentation()
       throws RecognitionException {
     FileName instance = new StringFileName("text with symbols (STAR)__(PIPE) and _(LANGLE) plus just __ underscore");
     String expResult ="text with symbols *_| and (LANGLE) plus just _ underscore" ;
     String result = instance.toTextRepresentation();
     assertTrue(EqualsBuilder.reflectionEquals(expResult, result, false, null, true));
   }
 }
 ```
In order not to implement the *equals* methods for test purposes, we use
 mechanism from the library *org.apache.commons:commons-lang3:3.7*.
  
When you run the test does not pass.
 
You can see the details of the recognition process in the standard output **Logger**. It presents 
 the whole recognition process. At the end of the report the resulting object is presented.
 
 ```
 FNLang
 TextFileName
 '(STAR)'
 '_'
 '_'
 '(PIPE)'
 TextFileName
 '_'
 '(LANGLE)'
 TextFileName
 '_'
 '_'
 TextFileName
 ```
 In this case, it is a chain of objects written in the form of a list. Each token is represented by its own 
  text. Each class has its own short name.
 
 To add information to the report for any class, you can override the **toVisual** method. 
  For example, for the **TextFileName** class, we want to display not only the class name but also the content.
   Add the following method to this class:
  ```java
  ...
  @Override public TextCell toVisual() {
      return FileName.super.toVisual().addRight(text);
    }
  ...
  ```
 After that, the recognition result is displayed as follows:
 ```
 FNLang
 TextFileName text with symbols 
 '(STAR)'
 '_'
 '_'
 '(PIPE)'
 TextFileName  and 
 '_'
 '(LANGLE)'
 TextFileName  plus just 
 '_'
 '_'
 TextFileName  underscore
 ```
 ## Interaction of objects
 Now we need to implement methods that provide object interaction. Let's start with 
  a special character, which should turn the token following him into a normal text.
 
 Therefore, you must add a method to the **UnderScore** class:
 ```java
...
  /**
   * convert the following token into text without a translating
   */
  public Sense concat(final Token token) {
    return new TextFileName(token.toSource());
  }
...
``` 
The interaction of the current special character and the next token gives a simple text of the token.

Let's look at the result of running the test:
```
FNLang
TextFileName text with symbols 
'(STAR)'
TextFileName _
'(PIPE)'
TextFileName  and 
TextFileName (LANGLE)
TextFileName  plus just 
TextFileName _
TextFileName  underscore
``` 
All tokens escaped by a special character became a text.

Now you need to combine the chain of texts in a single text. Add a method to the **TextFileName** class:
```java
...
public TextFileName concat(TextFileName textFileName) {
    return new TextFileName(this.text + textFileName.text);
  }
  ...
```
Let's see the result:
```java
FNLang
TextFileName text with symbols 
'(STAR)'
TextFileName _
'(PIPE)'
TextFileName  and (LANGLE) plus just _ underscore
```
To get the resulting text, any token and text must interact with each other, 
generating the right text.

For tokens, the correct text is the original symbol. To obtain it from each token 
there must be a special method.

For these purposes, it would be very convenient to implement an interface **FileName** in each token, which has 
the corresponding *toTextRepresentation* method. Create an interface:
```java
import su.izotov.java.objectlr.token.Token;

/**
 * it has the behavior of a token and file name simultaneously
 */
public interface FileNameToken
    extends Token, FileName {
  @Override default String toStringRepresentation() {
    return toSource();
  }
}
```
All tokens, instead of the **Token** interface, must implement the **FileNnameToken** interface. 
To do this, you must add the *toTextRepresentation* method to each token. The result should be the following: 

*Back slash*
 ```java
public final class BSlash
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(BSLASH)";
  }

  @Override public String toTextRepresentation() {
    return "\\";
  }
}
 ``` 
 *Colon*
 ```java
public final class Colon
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(COLON)";
  }

  @Override public String toTextRepresentation() {
    return ":";
  }
}
 ``` 
 *Left angle bracket*
 ```java
public final class LAngle
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(LANGLE)";
  }

  @Override public String toTextRepresentation() {
    return "<";
  }
}
 ``` 
 *Linefeed*
 ```java
public final class NewLine
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "\\";
  }

  @Override public String toTextRepresentation() {
    return "\n";
  }
}
 ``` 
 *Pipe*
 ```java
public final class Pipe
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(PIPE)";
  }

  @Override public String toTextRepresentation() {
    return "|";
  }
}
 ``` 
 *Question*
 ```java
public final class Question
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(QUESTION)";
  }

  @Override public String toTextRepresentation() {
    return "?";
  }
}
 ``` 
 *Double quote*
 ```java
public final class Quot
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(QUOT)";
  }

  @Override public String toTextRepresentation() {
    return "\"";
  }
}
 ``` 
 *Right angle bracket*
 ```java
public final class RAngle
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(RANGLE)";
  }

  @Override public String toTextRepresentation() {
    return ">";
  }
}
 ``` 
 *Slash*
 ```java
public final class Slash
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(SLASH)";
  }

  @Override public String toTextRepresentation() {
    return "/";
  }
}
 ``` 
 *Star*
 ```java
public final class Star
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "(STAR)";
  }

  @Override public String toTextRepresentation() {
    return "*";
  }
}
 ``` 
 *Underscore*
 ```java
import su.izotov.java.objectlr.Sense;

public final class UnderScore
    implements FileNameToken, FileNameLang {
  @Override public String toSource() {
    return "_";
  }

  /**
   * convert the following token into text without a translating
   */
  public FileName concat(final FileNameToken token) {
    return new TextFileName(token.toSource());
  }

  @Override public String toTextRepresentation() {
    return "_";
  }
}
 ```
 Text class **TextFileName** is a specialized language token. Therefore, it must also implement 
 the **FileNameToken** and **FileNameLang** interfaces.
```java
import su.izotov.java.objectlr.print.TextCell;

public class TextFileName
    implements FileNameToken, FileNameLang {
  private final String text;

  public TextFileName(final String text) {
    this.text = text;
  }

  @Override public String toTextRepresentation() {
    return text;
  }

  @Override public String toStringRepresentation() {
    // At this point, implementation is not important
    throw new UnsupportedOperationException("#toStringRepresentation");
  }

  public TextFileName concat(TextFileName textFileName) {
    return new TextFileName(this.text + textFileName.text);
  }

  @Override public String toSource() {
    return this.text;
  }
}

```
Now the recognition result will look like this:
```
FNLang
'text with symbols '
'(STAR)'
'_'
'(PIPE)'
' and (LANGLE) plus just _ underscore'
```
All tokens and texts implement the interface **FileNameToken**, so to convert their sequence 
into text, you should implement their interaction.
Add the method to the interface **FileNameToken**: 
```java
...
 default FileName concat(FileNameToken token)
      throws RecognitionException {
    return new TextFileName(this.toTextRepresentation() + token.toTextRepresentation());
  }
...
```
The launch result looks like this:
```
FNLang
'text with symbols *__| and _< plus just __ underscore'
```
Object type **FNLang** initiates facial recognition is in the given language. When interacting 
with **FileNameToken**, it should simply disappear, since this interface is already implemented 
in subsequent objects. You must add a method to the **FNLang** class: 
```java
...
  public FileNameToken concat(FileNameToken token) {
    return token;
  }
...
```
Let's see the result:

`text with symbols *__| and _< plus just __ underscore`

It does not match the expected:

`text with symbols *_| and (LANGLE) plus just _ underscore`

The problem is that the special character underscore should not be perceived as a normal token. 
Therefore, it should not implement the behavior of the **FileNameToken** interface. It should not
 interact with the previous token, but only with the subsequent one. Here is its new version: 
 ```java
import su.izotov.java.objectlr.Sense;
import su.izotov.java.objectlr.token.Token;

public final class UnderScore
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "_";
  }

  /**
   * convert the following token into text without a translating
   */
  public FileName concat(final FileNameToken token) {
    return new TextFileName(token.toSource());
  }

  /**
   * it must escape not only FileNameToken, but also itself
   */
  public Sense concat(UnderScore sense) {
    return new TextFileName(sense.toSource());
  }
}
```
At this stage we get the expected recognition result.

You can see the result of other tests in this project.

As expected, the recognition process was implemented as a process of interaction of parts of the 
source text.