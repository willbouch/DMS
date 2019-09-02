/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.io.Serializable;
import java.util.*;

// line 14 "../../DMS_Persistence.ump"
// line 23 "../../DMS_Model.ump"
public class User implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByUsername = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;

  //User Associations
  private UserRole userRole;
  private DMS dMS;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, UserRole aUserRole, DMS aDMS)
  {
    // line 29 "../../DMS_Model.ump"
    if(aUsername == null || aUsername.equals("")) {
         		throw new RuntimeException("Le nom d'utilisateur ne peut �tre vide.");
       		}
    // END OF UMPLE BEFORE INJECTION
    // line 35 "../../DMS_Model.ump"
    if(aUsername.length() < 8) {
         		throw new RuntimeException("Le nom d'utilisateur doit comprendre au moins 8 charact�res.");
       		}
    // END OF UMPLE BEFORE INJECTION
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username");
    }
    if (!setUserRole(aUserRole))
    {
      throw new RuntimeException("Unable to create User due to aUserRole");
    }
    boolean didAddDMS = setDMS(aDMS);
    if (!didAddDMS)
    {
      throw new RuntimeException("Unable to create user due to dMS");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    // line 35 "../../DMS_Model.ump"
    if(aUsername.length() < 8) {
         		throw new RuntimeException("Le nom d'utilisateur doit comprendre au moins 8 charact�res.");
       		}
    // END OF UMPLE BEFORE INJECTION
    String anOldUsername = getUsername();
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      usersByUsername.remove(anOldUsername);
    }
    usersByUsername.put(aUsername, this);
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithUsername(String aUsername)
  {
    return usersByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }
  /* Code from template association_GetOne */
  public UserRole getUserRole()
  {
    return userRole;
  }
  /* Code from template association_GetOne */
  public DMS getDMS()
  {
    return dMS;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setUserRole(UserRole aNewUserRole)
  {
    boolean wasSet = false;
    if (aNewUserRole != null)
    {
      userRole = aNewUserRole;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setDMS(DMS aDMS)
  {
    boolean wasSet = false;
    if (aDMS == null)
    {
      return wasSet;
    }

    DMS existingDMS = dMS;
    dMS = aDMS;
    if (existingDMS != null && !existingDMS.equals(aDMS))
    {
      existingDMS.removeUser(this);
    }
    dMS.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    usersByUsername.remove(getUsername());
    userRole = null;
    DMS existingDMS = dMS;
    dMS = null;
    if (existingDMS != null)
    {
      existingDMS.delete();
    }
  }

  // line 20 "../../DMS_Persistence.ump"
   public static  void reinitializeUniqueUserName(List<User> users){
    usersByUsername = new HashMap<String, User>();
  		for (User user : users) {
  			usersByUsername.put(user.getUsername(), user);
  		}
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "userRole = "+(getUserRole()!=null?Integer.toHexString(System.identityHashCode(getUserRole())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "dMS = "+(getDMS()!=null?Integer.toHexString(System.identityHashCode(getDMS())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 17 "../../DMS_Persistence.ump"
  private static final long serialVersionUID = 4267485601061759914L ;

  
}