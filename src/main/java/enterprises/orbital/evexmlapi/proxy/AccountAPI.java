package enterprises.orbital.evexmlapi.proxy;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import enterprises.orbital.evexmlapi.IEveXmlApi;
import enterprises.orbital.evexmlapi.act.IAPIKeyInfo;
import enterprises.orbital.evexmlapi.act.IAccountAPI;
import enterprises.orbital.evexmlapi.act.IAccountStatus;
import enterprises.orbital.evexmlapi.act.ICharacter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Implement Account API category calls.
 */
@Path("/v1/account")
@Produces({
    "application/json"
})
@Api(tags = {
    "Account"
}, produces = "application/json")
public class AccountAPI extends AbstractAPIEndpoint {

  @Path("/AccountStatus")
  @GET
  @ApiOperation(value = "Retrieve account status", notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/account_accountstatus/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Account status", response = IAccountStatus.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestAccountStatus(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                       @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode.") String vCode) {
    IEveXmlApi api = getApi(info);
    try {
      IAccountAPI accountAPI = api.getAccountAPIService(keyID, vCode);
      IAccountStatus status = accountAPI.requestAccountStatus();
      if (accountAPI.isError()) return makeResponseErrorResponse(accountAPI);
      return makeResponse(accountAPI, status);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/APIKeyInfo")
  @GET
  @ApiOperation(value = "Retrieve API key info", notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/account_apikeyinfo/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "API key info", response = IAPIKeyInfo.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestAPIKeyInfo(
                                    @Context HttpServletRequest req,
                                    @Context UriInfo info,
                                    @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                    @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode.") String vCode) {
    IEveXmlApi api = getApi(info);
    try {
      IAccountAPI accountAPI = api.getAccountAPIService(keyID, vCode);
      IAPIKeyInfo keyinfo = accountAPI.requestAPIKeyInfo();
      if (accountAPI.isError()) return makeResponseErrorResponse(accountAPI);
      return makeResponse(accountAPI, keyinfo);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Characters")
  @GET
  @ApiOperation(
      value = "Retrieve account characters",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/account_characters/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Account character list", response = ICharacter.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestCharacters(
                                    @Context HttpServletRequest req,
                                    @Context UriInfo info,
                                    @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                    @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode.") String vCode) {
    IEveXmlApi api = getApi(info);
    try {
      IAccountAPI accountAPI = api.getAccountAPIService(keyID, vCode);
      Collection<ICharacter> characters = accountAPI.requestCharacters();
      if (accountAPI.isError()) return makeResponseErrorResponse(accountAPI);
      return makeResponse(accountAPI, characters);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

}
