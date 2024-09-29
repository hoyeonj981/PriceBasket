package me.hoyeonj.pricebasket.adapter.out;

import static me.hoyeonj.pricebasket.adapter.out.EmartMallItemInfoFixture.createItemInfo;
import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class EmartMallItemConverterTest {

  private final EmartMallItemConverter converter = new EmartMallItemConverter();

  @Test
  void 텍스트에서_상품_정보를_가져온다() {
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


  @ParameterizedTest
  @MethodSource("emptyOrNull")
  void 주어진_텍스트가_빈_문자열이거나_null일_경우_예외가_발생한다(final String given) {
    assertThatThrownBy(() -> converter.convert("", ""))
        .isInstanceOf(EmptyTextException.class);
  }

  private static Stream<String> emptyOrNull() {
    return Stream.of(null, "");
  }

  @Test
  void 상품_이미지_url이_null인_경우_예외가_발생한다() {
    assertThatThrownBy(() -> converter.convert("test", null))
        .isInstanceOf(InvalidImageUrlException.class);
  }

  @Test
  void 상품_상세_페이지가_없다면_예외가_발생한다() {
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

  @Test
  void 상품_이름은_빈_문자열이면_예외가_발생한다() {
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

  @ParameterizedTest
  @ValueSource(
      strings = {"0", "-1"}
  )
  void 상품_가격은_0_미만일_경우_예외가_발생한다(final String given) {
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
