package me.hoyeonj.pricebasket.adapter.out;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toUnmodifiableList;

import java.io.IOException;
import java.util.Arrays;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClient {

  private static final String REGEX = ";";
  private static final String DELIMITER = ", ";

  public ResponseDocument fetchFromUri(final String uri) {
    try (var httpClient = HttpClients.createDefault()) {
      final var httpGet = new HttpGet(uri);
      return httpClient.execute(httpGet, response -> {
        final var statusCode = response.getStatusLine().getStatusCode();
        final var content = EntityUtils.toString(response.getEntity());
        final var allHeaders = response.getAllHeaders();
        final var collect = Arrays.stream(allHeaders)
            .collect(groupingBy(
                NameValuePair::getName,
                mapping(this::splitValues, toUnmodifiableList())));
        return ResponseDocument.create(uri, statusCode, collect, content);
      });
    } catch (IOException e) {
      throw new HttpRequestException(e);
    }
  }

  private String splitValues(final Header header) {
    final var split = header.getValue().split(REGEX);
    return String.join(DELIMITER, split);
  }
}
