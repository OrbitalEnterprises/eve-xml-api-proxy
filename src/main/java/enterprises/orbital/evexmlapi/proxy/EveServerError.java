package enterprises.orbital.evexmlapi.proxy;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import enterprises.orbital.evexmlapi.IResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "EVE XML server error.  Indicates the EVE XML API server returned an error on the last request.")
public class EveServerError {
  private int    errorCode      = 0;
  private String errorMessage   = "";
  private Date   currentTime    = null;
  private Date   retryAfterDate = null;

  public EveServerError(int errorCode, String errorMessage, Date currentTime, Date retryAfterDate) {
    super();
    this.errorCode = errorCode;
    this.errorMessage = errorMessage;
    this.currentTime = currentTime;
    this.retryAfterDate = retryAfterDate;
  }

  public EveServerError(IResponse response) {
    this(response.getErrorCode(), response.getErrorString(), response.getCurrentTime(), response.getErrorRetryAfterDate());
  }

  @ApiModelProperty(value = "Error code")
  @JsonProperty("errorCode")
  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }

  @ApiModelProperty(value = "Error message")
  @JsonProperty("errorMessage")
  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  @ApiModelProperty(value = "Time of response")
  @JsonProperty("currentTime")
  public Date getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Date currentTime) {
    this.currentTime = currentTime;
  }

  @ApiModelProperty(value = "Time after which the request can be retried")
  @JsonProperty("retryAfterDate")
  public Date getRetryAfterDate() {
    return retryAfterDate;
  }

  public void setRetryAfterDate(Date retryAfterDate) {
    this.retryAfterDate = retryAfterDate;
  }

}
