package grpc.wishlist.services;

import grpc.wishlist.List;
import io.grpc.ServerServiceDefinition;

/**
 * Created by sebi on 31/08/17.
 */
public class WishListService implements Service {

    public WishListService(){

    }

    public WishListService(List list) {

    }

    @Override
    public ServerServiceDefinition bindService() {
        return null;
    }
}
