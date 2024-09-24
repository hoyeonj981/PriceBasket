package me.hoyeonj.pricebasket.adapter.out;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ApacheHttpClient implements HttpClient {

  @Override
  public ResponseDocument fetchFromUri(final String uri) {
    try (var httpClient = HttpClients.createDefault()) {
      final var httpGet = new HttpGet(uri);
      return httpClient.execute(httpGet, response -> {
        final var statusCode = response.getStatusLine().getStatusCode();
        final var content = EntityUtils.toString(response.getEntity());
        final var allHeaders = response.getAllHeaders();
        var collect = Arrays.stream(allHeaders).collect(Collectors.groupingBy(
            NameValuePair::getName,
            Collectors.mapping(header -> {
                  var split = header.getValue().split(";");
                  return String.join(", ", split);
                  },
                Collectors.toUnmodifiableList())
        ));
        return new ResponseDocument(uri, statusCode, collect, content);
      });
    } catch (IOException e) {
      throw new HttpRequestException(e);
    }
  }
}
