package enterprises.orbital.evexmlapi.proxy;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import enterprises.orbital.base.OrbitalProperties;

public class ProxyApplication extends Application {

  public ProxyApplication() throws IOException {
    // Populate properties
    OrbitalProperties.addPropertyFile("Application.properties");
  }

  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new HashSet<Class<?>>();

    // Local resources
    resources.add(ServerAPI.class);
    resources.add(AccountAPI.class);
    resources.add(ApiAPI.class);
    resources.add(CharacterAPI.class);
    resources.add(CorporationAPI.class);
    resources.add(MapAPI.class);
    resources.add(EveAPI.class);

    // Swagger additions
    resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
    resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

    return resources;
  }

}
