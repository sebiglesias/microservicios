package grpc.wishlist.transactions;

import java.util.*;

public interface Transaction {

    public TransactionType getType();
    public Date getTimeStamp();
    public byte[] getInfo();

}