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
 * on 2016-02-04 at 04:52:27 UTC 
 * Modify at your own risk.
 */

package com.sporthorsetech.horseshoepad.backend.horseApi;

/**
 * Service definition for HorseApi (v1).
 *
 * <p>
 * This is an API
 * </p>
 *
 * <p>
 * For more information about this service, see the
 * <a href="" target="_blank">API Documentation</a>
 * </p>
 *
 * <p>
 * This service uses {@link HorseApiRequestInitializer} to initialize global parameters via its
 * {@link Builder}.
 * </p>
 *
 * @since 1.3
 * @author Google, Inc.
 */
@SuppressWarnings("javadoc")
public class HorseApi extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient {

  // Note: Leave this static initializer at the top of the file.
  static {
    com.google.api.client.util.Preconditions.checkState(
        com.google.api.client.googleapis.GoogleUtils.MAJOR_VERSION == 1 &&
        com.google.api.client.googleapis.GoogleUtils.MINOR_VERSION >= 15,
        "You are currently running with version %s of google-api-client. " +
        "You need at least version 1.15 of google-api-client to run version " +
        "1.21.0 of the horseApi library.", com.google.api.client.googleapis.GoogleUtils.VERSION);
  }

  /**
   * The default encoded root URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_ROOT_URL = "https://sporthorsetech.appspot.com/_ah/api/";

  /**
   * The default encoded service path of the service. This is determined when the library is
   * generated and normally should not be changed.
   *
   * @since 1.7
   */
  public static final String DEFAULT_SERVICE_PATH = "horseApi/v1/";

  /**
   * The default encoded base URL of the service. This is determined when the library is generated
   * and normally should not be changed.
   */
  public static final String DEFAULT_BASE_URL = DEFAULT_ROOT_URL + DEFAULT_SERVICE_PATH;

  /**
   * Constructor.
   *
   * <p>
   * Use {@link Builder} if you need to specify any of the optional parameters.
   * </p>
   *
   * @param transport HTTP transport, which should normally be:
   *        <ul>
   *        <li>Google App Engine:
   *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
   *        <li>Android: {@code newCompatibleTransport} from
   *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
   *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
   *        </li>
   *        </ul>
   * @param jsonFactory JSON factory, which may be:
   *        <ul>
   *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
   *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
   *        <li>Android Honeycomb or higher:
   *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
   *        </ul>
   * @param httpRequestInitializer HTTP request initializer or {@code null} for none
   * @since 1.7
   */
  public HorseApi(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
      com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
    this(new Builder(transport, jsonFactory, httpRequestInitializer));
  }

  /**
   * @param builder builder
   */
  HorseApi(Builder builder) {
    super(builder);
  }

  @Override
  protected void initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest<?> httpClientRequest) throws java.io.IOException {
    super.initialize(httpClientRequest);
  }

  /**
   * An accessor for creating requests from the Horse collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code HorseApi horseApi = new HorseApi(...);}
   *   {@code HorseApi.Horse.List request = horseApi.horse().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Horse horse() {
    return new Horse();
  }

  /**
   * The "horse" collection of methods.
   */
  public class Horse {

    /**
     * Create a request for the method "horse.remove".
     *
     * This request holds the parameters needed by the horseApi server.  After setting any optional
     * parameters, call the {@link Remove#execute()} method to invoke the remote operation.
     *
     * @param id
     * @return the request
     */
    public Remove remove(java.lang.Long id) throws java.io.IOException {
      Remove result = new Remove(id);
      initialize(result);
      return result;
    }

    public class Remove extends HorseApiRequest<com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse> {

      private static final String REST_PATH = "horse/{id}";

      /**
       * Create a request for the method "horse.remove".
       *
       * This request holds the parameters needed by the the horseApi server.  After setting any
       * optional parameters, call the {@link Remove#execute()} method to invoke the remote operation.
       * <p> {@link
       * Remove#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param id
       * @since 1.13
       */
      protected Remove(java.lang.Long id) {
        super(HorseApi.this, "DELETE", REST_PATH, null, com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse.class);
        this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
      }

      @Override
      public Remove setAlt(java.lang.String alt) {
        return (Remove) super.setAlt(alt);
      }

      @Override
      public Remove setFields(java.lang.String fields) {
        return (Remove) super.setFields(fields);
      }

      @Override
      public Remove setKey(java.lang.String key) {
        return (Remove) super.setKey(key);
      }

      @Override
      public Remove setOauthToken(java.lang.String oauthToken) {
        return (Remove) super.setOauthToken(oauthToken);
      }

      @Override
      public Remove setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Remove) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Remove setQuotaUser(java.lang.String quotaUser) {
        return (Remove) super.setQuotaUser(quotaUser);
      }

      @Override
      public Remove setUserIp(java.lang.String userIp) {
        return (Remove) super.setUserIp(userIp);
      }

      @com.google.api.client.util.Key
      private java.lang.Long id;

      /**

       */
      public java.lang.Long getId() {
        return id;
      }

      public Remove setId(java.lang.Long id) {
        this.id = id;
        return this;
      }

      @Override
      public Remove set(String parameterName, Object value) {
        return (Remove) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "horse.save".
     *
     * This request holds the parameters needed by the horseApi server.  After setting any optional
     * parameters, call the {@link Save#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse}
     * @return the request
     */
    public Save save(com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse content) throws java.io.IOException {
      Save result = new Save(content);
      initialize(result);
      return result;
    }

    public class Save extends HorseApiRequest<com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse> {

      private static final String REST_PATH = "horse";

      /**
       * Create a request for the method "horse.save".
       *
       * This request holds the parameters needed by the the horseApi server.  After setting any
       * optional parameters, call the {@link Save#execute()} method to invoke the remote operation. <p>
       * {@link Save#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse}
       * @since 1.13
       */
      protected Save(com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse content) {
        super(HorseApi.this, "POST", REST_PATH, content, com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse.class);
      }

      @Override
      public Save setAlt(java.lang.String alt) {
        return (Save) super.setAlt(alt);
      }

      @Override
      public Save setFields(java.lang.String fields) {
        return (Save) super.setFields(fields);
      }

      @Override
      public Save setKey(java.lang.String key) {
        return (Save) super.setKey(key);
      }

      @Override
      public Save setOauthToken(java.lang.String oauthToken) {
        return (Save) super.setOauthToken(oauthToken);
      }

      @Override
      public Save setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Save) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Save setQuotaUser(java.lang.String quotaUser) {
        return (Save) super.setQuotaUser(quotaUser);
      }

      @Override
      public Save setUserIp(java.lang.String userIp) {
        return (Save) super.setUserIp(userIp);
      }

      @Override
      public Save set(String parameterName, Object value) {
        return (Save) super.set(parameterName, value);
      }
    }
    /**
     * Create a request for the method "horse.update".
     *
     * This request holds the parameters needed by the horseApi server.  After setting any optional
     * parameters, call the {@link Update#execute()} method to invoke the remote operation.
     *
     * @param content the {@link com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse}
     * @return the request
     */
    public Update update(com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse content) throws java.io.IOException {
      Update result = new Update(content);
      initialize(result);
      return result;
    }

    public class Update extends HorseApiRequest<com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse> {

      private static final String REST_PATH = "horse";

      /**
       * Create a request for the method "horse.update".
       *
       * This request holds the parameters needed by the the horseApi server.  After setting any
       * optional parameters, call the {@link Update#execute()} method to invoke the remote operation.
       * <p> {@link
       * Update#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)} must
       * be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @param content the {@link com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse}
       * @since 1.13
       */
      protected Update(com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse content) {
        super(HorseApi.this, "PUT", REST_PATH, content, com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse.class);
      }

      @Override
      public Update setAlt(java.lang.String alt) {
        return (Update) super.setAlt(alt);
      }

      @Override
      public Update setFields(java.lang.String fields) {
        return (Update) super.setFields(fields);
      }

      @Override
      public Update setKey(java.lang.String key) {
        return (Update) super.setKey(key);
      }

      @Override
      public Update setOauthToken(java.lang.String oauthToken) {
        return (Update) super.setOauthToken(oauthToken);
      }

      @Override
      public Update setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (Update) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public Update setQuotaUser(java.lang.String quotaUser) {
        return (Update) super.setQuotaUser(quotaUser);
      }

      @Override
      public Update setUserIp(java.lang.String userIp) {
        return (Update) super.setUserIp(userIp);
      }

      @Override
      public Update set(String parameterName, Object value) {
        return (Update) super.set(parameterName, value);
      }
    }

    /**
     * An accessor for creating requests from the By collection.
     *
     * <p>The typical use is:</p>
     * <pre>
     *   {@code HorseApi horseApi = new HorseApi(...);}
     *   {@code HorseApi.By.List request = horseApi.by().list(parameters ...)}
     * </pre>
     *
     * @return the resource collection
     */
    public By by() {
      return new By();
    }

    /**
     * The "by" collection of methods.
     */
    public class By {

      /**
       * Create a request for the method "by.id".
       *
       * This request holds the parameters needed by the horseApi server.  After setting any optional
       * parameters, call the {@link Id#execute()} method to invoke the remote operation.
       *
       * @param id
       * @return the request
       */
      public Id id(java.lang.Long id) throws java.io.IOException {
        Id result = new Id(id);
        initialize(result);
        return result;
      }

      public class Id extends HorseApiRequest<com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse> {

        private static final String REST_PATH = "horse/{id}";

        /**
         * Create a request for the method "by.id".
         *
         * This request holds the parameters needed by the the horseApi server.  After setting any
         * optional parameters, call the {@link Id#execute()} method to invoke the remote operation. <p>
         * {@link Id#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
         * must be called to initialize this instance immediately after invoking the constructor. </p>
         *
         * @param id
         * @since 1.13
         */
        protected Id(java.lang.Long id) {
          super(HorseApi.this, "GET", REST_PATH, null, com.sporthorsetech.horseshoepad.backend.horseApi.model.Horse.class);
          this.id = com.google.api.client.util.Preconditions.checkNotNull(id, "Required parameter id must be specified.");
        }

        @Override
        public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
          return super.executeUsingHead();
        }

        @Override
        public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
          return super.buildHttpRequestUsingHead();
        }

        @Override
        public Id setAlt(java.lang.String alt) {
          return (Id) super.setAlt(alt);
        }

        @Override
        public Id setFields(java.lang.String fields) {
          return (Id) super.setFields(fields);
        }

        @Override
        public Id setKey(java.lang.String key) {
          return (Id) super.setKey(key);
        }

        @Override
        public Id setOauthToken(java.lang.String oauthToken) {
          return (Id) super.setOauthToken(oauthToken);
        }

        @Override
        public Id setPrettyPrint(java.lang.Boolean prettyPrint) {
          return (Id) super.setPrettyPrint(prettyPrint);
        }

        @Override
        public Id setQuotaUser(java.lang.String quotaUser) {
          return (Id) super.setQuotaUser(quotaUser);
        }

        @Override
        public Id setUserIp(java.lang.String userIp) {
          return (Id) super.setUserIp(userIp);
        }

        @com.google.api.client.util.Key
        private java.lang.Long id;

        /**

         */
        public java.lang.Long getId() {
          return id;
        }

        public Id setId(java.lang.Long id) {
          this.id = id;
          return this;
        }

        @Override
        public Id set(String parameterName, Object value) {
          return (Id) super.set(parameterName, value);
        }
      }

    }
  }

  /**
   * An accessor for creating requests from the Horses collection.
   *
   * <p>The typical use is:</p>
   * <pre>
   *   {@code HorseApi horseApi = new HorseApi(...);}
   *   {@code HorseApi.Horses.List request = horseApi.horses().list(parameters ...)}
   * </pre>
   *
   * @return the resource collection
   */
  public Horses horses() {
    return new Horses();
  }

  /**
   * The "horses" collection of methods.
   */
  public class Horses {

    /**
     * Create a request for the method "horses.list".
     *
     * This request holds the parameters needed by the horseApi server.  After setting any optional
     * parameters, call the {@link List#execute()} method to invoke the remote operation.
     *
     * @return the request
     */
    public List list() throws java.io.IOException {
      List result = new List();
      initialize(result);
      return result;
    }

    public class List extends HorseApiRequest<com.sporthorsetech.horseshoepad.backend.horseApi.model.CollectionResponseHorse> {

      private static final String REST_PATH = "horse";

      /**
       * Create a request for the method "horses.list".
       *
       * This request holds the parameters needed by the the horseApi server.  After setting any
       * optional parameters, call the {@link List#execute()} method to invoke the remote operation. <p>
       * {@link List#initialize(com.google.api.client.googleapis.services.AbstractGoogleClientRequest)}
       * must be called to initialize this instance immediately after invoking the constructor. </p>
       *
       * @since 1.13
       */
      protected List() {
        super(HorseApi.this, "GET", REST_PATH, null, com.sporthorsetech.horseshoepad.backend.horseApi.model.CollectionResponseHorse.class);
      }

      @Override
      public com.google.api.client.http.HttpResponse executeUsingHead() throws java.io.IOException {
        return super.executeUsingHead();
      }

      @Override
      public com.google.api.client.http.HttpRequest buildHttpRequestUsingHead() throws java.io.IOException {
        return super.buildHttpRequestUsingHead();
      }

      @Override
      public List setAlt(java.lang.String alt) {
        return (List) super.setAlt(alt);
      }

      @Override
      public List setFields(java.lang.String fields) {
        return (List) super.setFields(fields);
      }

      @Override
      public List setKey(java.lang.String key) {
        return (List) super.setKey(key);
      }

      @Override
      public List setOauthToken(java.lang.String oauthToken) {
        return (List) super.setOauthToken(oauthToken);
      }

      @Override
      public List setPrettyPrint(java.lang.Boolean prettyPrint) {
        return (List) super.setPrettyPrint(prettyPrint);
      }

      @Override
      public List setQuotaUser(java.lang.String quotaUser) {
        return (List) super.setQuotaUser(quotaUser);
      }

      @Override
      public List setUserIp(java.lang.String userIp) {
        return (List) super.setUserIp(userIp);
      }

      @Override
      public List set(String parameterName, Object value) {
        return (List) super.set(parameterName, value);
      }
    }

  }

  /**
   * Builder for {@link HorseApi}.
   *
   * <p>
   * Implementation is not thread-safe.
   * </p>
   *
   * @since 1.3.0
   */
  public static final class Builder extends com.google.api.client.googleapis.services.json.AbstractGoogleJsonClient.Builder {

    /**
     * Returns an instance of a new builder.
     *
     * @param transport HTTP transport, which should normally be:
     *        <ul>
     *        <li>Google App Engine:
     *        {@code com.google.api.client.extensions.appengine.http.UrlFetchTransport}</li>
     *        <li>Android: {@code newCompatibleTransport} from
     *        {@code com.google.api.client.extensions.android.http.AndroidHttp}</li>
     *        <li>Java: {@link com.google.api.client.googleapis.javanet.GoogleNetHttpTransport#newTrustedTransport()}
     *        </li>
     *        </ul>
     * @param jsonFactory JSON factory, which may be:
     *        <ul>
     *        <li>Jackson: {@code com.google.api.client.json.jackson2.JacksonFactory}</li>
     *        <li>Google GSON: {@code com.google.api.client.json.gson.GsonFactory}</li>
     *        <li>Android Honeycomb or higher:
     *        {@code com.google.api.client.extensions.android.json.AndroidJsonFactory}</li>
     *        </ul>
     * @param httpRequestInitializer HTTP request initializer or {@code null} for none
     * @since 1.7
     */
    public Builder(com.google.api.client.http.HttpTransport transport, com.google.api.client.json.JsonFactory jsonFactory,
        com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      super(
          transport,
          jsonFactory,
          DEFAULT_ROOT_URL,
          DEFAULT_SERVICE_PATH,
          httpRequestInitializer,
          false);
    }

    /** Builds a new instance of {@link HorseApi}. */
    @Override
    public HorseApi build() {
      return new HorseApi(this);
    }

    @Override
    public Builder setRootUrl(String rootUrl) {
      return (Builder) super.setRootUrl(rootUrl);
    }

    @Override
    public Builder setServicePath(String servicePath) {
      return (Builder) super.setServicePath(servicePath);
    }

    @Override
    public Builder setHttpRequestInitializer(com.google.api.client.http.HttpRequestInitializer httpRequestInitializer) {
      return (Builder) super.setHttpRequestInitializer(httpRequestInitializer);
    }

    @Override
    public Builder setApplicationName(String applicationName) {
      return (Builder) super.setApplicationName(applicationName);
    }

    @Override
    public Builder setSuppressPatternChecks(boolean suppressPatternChecks) {
      return (Builder) super.setSuppressPatternChecks(suppressPatternChecks);
    }

    @Override
    public Builder setSuppressRequiredParameterChecks(boolean suppressRequiredParameterChecks) {
      return (Builder) super.setSuppressRequiredParameterChecks(suppressRequiredParameterChecks);
    }

    @Override
    public Builder setSuppressAllChecks(boolean suppressAllChecks) {
      return (Builder) super.setSuppressAllChecks(suppressAllChecks);
    }

    /**
     * Set the {@link HorseApiRequestInitializer}.
     *
     * @since 1.12
     */
    public Builder setHorseApiRequestInitializer(
        HorseApiRequestInitializer horseapiRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(horseapiRequestInitializer);
    }

    @Override
    public Builder setGoogleClientRequestInitializer(
        com.google.api.client.googleapis.services.GoogleClientRequestInitializer googleClientRequestInitializer) {
      return (Builder) super.setGoogleClientRequestInitializer(googleClientRequestInitializer);
    }
  }
}
