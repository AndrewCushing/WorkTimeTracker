package FrontEndInterface;

import com.sun.net.httpserver.BasicAuthenticator;

public class Authenticator extends BasicAuthenticator {

    /**
     * Creates a BasicAuthenticator for the given HTTP realm
     *
     * @param realm The HTTP Basic authentication realm
     * @throws NullPointerException if the realm is an empty string
     */
    public Authenticator(String realm) {
        super(realm);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        System.out.println(username);
        System.out.println(password);
        return true;
    }

}
