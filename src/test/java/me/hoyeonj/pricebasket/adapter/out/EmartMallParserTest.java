package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallItemInfoFixture.createItemInfoFixture;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallParser.CURRENT_PAGE_CSSQUERY;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallParser.IMAGE_CSSQUERY;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallParser.ITEM_LIST_DIV_CSSQUERY;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallParser.ITEM_LIST_LI_CSSQUERY;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallParser.PAGES_NAVIGATOR_CSSQUERY;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallParser.SRC_ATTRIBUTE_KEY;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.EMPTY_HTML;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.TEST_HTML;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.dummyDiv;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.dummyLi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EmartMallParserTest {

  @Mock
  private EmartMallItemConverter converter;

  @Mock
  private JsoupHtmlParser htmlParser;

  private EmartMallParser emartMallParser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    emartMallParser = new EmartMallParser(converter, htmlParser);
  }


  @DisplayName("주어진 상품 수에 맞게 파싱한다")
  @Test
  void parseDocumentUsingGivenItems() {
    var htmlDocument = new HtmlDocument(TEST_HTML);
    when(htmlParser.getElementByCssQuery(anyString(), eq(ITEM_LIST_DIV_CSSQUERY)))
        .thenReturn(dummyDiv(ITEM_LIST_DIV_CSSQUERY));
    when(htmlParser.getElementsByCssQuery(anyString(), eq(ITEM_LIST_LI_CSSQUERY)))
        .thenReturn(Arrays.asList(dummyLi(1), dummyLi(2)));
    when(htmlParser.getText(anyString())).thenReturn("Item text");
    when(htmlParser.getFirstAttributeValue(anyString(), eq(IMAGE_CSSQUERY), eq(SRC_ATTRIBUTE_KEY)))
        .thenReturn("http://example.com/image.jpg");
    when(converter.convert(anyString(), anyString())).thenReturn(createItemInfoFixture());
    when(htmlParser.getElementsByCssQuery(anyString(), eq(PAGES_NAVIGATOR_CSSQUERY)))
        .thenReturn(Arrays.asList("Page 1", "Page 2"));
    when(htmlParser.getElementByCssQuery(anyString(), eq(CURRENT_PAGE_CSSQUERY)))
        .thenReturn("1");

    var result = emartMallParser.parseDocument(htmlDocument);

    assertThat(result).isNotNull();
    assertThat(2).isEqualTo(result.itemList().size());
    assertThat(2).isEqualTo(result.totalPages());
    assertThat(1).isEqualTo(result.currentPage());
  }

  @DisplayName("HTML문서가 NULL일 경우 예외가 발생한다")
  @Test
  void throwExceptionWhenHtmlIsNull() {
    assertThatThrownBy(() -> emartMallParser.parseDocument(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @DisplayName("HTML문서가 비어있다면 예외가 발생한다")
  @Test
  void throwExceptionWhenHtmlIsEmpty() {
    var htmlDocument = new HtmlDocument(EMPTY_HTML);

    assertThatThrownBy(() -> emartMallParser.parseDocument(htmlDocument))
        .isInstanceOf(EmptyHtmlDocumentException.class);
  }

  @DisplayName("주어진 CSSQUERY에서 div 요소를 찾을 수 없다면 예외가 발생한다")
  @Test
  void throwExceptionWhenCanNotFindDivElementFromGivenCssQuery() {
    var htmlDocument = new HtmlDocument(TEST_HTML);
    when(htmlParser.getElementByCssQuery(anyString(), eq(ITEM_LIST_DIV_CSSQUERY)))
        .thenReturn("");

    assertThatThrownBy(() -> emartMallParser.parseDocument(htmlDocument))
        .isInstanceOf(ElementNotFoundException.class);
  }

  @DisplayName("주어진 CSSQUERY에서 li 요소들을 찾을 수 없다면 예외가 발생한다")
  @Test
  void throwExceptionWhenCanNotFindLiElementFromGivenCssQuery() {
    var htmlDocument = new HtmlDocument(TEST_HTML);
    when(htmlParser.getElementByCssQuery(anyString(), eq(ITEM_LIST_DIV_CSSQUERY)))
        .thenReturn(dummyDiv(ITEM_LIST_DIV_CSSQUERY));
    when(htmlParser.getElementsByCssQuery(anyString(), eq(ITEM_LIST_LI_CSSQUERY)))
        .thenReturn(Collections.emptyList());

    assertThatThrownBy(() -> emartMallParser.parseDocument(htmlDocument))
        .isInstanceOf(ElementNotFoundException.class);
  }
}
