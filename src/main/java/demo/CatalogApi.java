package demo;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/products")
public class CatalogApi {

  @GET
  public Multi<Product> list() {
    return Product.all();
  }

  @POST
  @Transactional
  public Uni<Product> register(Product product) {
    return product.persistAndFlush().replaceWith(product);
  }

  @GET
  @Path("/{id}")
  public Uni<Product> byId(@PathParam("id") Long id) {
    return Product.findById(id);
  }

  @GET
  @Path("/named/{name}")
  public Uni<Product> find(@PathParam("name") String name) {
    return Product.findByName(name);
  }
}
