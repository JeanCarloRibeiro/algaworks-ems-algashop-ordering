package com.algashop.ordering.domain.exception;

public class ErrorMessages {
  private ErrorMessages() {
  }
  public static final String VALIDATION_ERROR_BIRTHDATE_MUST_IN_PAST = "BirthDate must be a past date";
  public static final String VALIDATION_ERROR_BIRTHDATE_IS_NULL = "BirthDate cannot be null";
  public static final String VALIDATION_ERROR_FULL_NAME_IS_NULL = "FullName cannot be null";
  public static final String VALIDATION_ERROR_FULL_NAME_IS_BLANK = "FullName cannot be blank";
  public static final String VALIDATION_ERROR_DOCUMENT_IS_NULL = "Document cannot be null";
  public static final String VALIDATION_ERROR_DOCUMENT_IS_BLANK = "Document cannot be blank";
  public static final String VALIDATION_ERROR_EMAIL_IS_INVALID = "Email is invalid";
  public static final String ERROR_CUSTOMER_ARCHIVED = "Customer is already archived";
  public static final String VALIDATION_ERROR_PHONE_IS_BLANK = "Phone cannot be blank";
  public static final String VALIDATION_ERROR_PHONE_IS_NULL = "Phone cannot be null";
  public static final String VALIDATION_ERROR_MONEY_IS_NULL = "Money cannot be null";
  public static final String VALIDATION_ERROR_PRODUCT_IS_NULL = "ProductName cannot be null";
  public static final String VALIDATION_ERROR_PRODUCT_IS_BLANK = "ProductName cannot be blank";
  public static final String VALIDATION_ERROR_QUANTITY_MUST_ZERO_POSITIVE = "Quantity must be a zero ou positive";
  public static final String VALIDATION_ERROR_QUANTITY_MUST_POSITIVE = "Quantity must be a positive";

  public static final String ERROR_ORDER_STATUS_CANNOT_BE_CHANGED = "Cannot change order %s status from %s to %s";
  public static final String ERROR_DELIVERY_DATE_CANNOT_BE_IN_THE_PAST = "Order %s expected delivery cannot be in the past";
  public static final String ERROR_ORDER_CANNOT_BE_PLACED_HAS_NOT_ITEMS = "Order %s cannot be closed, it hast not items";
}
