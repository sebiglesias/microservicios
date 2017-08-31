package grpc.wishlist.services;

import grpc.wishlist.Article;
import grpc.wishlist.Request;
import grpc.wishlist.Response;
import grpc.wishlist.User;
import io.grpc.ServerServiceDefinition;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by sebi on 31/08/17.
 */
public class WishListService implements Service {

    private Map<User,Article> wishlists;

    public WishListService(){
        wishlists = new TreeMap<>();
    }

    @Override
    public ServerServiceDefinition bindService() {
        return ServerServiceDefinition.builder("WishListService").build();
    }

    /**
     * Add an article to a user's wishlist
     * Simple RPC
     * @param request
     * @param response
     */
    public void addArticle(Request request, StreamObserver<Response> response){
        response.onNext(addArticle(request.getUser(),request.getArticle()));
        response.onCompleted();
    }

    //todo Make a request to a real db
    private Response addArticle(User user, Article article) {
//        return new Response("Article "+article.getName()+" Added");
        return Response.getDefaultInstance();
    }

    /**
     * Remove an article from a user's wishlist
     * Simple RPC
     * @param request
     * @param response
     */
    public void removeArticle(Request request, StreamObserver<Response> response){
        response.onNext(removeArticle(request.getUser(),request.getArticle()));
        response.onCompleted();
    }

    //todo make a request to a real db
    private Response removeArticle(User user, Article article) {
//        return new Response("Article "+article.getName()+" deleted");
        return Response.getDefaultInstance();
    }

    /**
     * Returns a user's wishlist
     * @param user
     * @param article
     */
    public void getWishList(User user, StreamObserver<Article> article){
        final Iterator<Article> wishList = getWishList(user);
        if (wishList != null && wishList.hasNext()){
            wishList.forEachRemaining(article::onNext);
            article.onCompleted();
        }
    }

    //todo make a request to a real db
    private Iterator<Article> getWishList(User user) {
        return null;
    }

}
