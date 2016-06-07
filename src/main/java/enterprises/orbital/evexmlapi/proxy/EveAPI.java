package enterprises.orbital.evexmlapi.proxy;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import enterprises.orbital.evexmlapi.IEveXmlApi;
import enterprises.orbital.evexmlapi.eve.IAlliance;
import enterprises.orbital.evexmlapi.eve.ICharacterAffiliation;
import enterprises.orbital.evexmlapi.eve.ICharacterInfo;
import enterprises.orbital.evexmlapi.eve.ICharacterLookup;
import enterprises.orbital.evexmlapi.eve.IConquerableStation;
import enterprises.orbital.evexmlapi.eve.IError;
import enterprises.orbital.evexmlapi.eve.IEveAPI;
import enterprises.orbital.evexmlapi.eve.IFacWarSummary;
import enterprises.orbital.evexmlapi.eve.IFacWarTopSummary;
import enterprises.orbital.evexmlapi.eve.IRefType;
import enterprises.orbital.evexmlapi.eve.ISkillGroup;
import enterprises.orbital.evexmlapi.eve.ITypeName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Implement EVE API category calls.
 */
@Path("/v2/eve")
@Produces({
    "application/json"
})
@Api(
    tags = {
        "EVE"
    },
    produces = "application/json")
public class EveAPI extends AbstractAPIEndpoint {

  @Path("/AllianceList")
  @GET
  @ApiOperation(
      value = "Request alliance list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_alliancelist/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Alliance list",
              response = IAlliance.class,
              responseContainer = "array"),
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
  public Response requestAlliances(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<IAlliance> alliances = eveAPI.requestAlliances();
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, alliances);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CharacterAffiliation")
  @GET
  @ApiOperation(
      value = "Request character affiliation by ID",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_characteraffiliation/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character affiliation information",
              response = ICharacterAffiliation.class,
              responseContainer = "array"),
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
  public Response requestCharacterAffiliation(
                                              @Context HttpServletRequest req,
                                              @Context UriInfo info,
                                              @QueryParam("charid") @ApiParam(
                                                  name = "charid",
                                                  required = true,
                                                  value = "IDs of characters to request affiliation",
                                                  allowMultiple = true) List<Long> charID) {
    IEveXmlApi api = getApi(info);
    try {
      long[] ids = new long[charID.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = charID.get(i);
      }
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<ICharacterAffiliation> affiliations = eveAPI.requestCharacterAffiliation(ids);
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, affiliations);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CharacterID")
  @GET
  @ApiOperation(
      value = "Request character ID by name",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_characterid/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character name list",
              response = ICharacterLookup.class,
              responseContainer = "array"),
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
  public Response requestCharacterID(
                                     @Context HttpServletRequest req,
                                     @Context UriInfo info,
                                     @QueryParam("name") @ApiParam(
                                         name = "name",
                                         required = true,
                                         value = "Names of character IDs to request",
                                         allowMultiple = true) List<String> name) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<ICharacterLookup> chars = eveAPI.requestCharacterID(name.toArray(new String[name.size()]));
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, chars);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CharacterInfo")
  @GET
  @ApiOperation(
      value = "Request character information",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_characterinfo/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character information",
              response = ICharacterInfo.class),
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
  public Response requestCharacterInfo(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @DefaultValue("-1") @QueryParam("keyID") @ApiParam(
                                           name = "keyID",
                                           required = false,
                                           value = "If specified, request private character information (requires vCode)") int keyID,
                                       @DefaultValue("") @QueryParam("vCode") @ApiParam(
                                           name = "vCode",
                                           required = false,
                                           value = "If specified, request private character information (requires keyID)") String vCode,
                                       @QueryParam("characterID") @ApiParam(
                                           name = "characterID",
                                           required = true,
                                           value = "Character ID of character to request") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      ICharacterInfo charInfo = keyID == -1 || vCode.equals("") ? eveAPI.requestCharacterInfo(characterID)
          : eveAPI.requestCharacterInfo(keyID, vCode, characterID);
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, charInfo);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CharacterName")
  @GET
  @ApiOperation(
      value = "Request character name by ID",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_charactername/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character ID list",
              response = ICharacterLookup.class,
              responseContainer = "array"),
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
  public Response requestCharacterName(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @QueryParam("charid") @ApiParam(
                                           name = "charid",
                                           required = true,
                                           value = "IDs of characters to request",
                                           allowMultiple = true) List<Long> charID) {
    IEveXmlApi api = getApi(info);
    try {
      long[] ids = new long[charID.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = charID.get(i);
      }
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<ICharacterLookup> chars = eveAPI.requestCharacterName(ids);
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, chars);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ConquerableStationList")
  @GET
  @ApiOperation(
      value = "Request conquerable station list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_conquerablestationlist/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Conquerable station list",
              response = IConquerableStation.class,
              responseContainer = "array"),
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
  public Response requestConquerableStations(
                                             @Context HttpServletRequest req,
                                             @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<IConquerableStation> stations = eveAPI.requestConquerableStations();
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, stations);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ErrorList")
  @GET
  @ApiOperation(
      value = "Request error code list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_errorlist/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Error code list",
              response = IError.class,
              responseContainer = "array"),
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
  public Response requestErrors(
                                @Context HttpServletRequest req,
                                @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<IError> errors = eveAPI.requestErrors();
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, errors);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/FacWarStats")
  @GET
  @ApiOperation(
      value = "Request faction war summary stats",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Faction war summary",
              response = IFacWarSummary.class),
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
  public Response requestFacWarStats(
                                     @Context HttpServletRequest req,
                                     @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      IFacWarSummary summary = eveAPI.requestFacWarStats();
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, summary);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/FacWarTopStats")
  @GET
  @ApiOperation(
      value = "Request faction war summary top stats",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Faction war summary top stats",
              response = IFacWarTopSummary.class),
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
  public Response requestFacWarTopStats(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      IFacWarTopSummary stats = eveAPI.requestFacWarTopStats();
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, stats);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/RefTypes")
  @GET
  @ApiOperation(
      value = "Request reference type list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_reftypes/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Reference type list",
              response = IRefType.class,
              responseContainer = "array"),
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
  public Response requestRefTypes(
                                  @Context HttpServletRequest req,
                                  @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<IRefType> refs = eveAPI.requestRefTypes();
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, refs);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/SkillTree")
  @GET
  @ApiOperation(
      value = "Request skill tree",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Skill tree",
              response = ISkillGroup.class,
              responseContainer = "array"),
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
  public Response requestSkillTree(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info) {
    IEveXmlApi api = getApi(info);
    try {
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<ISkillGroup> tree = eveAPI.requestSkillTree();
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, tree);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/TypeName")
  @GET
  @ApiOperation(
      value = "Request type information by ID",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/eve_typename/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Type information list",
              response = ITypeName.class,
              responseContainer = "array"),
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
  public Response requestTypeName(
                                  @Context HttpServletRequest req,
                                  @Context UriInfo info,
                                  @QueryParam("typeid") @ApiParam(
                                      name = "typeid",
                                      required = true,
                                      value = "IDs of types to request",
                                      allowMultiple = true) List<Integer> typeID) {
    IEveXmlApi api = getApi(info);
    try {
      int[] ids = new int[typeID.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = typeID.get(i);
      }
      IEveAPI eveAPI = api.getEveAPIService();
      Collection<ITypeName> types = eveAPI.requestTypeName(ids);
      if (eveAPI.isError()) return makeResponseErrorResponse(eveAPI);
      return makeResponse(eveAPI, types);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

}
