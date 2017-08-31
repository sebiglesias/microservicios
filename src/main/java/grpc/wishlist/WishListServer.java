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
    private final int port;
    private final Server server;

    public WishListServer(int port, Service service){
        this.server = ServerBuilder.forPort(port).addService(service).build();
        this.port = port;
    }

    public void start() throws IOException {
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
