import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class GameServer extends UnicastRemoteObject implements RemoteServerInterface {
    private static final long serialVersionUID = 1L;
    private DatabaseServiceImpl db;
    GameServer() throws RemoteException {
        super();
        this.db = new DatabaseServiceImpl();
    }

    public static void main(String[] args) {
        try {
            GameServer server = new GameServer();
            Naming.rebind("GameServer", server);
            System.out.println("Game server is running...");
        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUserData(String name) throws RemoteException {
        return this.db.getUserData(name);
    }

    @Override
    public ArrayList<User> getAllUserData() throws RemoteException {
        return this.db.getAllUserData();
    }

    @Override
    public boolean  authenticateUser(String username, String password) throws RemoteException {
        return this.db.authenticateUser(username, password);
    }

    @Override
    public boolean registerAndLoginUser(String username, int games, int wins, double avg, String password) {
        return this.db.registerAndLoginUser(username, password, games, wins, avg);
    }

    @Override
    public void logoutUser(String name) throws RemoteException {
        if (name == null) {
            // If name is null, do nothing
            return;
        }
        // Otherwise, perform the logout operation
        this.db.logoutUser(name);
    }
}
