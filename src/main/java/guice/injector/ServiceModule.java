package guice.injector;

import com.google.inject.AbstractModule;
import grpc.wishlist.services.Service;
import grpc.wishlist.services.WishListService;

/**
 * Created by sebi on 31/08/17.
 */
public class ServiceModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(Service.class).to(WishListService.class);
    }
}
