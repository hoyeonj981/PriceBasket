package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallItemInfoFixture.createItemInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class EmartMallItemConverterTest {

  private final EmartMallItemConverter converter = new EmartMallItemConverter();

  @DisplayName("텍스트에서 상품 정보를 가져온다")
  @Test
  void parse_item_info_from_text() {
    var itemName = "itemName";
    var brandName = "brandName";
    var price = "1000";
    var unitOfPrice = "10매 당 100원";
    var rating = "4.8";
    var detailsUrl = "testUrl";
    var imageUrl = "testUrl";
    var givenText = createText(itemName, brandName, price, unitOfPrice, rating, detailsUrl);
    var expected = createItemInfo(
        itemName, brandName, price, unitOfPrice, rating, detailsUrl, imageUrl);

    var actual = converter.convert(givenText, imageUrl);

    assertThat(actual).isEqualTo(expected);
  }


  @DisplayName("주어진 텍스트가 빈 문자열이거나 null일 경우 예외가 발생한다")
  @ParameterizedTest
  @MethodSource("emptyOrNull")
  void throw_exception_when_text_is_empty_or_null(final String given) {
    assertThatThrownBy(() -> converter.convert("", ""))
        .isInstanceOf(EmptyTextException.class);
  }

  private static Stream<String> emptyOrNull() {
    return Stream.of(null, "");
  }

  @DisplayName("상품 이미지 url이 null인 경우 예외가 발생한다")
  @Test
  void throw_exception_when_image_url_is_null() {
    assertThatThrownBy(() -> converter.convert("test", null))
        .isInstanceOf(InvalidImageUrlException.class);
  }

  @DisplayName("상품 상세 페이지가 없다면 예외가 발생한다")
  @Test
  void throw_exception_when_there_item_detail_page_does_not_exist() {
    var itemName = "itemName";
    var brandName = "brandName";
    var price = "1000";
    var unitOfPrice = "10매 당 100원";
    var rating = "4.8";
    var detailsUrl = "";
    var imageUrl = "testUrl";
    var givenText = createText(itemName, brandName, price, unitOfPrice, rating, detailsUrl);

    assertThatThrownBy(() -> converter.convert(givenText, imageUrl))
        .isInstanceOf(ItemDetailNotFoundException.class);
  }

  @DisplayName("상품이름은 빈 문자열이면 예외가 발생한다")
  @Test
  void throw_exception_when_itemName_is_empty_or_null() {
    var itemName = "";
    var brandName = "brandName";
    var price = "1000";
    var unitOfPrice = "10매 당 100원";
    var rating = "4.8";
    var detailsUrl = "testUrl";
    var imageUrl = "testUrl";
    var givenText = createText(itemName, brandName, price, unitOfPrice, rating, detailsUrl);

    assertThatThrownBy(() -> converter.convert(givenText, imageUrl))
        .isInstanceOf(ItemNameNotFoundException.class);
  }

  @DisplayName("상품가격은 0 미만일 경우 예외가 발생한다")
  @ParameterizedTest
  @ValueSource(
      strings = {"0", "-1"}
  )
  void throw_exception_when_price_is_under_zero(final String given) {
    var itemName = "itemName";
    var brandName = "brandName";
    var price = given;
    var unitOfPrice = "10매 당 100원";
    var rating = "4.8";
    var detailsUrl = "testUrl";
    var imageUrl = "testUrl";
    var givenText = createText(itemName, brandName, price, unitOfPrice, rating, detailsUrl);

    assertThatThrownBy(() -> converter.convert(givenText, imageUrl))
        .isInstanceOf(InvalidItemPriceException.class);
  }

  private String createText(
      final String itemName,
      final String brandName,
      final String price,
      final String unitOfPrice,
      final String rating,
      final String detailsUrl) {
    return "{\"advertBidId\":\"\","
        + "\"displayPrc\":\"" + price + "\","
        + "\"giftBtnShowType\":\"\","
        + "\"giftBtnActType\":\"Y\","
        + "\"itemChrctDivCd\":\"10\","
        + "\"giftBtnMsg\":\"\","
        + "\"itemNm\":\"" + itemName + "\","
        + "\"shppTypeCd\":\"10\","
        + "\"shppTypeDtlCd\":\"11\","
        + "\"goItemDetailYn\":\"N\","
        + "\"dealItemYn\":\"N\","
        + "\"itemRegDivCd\":\"20\","
        + "\"advertExtensTeryDivCd\":\"\","
        + "\"siteNo\":\"6001\","
        + "\"brandNm\":\"" + brandName + "\","
        + "\"useForcedSsg\":\"N\","
        + "\"cartPsblType\":\"\","
        + "\"msgItemDetail\":\"\","
        + "\"bsplItemDivCd\":null,"
        + "\"itemLnkd\":\"" + detailsUrl + "\","
        + "\"bothSsgMorningShppYn\":\"N\","
        + "\"drctPurchYn\":\"N\","
        + "\"gourmetYn\":\"\","
        + "\"itemId\":\"1000484056251\","
        + "\"cleaningLabYn\":\"N\","
        + "\"uitemId\":\"00000\","
        + "\"infloSiteNo\":\"7018\","
        + "\"salestrNo\":\"2037\"} "
        + "쓱배송 새벽배송 가능 "
        + "레모나 [경남제약]상큼한비타민레모나에스산90포(캐릭터 랜덤출고) "
        + "정상가격 21,800 원 "
        + "할인율50% "
        + "판매가격 10,900원 "
        + unitOfPrice + " "
        + "상품평점 5점 만점에 " + rating + " "
        + "상품평 개수 (1,534)";
  }
}
