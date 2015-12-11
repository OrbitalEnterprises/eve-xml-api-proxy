package enterprises.orbital.evexmlapi.proxy;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import enterprises.orbital.evexmlapi.IEveXmlApi;
import enterprises.orbital.evexmlapi.map.IFacWarSystem;
import enterprises.orbital.evexmlapi.map.IMapAPI;
import enterprises.orbital.evexmlapi.map.IMapJump;
import enterprises.orbital.evexmlapi.map.IMapKill;
import enterprises.orbital.evexmlapi.map.ISovereignty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Implement Map API category calls.
 */
@Path("/v1/map")
@Produces({
    "application/json"
})
@Api(tags = {
    "Map"
}, produces = "application/json")
public class MapAPI extends AbstractAPIEndpoint {

  @Path("/FacWarSystems")
  @GET
  @ApiOperation(
      value = "Retrieve faction war systems information",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/map_facwarsystems/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of faction war systems", response = IFacWarSystem.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestFacWarSystems(@Context HttpServletRequest req, @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IMapAPI mapAPI = api.getMapAPIService();
      Collection<IFacWarSystem> systems = mapAPI.requestFacWarSystems();
      if (mapAPI.isError()) return makeResponseErrorResponse(mapAPI);
      return makeResponse(mapAPI, systems);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Jumps")
  @GET
  @ApiOperation(value = "Retrieve system jumps", notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/map_jumps/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "System jump information", response = IMapJump.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestJumps(@Context HttpServletRequest req, @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IMapAPI mapAPI = api.getMapAPIService();
      IMapJump jumps = mapAPI.requestJumps();
      if (mapAPI.isError()) return makeResponseErrorResponse(mapAPI);
      return makeResponse(mapAPI, jumps);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Kills")
  @GET
  @ApiOperation(value = "Retrieve system kills", notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/map_kills/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "System kill information", response = IMapKill.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestKills(@Context HttpServletRequest req, @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IMapAPI mapAPI = api.getMapAPIService();
      IMapKill kills = mapAPI.requestKills();
      if (mapAPI.isError()) return makeResponseErrorResponse(mapAPI);
      return makeResponse(mapAPI, kills);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Sovereignty")
  @GET
  @ApiOperation(value = "Retrieve sovereignty status", notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/map_sovereignty/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Sovereignty information", response = ISovereignty.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestSovereignty(@Context HttpServletRequest req, @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IMapAPI mapAPI = api.getMapAPIService();
      ISovereignty sov = mapAPI.requestSovereignty();
      if (mapAPI.isError()) return makeResponseErrorResponse(mapAPI);
      return makeResponse(mapAPI, sov);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

}
