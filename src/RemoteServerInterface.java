import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface RemoteServerInterface extends Remote {
    public User getUserData(String name) throws RemoteException;

    public ArrayList<User> getAllUserData() throws RemoteException;

    public boolean authenticateUser(String username, String password) throws RemoteException;

    public boolean registerAndLoginUser(String username, int games, int wins, double avg, String password) throws RemoteException;

    public void logoutUser(String name) throws RemoteException;
}
