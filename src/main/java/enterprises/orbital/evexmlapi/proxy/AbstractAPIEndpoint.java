package enterprises.orbital.evexmlapi.proxy;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import enterprises.orbital.base.OrbitalProperties;
import enterprises.orbital.evexmlapi.EveXmlApiAdapter;
import enterprises.orbital.evexmlapi.EveXmlApiConfig;
import enterprises.orbital.evexmlapi.IEveXmlApi;
import enterprises.orbital.evexmlapi.IResponse;

public abstract class AbstractAPIEndpoint {
  private static final Logger log               = Logger.getLogger(AbstractAPIEndpoint.class.getName());

  // Special query parameters we always extract and apply to config
  public static final String  SERVER_QP         = "server";
  public static final String  SERVER_QP_DEFAULT = "https://api.eveonline.com";
  public static final String  AGENT_OP          = "enterprises.orbital.evexmlapi.proxy.agent";

  private SimpleDateFormat    dateFormat        = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

  public AbstractAPIEndpoint() {
    dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  protected String getQueryParam(UriInfo info, String key, String def) {
    String v = info.getQueryParameters().getFirst(key);
    return v == null ? def : v;
  }

  protected IEveXmlApi getApi(UriInfo info) {
    String server = getQueryParam(info, SERVER_QP, SERVER_QP_DEFAULT);
    String agent = OrbitalProperties.getGlobalProperty(AGENT_OP, "");
    try {
      return new EveXmlApiAdapter(EveXmlApiConfig.get().serverURI(server).agent(agent));
    } catch (URISyntaxException e) {
      log.log(Level.SEVERE, "failed to create IEvreXmlApi", e);
      return null;
    }
  }

  protected String getServerTime(Date tm) {
    return dateFormat.format(tm);
  }

  protected Response makeResponseErrorResponse(IResponse err) {
    return Response.status(Status.NOT_FOUND).entity(new EveServerError(err)).build();
  }

  protected Response makeServiceErrorResponse(Exception e) {
    log.log(Level.WARNING, "API error", e);
    return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ServiceError(e.getMessage())).build();
  }

  protected Response makeResponse(IResponse response, Object data) {
    return Response.ok(data).header("Date", getServerTime(response.getCurrentTime())).expires(response.getCachedUntil())
        .header("EVEXML-Version", response.getEveAPIVersion()).build();
  }
}
