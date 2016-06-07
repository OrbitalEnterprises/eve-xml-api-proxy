package enterprises.orbital.evexmlapi.proxy;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Proxy service error.  Indicates an error occurred handling the request within the EVE XML API proxy service.")
public class ServiceError {
  private String message = null;

  public ServiceError(String message) {
    super();
    this.message = message;
  }

  @ApiModelProperty(value = "Error message")
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
