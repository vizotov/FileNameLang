## Конвертация произвольной строки в имя файла и обратно

Этот проект - пример создания языка с использованием 
[ObjectLR](https://github.com/vizotov/ObjectLR), библиотеки построения распознавателей формальных языков.
 
Результат создания языка находится в исходном коде данного проекта, а последовательность его 
создания - в этом тексте далее.

Предположим, что мы хотим давать создаваемым файлам произвольные имена. При этом мы натыкаемся
на ограничения файловой системы, согласно которым символы '*' '|' '\' ':' '"' '<' '>' '?' '\n' 
не могут быть использованы в имени файла.
 
При создании имени файла из произвольной строки нам придётся заменять запрещённые символы на 
специальные последовательности допустимых символов, а при обратном преобразовании - заменять обратно.
   
   ---------------
   |Символ|Замена|
   |:---:|:---:|
   |*|(STAR)|
   |&#124;|(PIPE)|
   |&#92;|(BSLASH)|
   |:|(COLON)|
   |\"|(QUOT)|
   |<|(LANGLE)|
   |>|(RANGLE)|
   |?|(QUESTION)|
   |/|(SLASH)|
   |new line|\\|
   
  Если такая последовательность появится в исходной строке, она должна быть помечена 
  специальным символом, чтобы при обратном преобразовании её не учитывать.
   
  В качестве специального символа будем использовать спецсимвол "_" (подчёркивание).
   
  Кроме того, сам спецсимвол также может встретиться в исходной строке, поэтому он также должен 
  быть помечен.
  
  Например, такая произвольная строка
  
 `text with symbols *_| and (LANGLE) plus just _ underscore`
 
 должна в виде имени файла выглядеть так
 
 `text with symbols (STAR)__(PIPE) and _(LANGLE) plus just __ underscore`
 
   Преобразование из строки в имя файла реализуется очень просто. Реализация представлена в классе 
    TextFileName.java в методе toStringRepresentation.
    
  Преобразование имени файла в строку не так просто, поскольку значения символов зависят от 
  контекста. Поэтому реализуем его при помощи создания соответствующего языка.

## Создание языка

 Создадим интерфейс **FileName**. Объекты, реализующие данный интерфейс, будут представлять собой 
 имя файла,  которое можно получить как в текстовом виде, на который не накладывается никаких 
 ограничений, так и в виде строки, соответствующей ограничениям на создаваемые имена файлов.
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

 ## Разрешённые символы
 Все символы, которые не относятся к токенам, и так являются правильным представлением имени файла.
  Поэтому, просто создадим объект, который их содержит и реализует интерфейс FileName. Этот объект
   будет обёрткой для нераспознанных символов языка:
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
 ## Сам язык
 Теперь реализуем сам язык:
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
 ## Интерфейс Token
 Создаём набор токенов распознаваемого языка. В качестве них используются заменённые символы, 
 которых не бывает в именах файлов. Каждому символу из вышеприведённой таблицы должен соответствовать объект, реализующий интерфейс **Token**. Каждый **Token** сам распознаёт 
 следующий за ним текст, реализуя интерфейс **Sense**.
 
 Все токены нашего языка должны распознавать друг друга, то есть весь язык в целом. 
 Следовательно, они должны реализовывать интерфейс **FileNameLang**:
  
  *Обратный слэш*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class BSlash
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(BSLASH)";
  }
}
 ``` 
 *Двоеточие*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Colon
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(COLON)";
  }
}
 ``` 
 *Левая угловая скобка*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class LAngle
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(LANGLE)";
  }
}
 ``` 
 *Перевод строки*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class NewLine
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "\\";
  }
}
 ``` 
 *Вертикальная черта*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Pipe
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(PIPE)";
  }
}
 ``` 
 *Знак вопроса*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Question
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(QUESTION)";
  }
}
 ``` 
 *Двойная кавычка*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Quot
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(QUOT)";
  }
}
 ``` 
 *Правая угловая скобка*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class RAngle
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(RANGLE)";
  }
}
 ``` 
 *Слэш*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Slash
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(SLASH)";
  }
}
 ``` 
 *Звёздочка*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class Star
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "(STAR)";
  }
}
 ``` 
 *Подчёркивание*
 ```java
import su.izotov.java.objectlr.token.Token;

public final class UnderScore
    implements Token, FileNameLang {
  @Override public String toSource() {
    return "_";
  }
}
 ```
Теперь добавляем список токенов в язык. Интерфейс принимает следующий вид:
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
 Реализация этого интерфейса    
 ```java
 public class FNLang
     implements FileNameLang {
 }
 ```
Для использования имени файла в качестве исходных данных создадим класс:
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
 ## Первый запуск
Проверим распознавание вышеупомянутой тестовой строки путём создания теста:
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
Для сравнения результатов, чтобы  не реализовывать методы *equals* для тестовых целей, используем
 механизм из библиотеки *org.apache.commons:commons-lang3:3.7* 
  
 При запуске тест не проходит.
 
 Увидеть детали процесса распознавания можно в стандартном выводе **Logger**. В нём представлен 
 весь процесс распознавания. В конце отчёта представлен результирующий объект.
 
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
 В данном случае это цепочка объектов, записанная в виде списка. Каждый токен представлен своим 
 текстом. Каждый класс - своим коротким наименованием.
 
 Для добавления информативности в отчёт для любого класса можно перегрузить метод **toVisual**. 
 Например, для класса **TextFileName** мы хотим отображать не только имя класса, но и содержимое.
  Добавим в этот класс следующий метод:
  ```java
  ...
  @Override public TextCell toVisual() {
      return FileName.super.toVisual().addRight(text);
    }
  ...
  ```
 После этого результат распознавания отображается следующим образом:
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
 ## Взаимодействие объектов
 Теперь нам нужно реализовать методы, обеспечивающие взаимодействие объектов. Начнём со 
 спецсимвола, который должен превращать следующий за ним токен в обычный текст.
 
 Значит, к классу **UnderScore** следует добавить метод
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
При взаимодействии текущего спецсимвола и следующего за ним токена получается просто текст токена.

Посмотрим на результат запуска теста:
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
Все токены, экранированные спецсимволом стали текстом.

Теперь необходимо объединить цепочки текстов в один текст. В класс **TextFileName** добавим метод:
```java
...
public TextFileName concat(TextFileName textFileName) {
    return new TextFileName(this.text + textFileName.text);
  }
  ...
```
Посмотрим на результат:
```java
FNLang
TextFileName text with symbols 
'(STAR)'
TextFileName _
'(PIPE)'
TextFileName  and (LANGLE) plus just _ underscore
```
Для получения результирующего текста любой токен и текст должны взаимодействовать между собой, 
порождая правильный текст.

Для токенов правильным текстом является исходный символ. Для его получения у каждого токена 
должен существовать специальный метод.

Для этих целей очень удобно было бы реализовать в каждом токене интерфейс **FileName**, имеющий 
соответствующий метод *toTextRepresentation*. Создадим интерфейс:
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
Все токены вместо интерфейса **Token** должны реализовывать интерфейс **FileNameToken**. Для 
этого необходимо добавить метод *toTextRepresentation* в каждый из токенов. Результат должен 
получиться следующий:

*Обратный слэш*
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
 *Двоеточие*
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
 *Левая угловая скобка*
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
 *Перевод строки*
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
 *Вертикальная черта*
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
 *Знак вопроса*
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
 *Двойная кавычка*
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
 *Правая угловая скобка*
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
 *Слэш*
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
 *Звёздочка*
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
 *Подчёркивание*
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
Текст, представленный классом **TextFileName** также представляет собой специализированный токен 
языка. Поэтому, он тоже должен реализовывать интерфейсы **FileNameToken** и **FileNameLang**.

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
Теперь результат распознавания будет выглядеть следующим образом:
```
FNLang
'text with symbols '
'(STAR)'
'_'
'(PIPE)'
' and (LANGLE) plus just _ underscore'
```
Все токены и тексты реализуют интерфейс **FileNameToken**, поэтому для преобразования их 
последовательности в текст следует реализовать их взаимодействие. В интерфейс **FileNameToken** 
добавляем метод:
```java
...
 default FileName concat(FileNameToken token)
      throws RecognitionException {
    return new TextFileName(this.toTextRepresentation() + token.toTextRepresentation());
  }
...
```
Результат запуска выглядит так:
```
FNLang
'text with symbols *__| and _< plus just __ underscore'
```
Объект типа **FNLang** инициирует распознавание именно на данном языке. При взаимодействии с 
**FileNameToken** он должен просто исчезать, поскольку данный интерфейс и так реализован в 
последующих объектах. В класс **FNLang** необходимо добавить метод:
```java
...
  public FileNameToken concat(FileNameToken token) {
    return token;
  }
...
```
Посмотрим на результат:

`text with symbols *__| and _< plus just __ underscore`

Он не совпадает с ожидаемым:

`text with symbols *_| and (LANGLE) plus just _ underscore`

Проблема в том, что спецсимвол подчёркивание не должен восприниматься как обычный токен. Поэтому,
 он не должен реализовывать поведение интерфейса **FileNameToken**. Он не должен 
 взаимодействовать с предыдущим токеном, а только с последующим. Вот его новая версия:
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
На этом этапе мы получаем ожидаемый результат распознавания.

Результат выполнения других тестов можно посмотреть в этом проекте.

Как и предполагалось, процесс распознавания был реализован как процесс взаимодействия частей 
исходного текста. 