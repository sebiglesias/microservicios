package grpc.wishlist.transactions;

import grpc.wishlist.*;
import grpc.wishlist.transactions.*;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class AddTransaction implements Transaction {

    private Date time;
    private User user;
    private Item item;

    public AddTransaction(User user, Item item){
        time = new Date();
        this.user = user;
        this.item = item;
    }

    @Override
    public TransactionType getType() {
        return TransactionType.ADD;
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