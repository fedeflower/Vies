package vies.uniba.it.vies.model;

/**
 * Created by Marco on 4/3/2016.
 */
public class Login {
    private static String username="Not Logged In";
    private static Login myObj=null;

    private Login(){

    }

    public String getUsername(){
        return username;
    }

    public static Login getInstance(){
        if(myObj==null){
            myObj=new Login();
        }

        return myObj;
    }

    public void setUsername(String user){
        username=user;
    }
}