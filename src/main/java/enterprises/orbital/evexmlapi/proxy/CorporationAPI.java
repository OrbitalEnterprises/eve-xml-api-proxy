package enterprises.orbital.evexmlapi.proxy;

import java.io.IOException;
import java.util.ArrayList;
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
import enterprises.orbital.evexmlapi.crp.IContainerLog;
import enterprises.orbital.evexmlapi.crp.ICorporationAPI;
import enterprises.orbital.evexmlapi.crp.ICorporationMedal;
import enterprises.orbital.evexmlapi.crp.ICorporationSheet;
import enterprises.orbital.evexmlapi.crp.ICustomsOffice;
import enterprises.orbital.evexmlapi.crp.IFacility;
import enterprises.orbital.evexmlapi.crp.IMemberMedal;
import enterprises.orbital.evexmlapi.crp.IMemberSecurity;
import enterprises.orbital.evexmlapi.crp.IMemberSecurityLog;
import enterprises.orbital.evexmlapi.crp.IMemberTracking;
import enterprises.orbital.evexmlapi.crp.IOutpost;
import enterprises.orbital.evexmlapi.crp.IOutpostServiceDetail;
import enterprises.orbital.evexmlapi.crp.IShareholder;
import enterprises.orbital.evexmlapi.crp.IStarbase;
import enterprises.orbital.evexmlapi.crp.IStarbaseDetail;
import enterprises.orbital.evexmlapi.crp.ITitle;
import enterprises.orbital.evexmlapi.shared.IAccountBalance;
import enterprises.orbital.evexmlapi.shared.IAsset;
import enterprises.orbital.evexmlapi.shared.IBlueprint;
import enterprises.orbital.evexmlapi.shared.IBookmarkFolder;
import enterprises.orbital.evexmlapi.shared.IContactSet;
import enterprises.orbital.evexmlapi.shared.IContract;
import enterprises.orbital.evexmlapi.shared.IContractBid;
import enterprises.orbital.evexmlapi.shared.IContractItem;
import enterprises.orbital.evexmlapi.shared.IFacWarStats;
import enterprises.orbital.evexmlapi.shared.IIndustryJob;
import enterprises.orbital.evexmlapi.shared.IKill;
import enterprises.orbital.evexmlapi.shared.ILocation;
import enterprises.orbital.evexmlapi.shared.IMarketOrder;
import enterprises.orbital.evexmlapi.shared.IStandingSet;
import enterprises.orbital.evexmlapi.shared.IWalletJournalEntry;
import enterprises.orbital.evexmlapi.shared.IWalletTransaction;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Implement Corporation API category calls.
 */
@Path("/v1/corp")
@Produces({
    "application/json"
})
@Api(tags = {
    "Corporation"
}, produces = "application/json")
public class CorporationAPI extends AbstractAPIEndpoint {

  @Path("/AccountBalance")
  @GET
  @ApiOperation(
      value = "Retrieve corporation account balance",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_accountbalance/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of account balances", response = IAccountBalance.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestAccountBalance(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IAccountBalance> balance = corpAPI.requestAccountBalances();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, balance);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/AssetList")
  @GET
  @ApiOperation(
      value = "Retrieve corporation asset list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_assetlist/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Asset list", response = IAsset.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestAssets(
                                @Context HttpServletRequest req,
                                @Context UriInfo info,
                                @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IAsset> assets = corpAPI.requestAssets();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, assets);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Blueprints")
  @GET
  @ApiOperation(
      value = "Retrieve corporation blueprints",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_blueprints/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Blueprint list", response = IBlueprint.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestBlueprints(
                                    @Context HttpServletRequest req,
                                    @Context UriInfo info,
                                    @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                    @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                    @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IBlueprint> blueprints = corpAPI.requestBlueprints();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, blueprints);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Bookmarks")
  @GET
  @ApiOperation(value = "Retrieve corporation bookmarks", notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_bookmarks/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation bookmark folder list", response = IBookmarkFolder.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestBookmarks(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IBookmarkFolder> bookmarks = corpAPI.requestBookmarks();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, bookmarks);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContactList")
  @GET
  @ApiOperation(
      value = "Retrieve corporation contact list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_contactlist/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation contact list", response = IContactSet.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestContacts(
                                  @Context HttpServletRequest req,
                                  @Context UriInfo info,
                                  @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                  @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                  @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      IContactSet contacts = corpAPI.requestContacts();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, contacts);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContainerLog")
  @GET
  @ApiOperation(value = "Retrieve container logs", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation container log", response = IContainerLog.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestContainerLogs(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                       @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                       @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IContainerLog> bookmarks = corpAPI.requestContainerLogs();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, bookmarks);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Contracts")
  @GET
  @ApiOperation(
      value = "Retrieve corporation contract list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_contracts/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contract list", response = IContract.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestContracts(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IContract> contracts = corpAPI.requestContracts();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, contracts);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContractBids")
  @GET
  @ApiOperation(
      value = "Retrieve corporation contract bids",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_contractbids/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contract bid list", response = IContractBid.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestContractBids(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IContractBid> bids = corpAPI.requestContractBids();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, bids);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContractItems")
  @GET
  @ApiOperation(
      value = "Retrieve contract items for the given contract ID",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_contractitems/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Contract items for the given contract ID", response = IContractItem.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestContractItems(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                       @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                       @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                       @QueryParam("contractID") @ApiParam(name = "contractID", required = true, value = "Contract ID") long contractID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IContractItem> items = corpAPI.requestContractItems(contractID);
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, items);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CorporationSheet")
  @GET
  @ApiOperation(value = "Retrieve corporation sheet", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation sheet", response = ICorporationSheet.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestCorporationSheet(
                                          @Context HttpServletRequest req,
                                          @Context UriInfo info,
                                          @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                          @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                          @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                          @DefaultValue("-1") @QueryParam("corpID") @ApiParam(
                                              name = "corpID",
                                              required = false,
                                              value = "If specified, retrieve the public corporation sheet of the corporation with the given ID (instead of the corporation associated with this key)") long corpID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      ICorporationSheet sheet = corpID != -1 ? corpAPI.requestCorporationSheet(corpID) : corpAPI.requestCorporationSheet();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, sheet);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CustomsOffices")
  @GET
  @ApiOperation(value = "Retrieve corporation customs offices", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Customs offices", response = ICustomsOffice.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestCustomsOffices(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<ICustomsOffice> offices = corpAPI.requestCustomsOffices();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, offices);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Facilities")
  @GET
  @ApiOperation(value = "Retrieve corporation facilities", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Facilities list", response = IFacility.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestFacilities(
                                    @Context HttpServletRequest req,
                                    @Context UriInfo info,
                                    @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                    @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                    @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IFacility> facilities = corpAPI.requestFacilities();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, facilities);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/FacWarStats")
  @GET
  @ApiOperation(value = "Retrieve faction war stats for the faction the corporation is a member of", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Faction war stats", response = IFacWarStats.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestFacWarStats(
                                     @Context HttpServletRequest req,
                                     @Context UriInfo info,
                                     @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                     @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                     @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      IFacWarStats stats = corpAPI.requestFacWarStats();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, stats);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/IndustryJobs")
  @GET
  @ApiOperation(value = "Retrieve corporation industry jobs", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Industry job list", response = IIndustryJob.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestIndustryJobs(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IIndustryJob> jobs = corpAPI.requestIndustryJobs();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, jobs);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/IndustryJobsHistory")
  @GET
  @ApiOperation(value = "Retrieve historical industry jobs for corporation", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Historic industry job list", response = IIndustryJob.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestIndustryJobsHistory(
                                             @Context HttpServletRequest req,
                                             @Context UriInfo info,
                                             @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                             @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                             @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IIndustryJob> jobs = corpAPI.requestIndustryJobsHistory();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, jobs);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/KillMails")
  @GET
  @ApiOperation(value = "Retrieve corporation kill mails", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Kill mail list", response = IKill.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestKillMails(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                   @DefaultValue("-1") @QueryParam("beforeKillID") @ApiParam(
                                       name = "beforeKillID",
                                       required = false,
                                       value = "If present, the upper bound for the kill ID of the returned kills (see third party docs)") long beforeKillID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IKill> kills = beforeKillID != -1 ? corpAPI.requestKillMails(beforeKillID) : corpAPI.requestKillMails();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, kills);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Locations")
  @GET
  @ApiOperation(value = "Retrieve locations for given item IDs", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Location list", response = ILocation.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestLocations(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                   @QueryParam("itemID") @ApiParam(
                                       name = "itemID",
                                       required = true,
                                       value = "IDs of items for which location will be retrieved",
                                       allowMultiple = true) List<Long> itemID) {
    IEveXmlApi api = getApi(info);
    try {
      long[] ids = new long[itemID.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = itemID.get(i);
      }
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<ILocation> locations = corpAPI.requestLocations(ids);
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, locations);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MarketOrders")
  @GET
  @ApiOperation(value = "Retrieve active market orders, or market order by ID", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Market order batch, or a single market order", response = IMarketOrder.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestMarketOrder(
                                     @Context HttpServletRequest req,
                                     @Context UriInfo info,
                                     @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                     @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                     @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                     @DefaultValue("-1") @QueryParam("orderID") @ApiParam(
                                         name = "orderID",
                                         required = false,
                                         value = "If specified, retrieve the market order with the given ID") long orderID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IMarketOrder> orders;
      if (orderID != -1) {
        orders = new ArrayList<IMarketOrder>();
        orders.add(corpAPI.requestMarketOrder(orderID));
      } else {
        orders = corpAPI.requestMarketOrders();
      }
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, orders);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Medals")
  @GET
  @ApiOperation(value = "Retrieve awardable corporation medals", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Awardable corporation medals", response = ICorporationMedal.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestMedals(
                                @Context HttpServletRequest req,
                                @Context UriInfo info,
                                @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<ICorporationMedal> medals = corpAPI.requestMedals();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, medals);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MemberMedals")
  @GET
  @ApiOperation(value = "Retrieve medals awarded by corporation", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of medals corporation has awarded", response = IMemberMedal.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestMemberMedals(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IMemberMedal> medals = corpAPI.requestMemberMedals();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, medals);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MemberSecurity")
  @GET
  @ApiOperation(value = "Retrieve corporation member security settings", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of member security settings", response = IMemberSecurity.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestMemberSecurity(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IMemberSecurity> security = corpAPI.requestMemberSecurity();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, security);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MemberSecurityLog")
  @GET
  @ApiOperation(value = "Retrieve corporation member security changes log", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "List of member security changes", response = IMemberSecurityLog.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestMemberSecurityLog(
                                           @Context HttpServletRequest req,
                                           @Context UriInfo info,
                                           @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                           @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                           @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IMemberSecurityLog> log = corpAPI.requestMemberSecurityLog();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, log);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MemberTracking")
  @GET
  @ApiOperation(value = "Retrieve member tracking information", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Member tracking information list", response = IMemberTracking.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestMemberTracking(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IMemberTracking> medals = corpAPI.requestMemberTracking();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, medals);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/OutpostList")
  @GET
  @ApiOperation(value = "Retrieve corporation outposts", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation outpost list", response = IOutpost.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestOutpostList(
                                     @Context HttpServletRequest req,
                                     @Context UriInfo info,
                                     @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                     @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                     @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IOutpost> outposts = corpAPI.requestOutpostList();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, outposts);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/OutpostServiceDetail")
  @GET
  @ApiOperation(value = "Retrieve service details for a given corporation outpost", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Service details for the provided outpost", response = IOutpostServiceDetail.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestOutpostServiceDetail(
                                              @Context HttpServletRequest req,
                                              @Context UriInfo info,
                                              @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                              @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                              @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                              @QueryParam("itemID") @ApiParam(
                                                  name = "itemID",
                                                  required = true,
                                                  value = "Outpost ID of the outpost for which services will be retrieved") long itemID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IOutpostServiceDetail> services = corpAPI.requestOutpostServiceDetail(itemID);
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, services);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Shareholders")
  @GET
  @ApiOperation(value = "Retrieve corporation shareholders", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation shareholder list", response = IShareholder.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestShareholders(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IShareholder> shareholders = corpAPI.requestShareholders();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, shareholders);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Standings")
  @GET
  @ApiOperation(value = "Retrieve corporation standings", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation standings with other entities", response = IStandingSet.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestStandings(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      IStandingSet standings = corpAPI.requestStandings();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, standings);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/StarbaseDetail")
  @GET
  @ApiOperation(value = "Retrieve detail settings for a given starbase", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Details for the provided starbase", response = IStarbaseDetail.class),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestStarbaseDetail(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                        @QueryParam("posID") @ApiParam(
                                            name = "posID",
                                            required = true,
                                            value = "Starbase ID of the starbase for which details will be retrieved") long posID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      IStarbaseDetail detail = corpAPI.requestStarbaseDetail(posID);
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, detail);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/StarbaseList")
  @GET
  @ApiOperation(value = "Retrieve corporation starbases", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation starbase list", response = IStarbase.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestStarbaseList(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IStarbase> starbases = corpAPI.requestStarbaseList();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, starbases);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Titles")
  @GET
  @ApiOperation(value = "Retrieve corporation titles", notes = "TBD")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Corporation title list", response = ITitle.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestTitles(
                                @Context HttpServletRequest req,
                                @Context UriInfo info,
                                @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<ITitle> titles = corpAPI.requestTitles();
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, titles);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/WalletJournal")
  @GET
  @ApiOperation(
      value = "Retrieve corporation wallet journal",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_walletjournal/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Wallet journal", response = IWalletJournalEntry.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestWalletJournalEntries(
                                              @Context HttpServletRequest req,
                                              @Context UriInfo info,
                                              @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                              @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                              @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                              @QueryParam("account") @ApiParam(
                                                  name = "account",
                                                  required = true,
                                                  value = "Wallet account key (1000 through 1006)") int account,
                                              @DefaultValue("-1") @QueryParam("beforeRefID") @ApiParam(
                                                  name = "beforeRefID",
                                                  required = false,
                                                  value = "If present, the upper bound for the ref ID of returned journal entries (see third party docs)") long beforeRefID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IWalletJournalEntry> entries = beforeRefID != -1 ? corpAPI.requestWalletJournalEntries(account, beforeRefID)
          : corpAPI.requestWalletJournalEntries(account);
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, entries);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/WalletTransactions")
  @GET
  @ApiOperation(
      value = "Retrieve corporation wallet transactions",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/corp_wallettransactions/")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Wallet transactions", response = IWalletTransaction.class, responseContainer = "array"),
      @ApiResponse(code = 404, message = "EVE XML API server error", response = EveServerError.class),
      @ApiResponse(code = 500, message = "Proxy service error", response = ServiceError.class),
  })
  @ApiImplicitParams(@ApiImplicitParam(
      name = "server",
      value = "Optional EVE XML server URL override",
      required = false,
      dataType = "string",
      paramType = "query"))
  public Response requestWalletTransactions(
                                            @Context HttpServletRequest req,
                                            @Context UriInfo info,
                                            @QueryParam("keyID") @ApiParam(name = "keyID", required = true, value = "Access key ID") int keyID,
                                            @QueryParam("vCode") @ApiParam(name = "vCode", required = true, value = "Access vCode") String vCode,
                                            @QueryParam("charID") @ApiParam(name = "charID", required = true, value = "Character ID") long characterID,
                                            @QueryParam("account") @ApiParam(
                                                name = "account",
                                                required = true,
                                                value = "Wallet account key (1000 through 1006)") int account,
                                            @DefaultValue("-1") @QueryParam("beforeTransID") @ApiParam(
                                                name = "beforeTransID",
                                                required = false,
                                                value = "If present, the upper bound for the transaction ID of the returned transactions (see third party docs)") long beforeTransID) {
    IEveXmlApi api = getApi(info);
    try {
      ICorporationAPI corpAPI = api.getCorporationAPIService(keyID, vCode, characterID);
      Collection<IWalletTransaction> txns = beforeTransID != -1 ? corpAPI.requestWalletTransactions(account, beforeTransID)
          : corpAPI.requestWalletTransactions(account);
      if (corpAPI.isError()) return makeResponseErrorResponse(corpAPI);
      return makeResponse(corpAPI, txns);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }
}
