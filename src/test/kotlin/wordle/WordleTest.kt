package wordle

import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import wordle.domain.Word
import wordle.domain.Wordle

class WordleTest : AnnotationSpec() {

    @Test
    fun `입력된 값이 단어 목록에 없으면 예외가 발생한다`() {

        val word = Word("abcde")
        val words = WordsResourceReader().readWords("src/main/resources/words.txt")
        val wordle = Wordle(words, 1)

        shouldThrowWithMessage<IllegalArgumentException>("목록에 존재하지 않는 단어입니다.") {
            wordle.tryMatch(word)
        }
    }

    @Test
    fun `입력된 값이 단어 목록에 있으면 예외를 발생시키지 않는다`() {

        val word = Word("rebut")
        val words = WordsResourceReader().readWords("src/main/resources/words.txt")
        val wordle = Wordle(words, 1)

        shouldNotThrowAny { wordle.tryMatch(word) }
    }

    @Test
    fun `누적된 결과를 반환한다`() {
        val words = WordsResourceReader().readWords("src/main/resources/words.txt")
        val wordle = Wordle(words, 1)

        wordle.tryMatch(Word("evoke"))
        wordle.tryMatch(Word("evoke"))

        wordle.matchResults.size shouldBe 2
    }
}
