/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
/*
 * This code was generated by https://github.com/google/apis-client-generator/
 * (build: 2016-01-08 17:48:37 UTC)
 * on 2016-02-04 at 12:20:49 UTC 
 * Modify at your own risk.
 */

package com.sporthorsetech.horseshoepad.backend.horseApi.model;

/**
 * Model definition for Horse.
 *
 * <p> This is the Java data model class that specifies how to parse/serialize into the JSON that is
 * transmitted over HTTP when working with the horseApi. For a detailed explanation see:
 * <a href="https://developers.google.com/api-client-library/java/google-http-java-client/json">https://developers.google.com/api-client-library/java/google-http-java-client/json</a>
 * </p>
 *
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public final class Horse extends com.google.api.client.json.GenericJson {

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private java.lang.String horseName;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key @com.google.api.client.json.JsonString
  private java.lang.Long id;

  /**
   * The value may be {@code null}.
   */
  @com.google.api.client.util.Key
  private com.google.api.client.util.DateTime regDate;

  /**
   * @return value or {@code null} for none
   */
  public java.lang.String getHorseName() {
    return horseName;
  }

  /**
   * @param horseName horseName or {@code null} for none
   */
  public Horse setHorseName(java.lang.String horseName) {
    this.horseName = horseName;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public java.lang.Long getId() {
    return id;
  }

  /**
   * @param id id or {@code null} for none
   */
  public Horse setId(java.lang.Long id) {
    this.id = id;
    return this;
  }

  /**
   * @return value or {@code null} for none
   */
  public com.google.api.client.util.DateTime getRegDate() {
    return regDate;
  }

  /**
   * @param regDate regDate or {@code null} for none
   */
  public Horse setRegDate(com.google.api.client.util.DateTime regDate) {
    this.regDate = regDate;
    return this;
  }

  @Override
  public Horse set(String fieldName, Object value) {
    return (Horse) super.set(fieldName, value);
  }

  @Override
  public Horse clone() {
    return (Horse) super.clone();
  }

}
