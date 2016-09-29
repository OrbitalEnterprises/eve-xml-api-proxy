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
import enterprises.orbital.evexmlapi.chr.ICalendarEventAttendee;
import enterprises.orbital.evexmlapi.chr.ICharacterAPI;
import enterprises.orbital.evexmlapi.chr.ICharacterMedal;
import enterprises.orbital.evexmlapi.chr.ICharacterSheet;
import enterprises.orbital.evexmlapi.chr.IChatChannel;
import enterprises.orbital.evexmlapi.chr.IContactNotification;
import enterprises.orbital.evexmlapi.chr.IMailBody;
import enterprises.orbital.evexmlapi.chr.IMailList;
import enterprises.orbital.evexmlapi.chr.IMailMessage;
import enterprises.orbital.evexmlapi.chr.INotification;
import enterprises.orbital.evexmlapi.chr.INotificationText;
import enterprises.orbital.evexmlapi.chr.IPartialCharacterSheet;
import enterprises.orbital.evexmlapi.chr.IPlanetaryColony;
import enterprises.orbital.evexmlapi.chr.IPlanetaryLink;
import enterprises.orbital.evexmlapi.chr.IPlanetaryPin;
import enterprises.orbital.evexmlapi.chr.IPlanetaryRoute;
import enterprises.orbital.evexmlapi.chr.IResearchAgent;
import enterprises.orbital.evexmlapi.chr.ISkillInQueue;
import enterprises.orbital.evexmlapi.chr.ISkillInTraining;
import enterprises.orbital.evexmlapi.chr.ISkillInfo;
import enterprises.orbital.evexmlapi.chr.IUpcomingCalendarEvent;
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
 * Implement Character API category calls.
 */
@Path("/v2/char")
@Produces({
    "application/json"
})
@Api(
    tags = {
        "Character"
    },
    produces = "application/json")
public class CharacterAPI extends AbstractAPIEndpoint {

  @Path("/AccountBalance")
  @GET
  @ApiOperation(
      value = "Retrieve account balance",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_accountbalance/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Account balance",
              response = IAccountBalance.class),
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
  public Response requestAccountBalance(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(
                                            name = "keyID",
                                            required = true,
                                            value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(
                                            name = "vCode",
                                            required = true,
                                            value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(
                                            name = "charID",
                                            required = true,
                                            value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      IAccountBalance balance = charAPI.requestAccountBalance();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, balance);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/AssetList")
  @GET
  @ApiOperation(
      value = "Retrieve character asset list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_assetlist/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Asset list",
              response = IAsset.class,
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
  public Response requestAssets(
                                @Context HttpServletRequest req,
                                @Context UriInfo info,
                                @QueryParam("keyID") @ApiParam(
                                    name = "keyID",
                                    required = true,
                                    value = "Access key ID") int keyID,
                                @QueryParam("vCode") @ApiParam(
                                    name = "vCode",
                                    required = true,
                                    value = "Access vCode") String vCode,
                                @QueryParam("charID") @ApiParam(
                                    name = "charID",
                                    required = true,
                                    value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IAsset> assets = charAPI.requestAssets();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, assets);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CalendarEventAttendees")
  @GET
  @ApiOperation(
      value = "Retrieve calendar event attendees for given event IDs",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_calendareventattendees/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Calendar event attendee list",
              response = ICalendarEventAttendee.class,
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
  public Response requestCalendarEventAttendees(
                                                @Context HttpServletRequest req,
                                                @Context UriInfo info,
                                                @QueryParam("keyID") @ApiParam(
                                                    name = "keyID",
                                                    required = true,
                                                    value = "Access key ID") int keyID,
                                                @QueryParam("vCode") @ApiParam(
                                                    name = "vCode",
                                                    required = true,
                                                    value = "Access vCode") String vCode,
                                                @QueryParam("charID") @ApiParam(
                                                    name = "charID",
                                                    required = true,
                                                    value = "Character ID") long characterID,
                                                @QueryParam("eventID") @ApiParam(
                                                    name = "eventID",
                                                    required = true,
                                                    value = "Event IDs to retrieve attendees for",
                                                    allowMultiple = true) List<Long> eventID) {
    IEveXmlApi api = getApi(info);
    try {
      long[] ids = new long[eventID.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = eventID.get(i);
      }
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<ICalendarEventAttendee> attendees = charAPI.requestCalendarEventAttendees(ids);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, attendees);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/CharacterSheet")
  @GET
  @ApiOperation(
      value = "Retrieve character sheet",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_charactersheet/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character sheet",
              response = ICharacterSheet.class),
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
  public Response requestCharacterSheet(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(
                                            name = "keyID",
                                            required = true,
                                            value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(
                                            name = "vCode",
                                            required = true,
                                            value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(
                                            name = "charID",
                                            required = true,
                                            value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      ICharacterSheet sheet = charAPI.requestCharacterSheet();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, sheet);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Clones")
  @GET
  @ApiOperation(
      value = "Retrieve partial character sheet including clone information",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_clones/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Partial character sheet with clone information",
              response = IPartialCharacterSheet.class),
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
  public Response requestClones(
                                @Context HttpServletRequest req,
                                @Context UriInfo info,
                                @QueryParam("keyID") @ApiParam(
                                    name = "keyID",
                                    required = true,
                                    value = "Access key ID") int keyID,
                                @QueryParam("vCode") @ApiParam(
                                    name = "vCode",
                                    required = true,
                                    value = "Access vCode") String vCode,
                                @QueryParam("charID") @ApiParam(
                                    name = "charID",
                                    required = true,
                                    value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      IPartialCharacterSheet sheet = charAPI.requestClones();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, sheet);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Bookmarks")
  @GET
  @ApiOperation(
      value = "Retrieve character bookmarks",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_bookmarks/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character bookmark folder list",
              response = IBookmarkFolder.class,
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
  public Response requestBookmarks(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(
                                       name = "keyID",
                                       required = true,
                                       value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(
                                       name = "vCode",
                                       required = true,
                                       value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(
                                       name = "charID",
                                       required = true,
                                       value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IBookmarkFolder> bookmarks = charAPI.requestBookmarks();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, bookmarks);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Blueprints")
  @GET
  @ApiOperation(
      value = "Retrieve character blueprints",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_blueprints/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Blueprint list",
              response = IBlueprint.class,
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
  public Response requestBlueprints(
                                    @Context HttpServletRequest req,
                                    @Context UriInfo info,
                                    @QueryParam("keyID") @ApiParam(
                                        name = "keyID",
                                        required = true,
                                        value = "Access key ID") int keyID,
                                    @QueryParam("vCode") @ApiParam(
                                        name = "vCode",
                                        required = true,
                                        value = "Access vCode") String vCode,
                                    @QueryParam("charID") @ApiParam(
                                        name = "charID",
                                        required = true,
                                        value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IBlueprint> blueprints = charAPI.requestBlueprints();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, blueprints);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ChatChannels")
  @GET
  @ApiOperation(
      value = "Retrieve character chat channels",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_chatchannels/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Chat channel list",
              response = IChatChannel.class,
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
  public Response requestChatChannels(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(
                                          name = "keyID",
                                          required = true,
                                          value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(
                                          name = "vCode",
                                          required = true,
                                          value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(
                                          name = "charID",
                                          required = true,
                                          value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IChatChannel> channels = charAPI.requestChatChannels();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, channels);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContactList")
  @GET
  @ApiOperation(
      value = "Retrieve character contact list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_contactlist/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character contact list",
              response = IContactSet.class),
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
  public Response requestContacts(
                                  @Context HttpServletRequest req,
                                  @Context UriInfo info,
                                  @QueryParam("keyID") @ApiParam(
                                      name = "keyID",
                                      required = true,
                                      value = "Access key ID") int keyID,
                                  @QueryParam("vCode") @ApiParam(
                                      name = "vCode",
                                      required = true,
                                      value = "Access vCode") String vCode,
                                  @QueryParam("charID") @ApiParam(
                                      name = "charID",
                                      required = true,
                                      value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      IContactSet contacts = charAPI.requestContacts();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, contacts);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContactNotifications")
  @GET
  @ApiOperation(
      value = "Retrieve contact notifications liset",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_contactnotifications/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Contact notifications list",
              response = IContactNotification.class,
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
  public Response requestContactNotifications(
                                              @Context HttpServletRequest req,
                                              @Context UriInfo info,
                                              @QueryParam("keyID") @ApiParam(
                                                  name = "keyID",
                                                  required = true,
                                                  value = "Access key ID") int keyID,
                                              @QueryParam("vCode") @ApiParam(
                                                  name = "vCode",
                                                  required = true,
                                                  value = "Access vCode") String vCode,
                                              @QueryParam("charID") @ApiParam(
                                                  name = "charID",
                                                  required = true,
                                                  value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IContactNotification> notifications = charAPI.requestContactNotifications();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, notifications);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Contracts")
  @GET
  @ApiOperation(
      value = "Retrieve character contract list",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_contracts/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Contract list",
              response = IContract.class,
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
  public Response requestContracts(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(
                                       name = "keyID",
                                       required = true,
                                       value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(
                                       name = "vCode",
                                       required = true,
                                       value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(
                                       name = "charID",
                                       required = true,
                                       value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IContract> contracts = charAPI.requestContracts();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, contracts);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContractBids")
  @GET
  @ApiOperation(
      value = "Retrieve character contract bids",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_contractbids/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Contract bid list",
              response = IContractBid.class,
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
  public Response requestContractBids(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(
                                          name = "keyID",
                                          required = true,
                                          value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(
                                          name = "vCode",
                                          required = true,
                                          value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(
                                          name = "charID",
                                          required = true,
                                          value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IContractBid> bids = charAPI.requestContractBids();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, bids);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/ContractItems")
  @GET
  @ApiOperation(
      value = "Retrieve contract items for the given contract ID",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_contractitems/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Contract items for the given contract ID",
              response = IContractItem.class,
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
  public Response requestContractItems(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @QueryParam("keyID") @ApiParam(
                                           name = "keyID",
                                           required = true,
                                           value = "Access key ID") int keyID,
                                       @QueryParam("vCode") @ApiParam(
                                           name = "vCode",
                                           required = true,
                                           value = "Access vCode") String vCode,
                                       @QueryParam("charID") @ApiParam(
                                           name = "charID",
                                           required = true,
                                           value = "Character ID") long characterID,
                                       @QueryParam("contractID") @ApiParam(
                                           name = "contractID",
                                           required = true,
                                           value = "Contract ID") long contractID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IContractItem> items = charAPI.requestContractItems(contractID);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, items);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/FacWarStats")
  @GET
  @ApiOperation(
      value = "Retrieve faction war stats for the faction the character is a member of",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_facwarstats/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Faction war stats",
              response = IFacWarStats.class),
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
                                     @Context UriInfo info,
                                     @QueryParam("keyID") @ApiParam(
                                         name = "keyID",
                                         required = true,
                                         value = "Access key ID") int keyID,
                                     @QueryParam("vCode") @ApiParam(
                                         name = "vCode",
                                         required = true,
                                         value = "Access vCode") String vCode,
                                     @QueryParam("charID") @ApiParam(
                                         name = "charID",
                                         required = true,
                                         value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      IFacWarStats stats = charAPI.requestFacWarStats();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, stats);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/IndustryJobs")
  @GET
  @ApiOperation(
      value = "Retrieve character industry jobs",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Industry job list",
              response = IIndustryJob.class,
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
  public Response requestIndustryJobs(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(
                                          name = "keyID",
                                          required = true,
                                          value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(
                                          name = "vCode",
                                          required = true,
                                          value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(
                                          name = "charID",
                                          required = true,
                                          value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IIndustryJob> jobs = charAPI.requestIndustryJobs();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, jobs);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/IndustryJobsHistory")
  @GET
  @ApiOperation(
      value = "Retrieve historical industry jobs for character",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Historic industry job list",
              response = IIndustryJob.class,
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
  public Response requestIndustryJobsHistory(
                                             @Context HttpServletRequest req,
                                             @Context UriInfo info,
                                             @QueryParam("keyID") @ApiParam(
                                                 name = "keyID",
                                                 required = true,
                                                 value = "Access key ID") int keyID,
                                             @QueryParam("vCode") @ApiParam(
                                                 name = "vCode",
                                                 required = true,
                                                 value = "Access vCode") String vCode,
                                             @QueryParam("charID") @ApiParam(
                                                 name = "charID",
                                                 required = true,
                                                 value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IIndustryJob> jobs = charAPI.requestIndustryJobsHistory();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, jobs);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/KillMails")
  @GET
  @ApiOperation(
      value = "Retrieve character kill mails",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Kill mail list",
              response = IKill.class,
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
  public Response requestKillMails(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(
                                       name = "keyID",
                                       required = true,
                                       value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(
                                       name = "vCode",
                                       required = true,
                                       value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(
                                       name = "charID",
                                       required = true,
                                       value = "Character ID") long characterID,
                                   @DefaultValue("-1") @QueryParam("beforeKillID") @ApiParam(
                                       name = "beforeKillID",
                                       required = false,
                                       value = "If present, the upper bound for the kill ID of the returned kills (see third party docs)") long beforeKillID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IKill> kills = beforeKillID != -1 ? charAPI.requestKillMails(beforeKillID) : charAPI.requestKillMails();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, kills);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Locations")
  @GET
  @ApiOperation(
      value = "Retrieve locations for given item IDs",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Location list",
              response = ILocation.class,
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
  public Response requestLocations(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(
                                       name = "keyID",
                                       required = true,
                                       value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(
                                       name = "vCode",
                                       required = true,
                                       value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(
                                       name = "charID",
                                       required = true,
                                       value = "Character ID") long characterID,
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
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<ILocation> locations = charAPI.requestLocations(ids);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, locations);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MailBodies")
  @GET
  @ApiOperation(
      value = "Retrieve mail bodies for the given message IDs",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Mail bodies list",
              response = IMailBody.class,
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
  public Response requestMailBodies(
                                    @Context HttpServletRequest req,
                                    @Context UriInfo info,
                                    @QueryParam("keyID") @ApiParam(
                                        name = "keyID",
                                        required = true,
                                        value = "Access key ID") int keyID,
                                    @QueryParam("vCode") @ApiParam(
                                        name = "vCode",
                                        required = true,
                                        value = "Access vCode") String vCode,
                                    @QueryParam("charID") @ApiParam(
                                        name = "charID",
                                        required = true,
                                        value = "Character ID") long characterID,
                                    @QueryParam("messageID") @ApiParam(
                                        name = "messageID",
                                        required = true,
                                        value = "IDs of messages for which mail bodies will be retrieved",
                                        allowMultiple = true) List<Long> messageID) {
    IEveXmlApi api = getApi(info);
    try {
      long[] ids = new long[messageID.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = messageID.get(i);
      }
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IMailBody> bodies = charAPI.requestMailBodies(ids);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, bodies);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MailingLists")
  @GET
  @ApiOperation(
      value = "Retrieve mailing lists",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_mailinglists/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "List of mailing lists",
              response = IMailList.class,
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
  public Response requestMailingLists(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(
                                          name = "keyID",
                                          required = true,
                                          value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(
                                          name = "vCode",
                                          required = true,
                                          value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(
                                          name = "charID",
                                          required = true,
                                          value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IMailList> lists = charAPI.requestMailingLists();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, lists);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MailMessages")
  @GET
  @ApiOperation(
      value = "Retrieve mail",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_mailmessages/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "List of messages",
              response = IMailMessage.class,
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
  public Response requestMailMessages(
                                      @Context HttpServletRequest req,
                                      @Context UriInfo info,
                                      @QueryParam("keyID") @ApiParam(
                                          name = "keyID",
                                          required = true,
                                          value = "Access key ID") int keyID,
                                      @QueryParam("vCode") @ApiParam(
                                          name = "vCode",
                                          required = true,
                                          value = "Access vCode") String vCode,
                                      @QueryParam("charID") @ApiParam(
                                          name = "charID",
                                          required = true,
                                          value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IMailMessage> messages = charAPI.requestMailMessages();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, messages);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/MarketOrders")
  @GET
  @ApiOperation(
      value = "Retrieve active market orders, or market order by ID",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_marketorders/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Market order batch, or a single market order",
              response = IMarketOrder.class,
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
  public Response requestMarketOrder(
                                     @Context HttpServletRequest req,
                                     @Context UriInfo info,
                                     @QueryParam("keyID") @ApiParam(
                                         name = "keyID",
                                         required = true,
                                         value = "Access key ID") int keyID,
                                     @QueryParam("vCode") @ApiParam(
                                         name = "vCode",
                                         required = true,
                                         value = "Access vCode") String vCode,
                                     @QueryParam("charID") @ApiParam(
                                         name = "charID",
                                         required = true,
                                         value = "Character ID") long characterID,
                                     @DefaultValue("-1") @QueryParam("orderID") @ApiParam(
                                         name = "orderID",
                                         required = false,
                                         value = "If specified, retrieve the market order with the given ID") long orderID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IMarketOrder> orders;
      if (orderID != -1) {
        orders = new ArrayList<IMarketOrder>();
        orders.add(charAPI.requestMarketOrder(orderID));
      } else {
        orders = charAPI.requestMarketOrders();
      }
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, orders);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Medals")
  @GET
  @ApiOperation(
      value = "Retrieve character medals",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character medals",
              response = ICharacterMedal.class,
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
  public Response requestMedals(
                                @Context HttpServletRequest req,
                                @Context UriInfo info,
                                @QueryParam("keyID") @ApiParam(
                                    name = "keyID",
                                    required = true,
                                    value = "Access key ID") int keyID,
                                @QueryParam("vCode") @ApiParam(
                                    name = "vCode",
                                    required = true,
                                    value = "Access vCode") String vCode,
                                @QueryParam("charID") @ApiParam(
                                    name = "charID",
                                    required = true,
                                    value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<ICharacterMedal> medals = charAPI.requestMedals();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, medals);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Notifications")
  @GET
  @ApiOperation(
      value = "Retrieve notifications",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Notifications",
              response = INotification.class,
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
  public Response requestNotifications(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @QueryParam("keyID") @ApiParam(
                                           name = "keyID",
                                           required = true,
                                           value = "Access key ID") int keyID,
                                       @QueryParam("vCode") @ApiParam(
                                           name = "vCode",
                                           required = true,
                                           value = "Access vCode") String vCode,
                                       @QueryParam("charID") @ApiParam(
                                           name = "charID",
                                           required = true,
                                           value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<INotification> notes = charAPI.requestNotifications();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, notes);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/NotificationTexts")
  @GET
  @ApiOperation(
      value = "Retrieve notification bodies for the given list of notification IDs",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Notification bodies for the given list of notification IDs",
              response = INotificationText.class,
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
  public Response requestNotificationTexts(
                                           @Context HttpServletRequest req,
                                           @Context UriInfo info,
                                           @QueryParam("keyID") @ApiParam(
                                               name = "keyID",
                                               required = true,
                                               value = "Access key ID") int keyID,
                                           @QueryParam("vCode") @ApiParam(
                                               name = "vCode",
                                               required = true,
                                               value = "Access vCode") String vCode,
                                           @QueryParam("charID") @ApiParam(
                                               name = "charID",
                                               required = true,
                                               value = "Character ID") long characterID,
                                           @QueryParam("notificationID") @ApiParam(
                                               name = "notificationID",
                                               required = true,
                                               value = "Notification ID list",
                                               allowMultiple = true) List<Long> notificationID) {
    IEveXmlApi api = getApi(info);
    try {
      long[] ids = new long[notificationID.size()];
      for (int i = 0; i < ids.length; i++) {
        ids[i] = notificationID.get(i);
      }
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<INotificationText> texts = charAPI.requestNotificationTexts(ids);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, texts);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/PlanetaryColonies")
  @GET
  @ApiOperation(
      value = "Retrieve planetary colonies",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Planetary colonies",
              response = IPlanetaryColony.class,
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
  public Response requestPlanetaryColonies(
                                           @Context HttpServletRequest req,
                                           @Context UriInfo info,
                                           @QueryParam("keyID") @ApiParam(
                                               name = "keyID",
                                               required = true,
                                               value = "Access key ID") int keyID,
                                           @QueryParam("vCode") @ApiParam(
                                               name = "vCode",
                                               required = true,
                                               value = "Access vCode") String vCode,
                                           @QueryParam("charID") @ApiParam(
                                               name = "charID",
                                               required = true,
                                               value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IPlanetaryColony> colonies = charAPI.requestPlanetaryColonies();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, colonies);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/PlanetaryLinks")
  @GET
  @ApiOperation(
      value = "Retrieve planetary links for the given planet",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Planetary links for the given planet",
              response = IPlanetaryLink.class,
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
  public Response requestPlanetaryLinks(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(
                                            name = "keyID",
                                            required = true,
                                            value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(
                                            name = "vCode",
                                            required = true,
                                            value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(
                                            name = "charID",
                                            required = true,
                                            value = "Character ID") long characterID,
                                        @QueryParam("planetID") @ApiParam(
                                            name = "planetID",
                                            required = true,
                                            value = "Planet ID") long planetID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IPlanetaryLink> pins = charAPI.requestPlanetaryLinks(planetID);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, pins);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/PlanetaryPins")
  @GET
  @ApiOperation(
      value = "Retrieve planetary pins for the given planet",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Planetary pins for the given planet",
              response = IPlanetaryPin.class,
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
  public Response requestPlanetaryPins(
                                       @Context HttpServletRequest req,
                                       @Context UriInfo info,
                                       @QueryParam("keyID") @ApiParam(
                                           name = "keyID",
                                           required = true,
                                           value = "Access key ID") int keyID,
                                       @QueryParam("vCode") @ApiParam(
                                           name = "vCode",
                                           required = true,
                                           value = "Access vCode") String vCode,
                                       @QueryParam("charID") @ApiParam(
                                           name = "charID",
                                           required = true,
                                           value = "Character ID") long characterID,
                                       @QueryParam("planetID") @ApiParam(
                                           name = "planetID",
                                           required = true,
                                           value = "Planet ID") long planetID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IPlanetaryPin> pins = charAPI.requestPlanetaryPins(planetID);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, pins);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/PlanetaryRoutes")
  @GET
  @ApiOperation(
      value = "Retrieve planetary routes for the given planet",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Planetary routes for the given planet",
              response = IPlanetaryRoute.class,
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
  public Response requestPlanetaryRoutes(
                                         @Context HttpServletRequest req,
                                         @Context UriInfo info,
                                         @QueryParam("keyID") @ApiParam(
                                             name = "keyID",
                                             required = true,
                                             value = "Access key ID") int keyID,
                                         @QueryParam("vCode") @ApiParam(
                                             name = "vCode",
                                             required = true,
                                             value = "Access vCode") String vCode,
                                         @QueryParam("charID") @ApiParam(
                                             name = "charID",
                                             required = true,
                                             value = "Character ID") long characterID,
                                         @QueryParam("planetID") @ApiParam(
                                             name = "planetID",
                                             required = true,
                                             value = "Planet ID") long planetID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IPlanetaryRoute> routes = charAPI.requestPlanetaryRoutes(planetID);
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, routes);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Research")
  @GET
  @ApiOperation(
      value = "Retrieve character research in progress",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character research in progress",
              response = IResearchAgent.class,
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
  public Response requestResearchAgents(
                                        @Context HttpServletRequest req,
                                        @Context UriInfo info,
                                        @QueryParam("keyID") @ApiParam(
                                            name = "keyID",
                                            required = true,
                                            value = "Access key ID") int keyID,
                                        @QueryParam("vCode") @ApiParam(
                                            name = "vCode",
                                            required = true,
                                            value = "Access vCode") String vCode,
                                        @QueryParam("charID") @ApiParam(
                                            name = "charID",
                                            required = true,
                                            value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IResearchAgent> agents = charAPI.requestResearchAgents();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, agents);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/SkillInTraining")
  @GET
  @ApiOperation(
      value = "Retrieve current skill in training",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Current skill in training",
              response = ISkillInTraining.class),
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
  public Response requestSkillInTraining(
                                         @Context HttpServletRequest req,
                                         @Context UriInfo info,
                                         @QueryParam("keyID") @ApiParam(
                                             name = "keyID",
                                             required = true,
                                             value = "Access key ID") int keyID,
                                         @QueryParam("vCode") @ApiParam(
                                             name = "vCode",
                                             required = true,
                                             value = "Access vCode") String vCode,
                                         @QueryParam("charID") @ApiParam(
                                             name = "charID",
                                             required = true,
                                             value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      ISkillInTraining inTraining = charAPI.requestSkillInTraining();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, inTraining);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }

  }

  @Path("/SkillQueue")
  @GET
  @ApiOperation(
      value = "Retrieve current skill queue",
      notes = "TBD")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character skill queue",
              response = ISkillInQueue.class,
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
  public Response requestSkillQueue(
                                    @Context HttpServletRequest req,
                                    @Context UriInfo info,
                                    @QueryParam("keyID") @ApiParam(
                                        name = "keyID",
                                        required = true,
                                        value = "Access key ID") int keyID,
                                    @QueryParam("vCode") @ApiParam(
                                        name = "vCode",
                                        required = true,
                                        value = "Access vCode") String vCode,
                                    @QueryParam("charID") @ApiParam(
                                        name = "charID",
                                        required = true,
                                        value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<ISkillInQueue> skillQueue = charAPI.requestSkillQueue();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, skillQueue);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }

  }

  @Path("/Skills")
  @GET
  @ApiOperation(
      value = "Retrieve free skill points and skill information",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_skills/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Free skill points and skill information",
              response = ISkillInfo.class),
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
  public Response requestSkills(
                                @Context HttpServletRequest req,
                                @Context UriInfo info,
                                @QueryParam("keyID") @ApiParam(
                                    name = "keyID",
                                    required = true,
                                    value = "Access key ID") int keyID,
                                @QueryParam("vCode") @ApiParam(
                                    name = "vCode",
                                    required = true,
                                    value = "Access vCode") String vCode,
                                @QueryParam("charID") @ApiParam(
                                    name = "charID",
                                    required = true,
                                    value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      ISkillInfo skills = charAPI.requestSkills();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, skills);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/Standings")
  @GET
  @ApiOperation(
      value = "Retrieve character standings",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_standings/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Character standings with other entities",
              response = IStandingSet.class),
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
  public Response requestStandings(
                                   @Context HttpServletRequest req,
                                   @Context UriInfo info,
                                   @QueryParam("keyID") @ApiParam(
                                       name = "keyID",
                                       required = true,
                                       value = "Access key ID") int keyID,
                                   @QueryParam("vCode") @ApiParam(
                                       name = "vCode",
                                       required = true,
                                       value = "Access vCode") String vCode,
                                   @QueryParam("charID") @ApiParam(
                                       name = "charID",
                                       required = true,
                                       value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      IStandingSet standings = charAPI.requestStandings();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, standings);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/UpcomingCalendarEvents")
  @GET
  @ApiOperation(
      value = "Retrieve character upcoming calendar events",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_upcomingcalendarevents/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Upcoming calendar events for character",
              response = IUpcomingCalendarEvent.class,
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
  public Response requestUpcomingCalendarEvents(
                                                @Context HttpServletRequest req,
                                                @Context UriInfo info,
                                                @QueryParam("keyID") @ApiParam(
                                                    name = "keyID",
                                                    required = true,
                                                    value = "Access key ID") int keyID,
                                                @QueryParam("vCode") @ApiParam(
                                                    name = "vCode",
                                                    required = true,
                                                    value = "Access vCode") String vCode,
                                                @QueryParam("charID") @ApiParam(
                                                    name = "charID",
                                                    required = true,
                                                    value = "Character ID") long characterID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IUpcomingCalendarEvent> events = charAPI.requestUpcomingCalendarEvents();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, events);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/WalletJournal")
  @GET
  @ApiOperation(
      value = "Retrieve character wallet journal",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_walletjournal/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Wallet journal",
              response = IWalletJournalEntry.class,
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
  public Response requestWalletJournalEntries(
                                              @Context HttpServletRequest req,
                                              @Context UriInfo info,
                                              @QueryParam("keyID") @ApiParam(
                                                  name = "keyID",
                                                  required = true,
                                                  value = "Access key ID") int keyID,
                                              @QueryParam("vCode") @ApiParam(
                                                  name = "vCode",
                                                  required = true,
                                                  value = "Access vCode") String vCode,
                                              @QueryParam("charID") @ApiParam(
                                                  name = "charID",
                                                  required = true,
                                                  value = "Character ID") long characterID,
                                              @DefaultValue("-1") @QueryParam("beforeRefID") @ApiParam(
                                                  name = "beforeRefID",
                                                  required = false,
                                                  value = "If present, the upper bound for the ref ID of returned journal entries (see third party docs)") long beforeRefID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IWalletJournalEntry> entries = beforeRefID != -1 ? charAPI.requestWalletJournalEntries(beforeRefID) : charAPI.requestWalletJournalEntries();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, entries);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }

  @Path("/WalletTransactions")
  @GET
  @ApiOperation(
      value = "Retrieve character wallet transactions",
      notes = "http://eveonline-third-party-documentation.readthedocs.org/en/latest/xmlapi/char_wallettransactions/")
  @ApiResponses(
      value = {
          @ApiResponse(
              code = 200,
              message = "Wallet transactions",
              response = IWalletTransaction.class,
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
  public Response requestWalletTransactions(
                                            @Context HttpServletRequest req,
                                            @Context UriInfo info,
                                            @QueryParam("keyID") @ApiParam(
                                                name = "keyID",
                                                required = true,
                                                value = "Access key ID") int keyID,
                                            @QueryParam("vCode") @ApiParam(
                                                name = "vCode",
                                                required = true,
                                                value = "Access vCode") String vCode,
                                            @QueryParam("charID") @ApiParam(
                                                name = "charID",
                                                required = true,
                                                value = "Character ID") long characterID,
                                            @DefaultValue("-1") @QueryParam("beforeTransID") @ApiParam(
                                                name = "beforeTransID",
                                                required = false,
                                                value = "If present, the upper bound for the transaction ID of the returned transactions (see third party docs)") long beforeTransID) {
    IEveXmlApi api = getApi(info);
    try {
      ICharacterAPI charAPI = api.getCharacterAPIService(keyID, vCode, characterID);
      Collection<IWalletTransaction> txns = beforeTransID != -1 ? charAPI.requestWalletTransactions(beforeTransID) : charAPI.requestWalletTransactions();
      if (charAPI.isError()) return makeResponseErrorResponse(charAPI);
      return makeResponse(charAPI, txns);
    } catch (IOException e) {
      return makeServiceErrorResponse(e);
    }
  }
}
