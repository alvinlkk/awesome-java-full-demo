package com.alvin.kryo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoReferenceDemo {

    public static void main(String[] args) throws FileNotFoundException {
        Kryo kryo = new Kryo();
        kryo.register(User.class);
        kryo.register(Account.class);

        User user = new User();
        user.setUsername("alvin");

        Account account = new Account();
        account.setAccountNo("10000");

        // 循环引用
        user.setAccount(account);
        account.setUser(user);

        // 这里需要设置为true，才不会报错
        kryo.setReferences(true);

        Output output = new Output(new FileOutputStream("kryoreference.bin"));
        kryo.writeObject(output, user);
        output.close();

        Input input = new Input(new FileInputStream("kryoreference.bin"));
        User object2 = kryo.readObject(input, User.class);
        input.close();
        System.out.println(object2.getUsername());
        System.out.println(object2.getAccount().getAccountNo());
    }

    public static class User {
        private String username;

        private Account account;


        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }
    }

    public static class Account {

        private String accountNo;

        private String amount;

        private User user;

        public String getAccountNo() {
            return accountNo;
        }

        public void setAccountNo(String accountNo) {
            this.accountNo = accountNo;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }
}
