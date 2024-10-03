package me.hoyeonj.pricebasket.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HomePlusParserTest {

  static final int TOTAL_ITME_SIZE = 156;

  HomePlusParser homePlusParser = new HomePlusParser();
  Path path;

  @BeforeEach
  void setUp() {
    path = Paths.get("src/test/resources/homeplus.html");
  }

  @DisplayName("파일을 읽어 파싱한 상품 개수는 예상 수와 동일하다")
  @Test
  void parsed_documents_size_should_be_expected_number() throws IOException {
    var givenHtml = new String(Files.readAllBytes(path));
    var htmlDocument = new HtmlDocument(givenHtml);

    var actual = homePlusParser.parseDocument(htmlDocument);
    var size = actual.itemList().size();

    assertThat(size).isEqualTo(TOTAL_ITME_SIZE);
  }
}