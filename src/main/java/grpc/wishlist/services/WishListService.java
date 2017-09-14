package grpc.wishlist.services;

import grpc.wishlist.*;
import grpc.wishlist.transactions.*;
import io.grpc.stub.StreamObserver;


import java.util.*;
import java.util.List;

/**
 * Created by sebi on 31/08/17.
 */
public class WishListService extends WishListGrpc.WishListImplBase implements Service {

    private Map<Integer,List<Item>> wishlists;
    private List<Transaction> log;


    public WishListService(){
        wishlists = new TreeMap<>();
        log = new ArrayList<>();
    }

    /**
     * Add an article to a user's wishlist
     * Simple RPC
     * @param request
     * @param response
     */
    public void addItem(Request request, StreamObserver<Response> response){
        response.onNext(addItem(request.getUser(),request.getItem()));
        log.add(new AddTransaction(request.getUser(),request.getItem()));
        response.onCompleted();
    }

    //todo Make a request to a real db
    private Response addItem(User user, Item item) {
        wishlists.computeIfAbsent(user.getId(), k -> new ArrayList<>());
        wishlists.get(user.getId()).add(item);
        final Response.Builder responseBuilder = Response.newBuilder();
        //TODO comunicarle al resto
        return responseBuilder.setMessage("Hello world!").build();
    }

    /**
     * Remove an article from a user's wishlist
     * Simple RPC
     * @param request
     * @param response
     */
    public void removeItem(Request request, StreamObserver<Response> response){
        response.onNext(removeItem(request.getUser(),request.getItem()));
        log.add(new RemoveTransaction(request.getUser(),request.getItem()));
        //TODO informar al resto
        response.onCompleted();
    }

    //todo make a request to a real db
    private Response removeItem(User user, Item item) {
        wishlists.get(user.getId()).remove(item);
        return Response.getDefaultInstance();
    }

    /**
     * Returns a user's wishlist
     * @param user
     * @param item
     */
    public void getWishList(User user, StreamObserver<Item> item){
        final Iterator<Item> wishList = getWishList(user);
        if (wishList.hasNext()){
            wishList.forEachRemaining(item::onNext);
            item.onCompleted();
        }
    }

    private Iterator<Item> getWishList(User user) {
        return wishlists.get(user).iterator();
    }

}
