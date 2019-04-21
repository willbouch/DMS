/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.io.Serializable;

// line 45 "../../DMS_Persistence.ump"
// line 72 "../../DMS_Model.ump"
public class Administrator extends UserRole implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Administrator(String aPassword, DMS aDMS)
  {
    super(aPassword, aDMS);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public void delete()
  {
    super.delete();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 48 "../../DMS_Persistence.ump"
  private static final long serialVersionUID = -210105651472293481L ;

  
}