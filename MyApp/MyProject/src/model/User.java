

public class User {

    protected String name;
    protected String email;
    protected long phoneNumber;

    public User () {
        
    }

    public User (String name){
        this.name = name;

    }

    public User (String name, String email, long phoneNumber){
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setPhoneNumber(long phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }
    public long getPhoneNumber(){
        return this.phoneNumber;
    }
}
