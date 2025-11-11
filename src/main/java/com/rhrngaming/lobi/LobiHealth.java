package com.rhrngaming.lobi;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

@Component
@Endpoint(id = "lobi-backend")
public class LobiHealth {

  @ReadOperation
  public String customHealthEndpoint() {
    return """
                {
                    "status" : "This bish is working!"
                }
                """;
  }
}
