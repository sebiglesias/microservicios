package grpc.wishlist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import grpc.wishlist.services.Service;
import guice.injector.ServiceModule;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Created by sebi on 29/08/17.
 */
public class WishListServer {
    private final Server server;
    private final int port;

    public WishListServer(int port, Service service){
        final ServerBuilder<?> serverBuilder = ServerBuilder.forPort(port);
        this.server = serverBuilder.addService(service).build();
        this.port = port;
    }

    public void start() throws IOException {
        System.out.println("***Starting Server at port: " + this.port+ " ***");
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            WishListServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        Injector injector = Guice.createInjector(new ServiceModule());
        WishListServer server = new WishListServer(8980,injector.getInstance(Service.class));
        server.start();
        server.blockUntilShutdown();
    }
}
