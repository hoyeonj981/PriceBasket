package me.hoyeonj.pricebasket.adapter.out;

class EmartMallTestHtml {

  static final String TEST_HTML = "<html>test</html>";
  static final String EMPTY_HTML = "";

  static String dummyDiv(final String clazz) {
    return "<div class='" + clazz + "'>"
        + "..."
        + "</div>";
  }

  static String dummyLi(final int number) {
    return "<li>Item " + number + "</li>";
  }
}
