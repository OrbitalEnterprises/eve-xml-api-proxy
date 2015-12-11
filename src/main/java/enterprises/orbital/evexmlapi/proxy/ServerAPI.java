package enterprises.orbital.evexmlapi.proxy;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import enterprises.orbital.evexmlapi.IEveXmlApi;
import enterprises.orbital.evexmlapi.svr.IServerAPI;
import enterprises.orbital.evexmlapi.svr.IServerStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Implement Server API category calls.
 */
@Path("/v1/server")
@Produces({
    "application/json"
})
@Api(tags = {
    "Server"
}, produces = "application/json")
public class ServerAPI extends AbstractAPIEndpoint {

  @Path("/ServerStatus")
  @GET
  @ApiOperation(
      value = "Retrieve EVE Online server status",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/serv_serversstatus/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "EVE Online server status", response = IServerStatus.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestServerStatus(@Context HttpServletRequest req, @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IServerAPI serverAPI = api.getServerAPIService();
      IServerStatus status = serverAPI.requestServerStatus();
      if (serverAPI.isError()) return makeResponseErrorResponse(serverAPI);
      return makeResponse(serverAPI, status);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

}
