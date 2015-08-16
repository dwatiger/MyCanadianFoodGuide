package dwau.mycanadianfoodguide;

/**
 * Created by dwau on 15-07-31.
 */
public class Profile { // Database class

    private int id;
    private String profileName;
    private String sex;
    private String age;

    public Profile(){}

    public Profile(String profileName, String sex, String age){
        super();
        this.profileName = profileName;
        this.sex = sex;
        this.age = age;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Profile [id=" + id + ", Profile Name=" + profileName  + ", Sex=" + sex + ", Age=" +
                 age + "]";
    }
}
