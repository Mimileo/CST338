package edu.csumb.leon7534.airline;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UserItem {
    private UUID userLogId;
    Date logDate;
    String username;
    String password;
    String transaction_type;

    private int sqlLogId;

    SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy @ HH:mm:ss");


    public UserItem() {
        logDate = new Date();
        userLogId = UUID.randomUUID();
    }

    public UserItem(UUID userLogId){
        this.userLogId = userLogId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }

    public UUID getUserLogId() {
        return userLogId;
    }

    public SimpleDateFormat getSf() {
        return sf;
    }

    public int getSqlLogId() {
        return sqlLogId;
    }

    public void setSqlLogId(int sqlLogId) {
        this.sqlLogId = sqlLogId;
    }


    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        sb.append("\n\n-=-=-=-=-=-=-\n");
        sb.append("Username: " + username).append("\n Password: ").append(password).append("\nTranscation: ").append(transaction_type);
        sb.append("\nDate: " + sf.format(logDate));

        return sb.toString();
    }

    public boolean equals(UserItem other){
        return this.username.equals(other.getUsername()) && this.password.equals(other.getPassword());
    }
}
