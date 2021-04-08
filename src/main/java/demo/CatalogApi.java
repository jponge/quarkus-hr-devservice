package demo;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/products")
public class CatalogApi {

  private static final Logger logger = LoggerFactory.getLogger(CatalogApi.class);

  @GET
  public Multi<Product> list() {
    logger.info("list");
    return Product.all();
  }

  @POST
  @Transactional
  public Uni<Product> register(Product product) {
    logger.info("register");
    return product.persistAndFlush().replaceWith(product);
  }

  @GET
  @Path("/{id}")
  public Uni<Product> byId(@PathParam("id") Long id) {
    logger.info("byId");
    return Product.findById(id);
  }

  @GET
  @Path("/named/{name}")
  public Uni<Product> find(@PathParam("name") String name) {
    logger.info("find");
    return Product.findByName(name);
  }
}
