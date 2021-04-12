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
  public Multi<Product> listProducts() {
    logger.info("listProducts");
    return Product.all();
  }

  @POST
  @Transactional
  public Uni<Product> createProduct(Product product) {
    logger.info("register");
    return product.persistAndFlush().replaceWith(product);
  }

  @GET
  @Path("/{id}")
  public Uni<Product> getProduct(@PathParam("id") Long id) {
    logger.info("createProduct");
    return Product.findById(id);
  }

  @GET
  @Path("/named/{name}")
  public Uni<Product> getProductByName(@PathParam("name") String name) {
    logger.info("getProductByName");
    return Product.findByName(name);
  }
}
