package yugioh.server.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class UserHolder {
    User user;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DataOutputStream getDataOutputStream() {
        return dataOutputStream;
    }

    public void setDataOutputStream(DataOutputStream dataOutputStream) {
        this.dataOutputStream = dataOutputStream;
    }

    public DataInputStream getDataInputStream() {
        return dataInputStream;
    }

    public void setDataInputStream(DataInputStream dataInputStream) {
        this.dataInputStream = dataInputStream;
    }
}
