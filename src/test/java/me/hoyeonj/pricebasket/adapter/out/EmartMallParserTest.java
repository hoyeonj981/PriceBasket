package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallItemInfoFixture.createItemInfoFixture;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallParser.*;
import static me.hoyeonj.pricebasket.adapter.out.EmartMallTestHtml.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
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


  @Test
  void 주어진_상품_수에_맞게_파싱한다() {
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

  @Test
  void HTML_문서가_NULL일_경우_예외가_발생한다() {
    assertThatThrownBy(() -> emartMallParser.parseDocument(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void HTML_문서가_비어있다면_예외가_발생한다() {
    var htmlDocument = new HtmlDocument(EMPTY_HTML);

    assertThatThrownBy(() -> emartMallParser.parseDocument(htmlDocument))
        .isInstanceOf(EmptyHtmlDocumentException.class);
  }

  @Test
  void 주어진_CSSQUERY에서_단일_요소를_찾을_수_없다면_예외가_발생한다() {
    var htmlDocument = new HtmlDocument(TEST_HTML);
    when(htmlParser.getElementByCssQuery(anyString(), eq(ITEM_LIST_DIV_CSSQUERY)))
        .thenReturn("");

    assertThatThrownBy(() -> emartMallParser.parseDocument(htmlDocument))
        .isInstanceOf(ElementNotFoundException.class);
  }

  @Test
  void 주어진_CSSQUERY에서_요소들을_찾을_수_없다면_예외가_발생한다() {
    var htmlDocument = new HtmlDocument(TEST_HTML);
    when(htmlParser.getElementByCssQuery(anyString(), eq(ITEM_LIST_DIV_CSSQUERY)))
        .thenReturn(dummyDiv(ITEM_LIST_DIV_CSSQUERY));
    when(htmlParser.getElementsByCssQuery(anyString(), eq(ITEM_LIST_LI_CSSQUERY)))
        .thenReturn(Collections.emptyList());

    assertThatThrownBy(() -> emartMallParser.parseDocument(htmlDocument))
        .isInstanceOf(ElementNotFoundException.class);
  }
}
