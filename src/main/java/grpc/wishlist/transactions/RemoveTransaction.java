package grpc.wishlist.transactions;


import grpc.wishlist.*;

import java.util.*;

public class RemoveTransaction implements Transaction {

    private Date time;
    private User user;
    private Item item;

    public RemoveTransaction(User user, Item item){
        time = new Date();
        this.user = user;
        this.item = item;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.REMOVE;
    }

    @Override
    public Date getTimeStamp() {
        return time;
    }

    @Override
    public byte[] getInfo() {
        return new byte[0];
    }
}