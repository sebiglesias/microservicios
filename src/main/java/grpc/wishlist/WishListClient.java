package grpc.wishlist;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created by sebi on 29/08/17.
 */
public class WishListClient {

    private final WishListGrpc.WishListBlockingStub blockingStub;
    private final WishListGrpc.WishListStub asyncStub;

    public WishListClient(String host, int port){
        this(ManagedChannelBuilder.forAddress(host,port).usePlaintext(true));
    }

    public WishListClient(ManagedChannelBuilder<?> builder) {
        final ManagedChannel channel = builder.build();
        this.blockingStub = WishListGrpc.newBlockingStub(channel);
        this.asyncStub = WishListGrpc.newStub(channel);
    }

    public WishListGrpc.WishListBlockingStub getBlockingStub() {
        return blockingStub;
    }

    public WishListGrpc.WishListStub getAsyncStub() {
        return asyncStub;
    }

    public static void main(String[] args) {
        final WishListClient client = new WishListClient("localhost", 8980);
        final WishListGrpc.WishListBlockingStub blockingStub = client.getBlockingStub();
        final WishListGrpc.WishListStub asyncStub = client.getAsyncStub();

        final Request.Builder requestBuilder = Request.newBuilder();
        final Item.Builder itemBuilder = Item.newBuilder();
        final User.Builder userBuilder = User.newBuilder();

        final User sebastian = userBuilder.setEmail("sebi@hotmail.com").setId(0303).setName("Sebastian").build();
        final Item item = itemBuilder.setName("Articulo").build();
        final Request request = requestBuilder.setUser(sebastian).setItem(item).build();

        final Response response = blockingStub.addItem(request);
        System.out.println(response.getMessage());

        final Response response1 = blockingStub.removeItem(request);
        System.out.println(response1.getMessage());
        //todo getList
//        asyncStub.getList(sebastian).var;
    }

}
