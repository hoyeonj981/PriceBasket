package me.hoyeonj.pricebasket.domain;

import java.util.Objects;

public class Category {

  static final String UNCATEGORIZED_NAME = "ETC";

  public static Category UNCATEGORIZED = new Category(CategoryId.uncategorized(),
      UNCATEGORIZED_NAME);

  private final CategoryId categoryId;
  private final String name;

  public Category(final CategoryId categoryId, final String name) {
    validateName(name);
    this.categoryId = categoryId;
    this.name = name;
  }

  private void validateName(final String name) {
    if (name.isBlank()) {
      throw new EmptyCategoryNameException("카테고리 이름은 공백일 수 없습니다");
    }
  }

  public String getCategoryId() {
    return categoryId.getValue();
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Category category = (Category) o;
    return Objects.equals(categoryId, category.categoryId) && Objects.equals(name,
        category.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(categoryId, name);
  }
}
