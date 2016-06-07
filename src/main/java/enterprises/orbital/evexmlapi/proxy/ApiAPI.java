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
import enterprises.orbital.evexmlapi.api.IApiAPI;
import enterprises.orbital.evexmlapi.api.ICallList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Implement Api API category calls.
 */
@Path("/v2/api")
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Api"
    },
    produces = "application/json")
public class ApiAPI extends AbstractAPIEndpoint {

  @Path("/CallList")
  @GET
  @ApiOperation(
      value = "Retrieve EVE Online API call list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/api_calllist/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "EVE Online API call list",
              response = ICallList.class),
          @ApiResponse(
              code = 404,
              message = "EVE XML API server error",
              response = EveServerError.class),
          @ApiResponse(
              code = 500,
              message = "Proxy service error",
              response = ServiceError.class),
      })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestCallList(
                                  @Context HttpServletRequest req,
                                  @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IApiAPI apiAPI = api.getApiAPIService();
      ICallList callList = apiAPI.requestCallList();
      if (apiAPI.isError()) return makeResponseErrorResponse(apiAPI);
      return makeResponse(apiAPI, callList);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

}
