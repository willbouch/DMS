/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.io.Serializable;

// line 27 "../../DMS_Persistence.ump"
// line 19 "../../DMS_Model.ump"
public abstract class UserRole implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Attributes
  private String password;

  //UserRole Associations
  private DMS dMS;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(String aPassword, DMS aDMS)
  {
    password = aPassword;
    boolean didAddDMS = setDMS(aDMS);
    if (!didAddDMS)
    {
      throw new RuntimeException("Unable to create userRole due to dMS");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public DMS getDMS()
  {
    return dMS;
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
      existingDMS.removeUserRole(this);
    }
    dMS.addUserRole(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    DMS existingDMS = dMS;
    dMS = null;
    if (existingDMS != null)
    {
      existingDMS.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dMS = "+(getDMS()!=null?Integer.toHexString(System.identityHashCode(getDMS())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 30 "../../DMS_Persistence.ump"
  private static final long serialVersionUID = 3389752283403781197L ;

  
}