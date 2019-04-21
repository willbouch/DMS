/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.io.Serializable;
import java.sql.Date;
import java.util.*;

// line 69 "../../DMS_Persistence.ump"
// line 66 "../../DMS_Model.ump"
public class Order implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Order Attributes
  private Date date;

  //Order Associations
  private Pharmacist pharmacist;
  private Administrator administrator;
  private List<Drug> drugs;
  private DMS dMS;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Order(Date aDate, Pharmacist aPharmacist, Administrator aAdministrator, DMS aDMS)
  {
    date = aDate;
    if (!setPharmacist(aPharmacist))
    {
      throw new RuntimeException("Unable to create Order due to aPharmacist");
    }
    if (!setAdministrator(aAdministrator))
    {
      throw new RuntimeException("Unable to create Order due to aAdministrator");
    }
    drugs = new ArrayList<Drug>();
    boolean didAddDMS = setDMS(aDMS);
    if (!didAddDMS)
    {
      throw new RuntimeException("Unable to create order due to dMS");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public Pharmacist getPharmacist()
  {
    return pharmacist;
  }
  /* Code from template association_GetOne */
  public Administrator getAdministrator()
  {
    return administrator;
  }
  /* Code from template association_GetMany */
  public Drug getDrug(int index)
  {
    Drug aDrug = drugs.get(index);
    return aDrug;
  }

  public List<Drug> getDrugs()
  {
    List<Drug> newDrugs = Collections.unmodifiableList(drugs);
    return newDrugs;
  }

  public int numberOfDrugs()
  {
    int number = drugs.size();
    return number;
  }

  public boolean hasDrugs()
  {
    boolean has = drugs.size() > 0;
    return has;
  }

  public int indexOfDrug(Drug aDrug)
  {
    int index = drugs.indexOf(aDrug);
    return index;
  }
  /* Code from template association_GetOne */
  public DMS getDMS()
  {
    return dMS;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setPharmacist(Pharmacist aNewPharmacist)
  {
    boolean wasSet = false;
    if (aNewPharmacist != null)
    {
      pharmacist = aNewPharmacist;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setAdministrator(Administrator aNewAdministrator)
  {
    boolean wasSet = false;
    if (aNewAdministrator != null)
    {
      administrator = aNewAdministrator;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDrugs()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addDrug(Drug aDrug)
  {
    boolean wasAdded = false;
    if (drugs.contains(aDrug)) { return false; }
    drugs.add(aDrug);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeDrug(Drug aDrug)
  {
    boolean wasRemoved = false;
    if (drugs.contains(aDrug))
    {
      drugs.remove(aDrug);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDrugAt(Drug aDrug, int index)
  {  
    boolean wasAdded = false;
    if(addDrug(aDrug))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDrugs()) { index = numberOfDrugs() - 1; }
      drugs.remove(aDrug);
      drugs.add(index, aDrug);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDrugAt(Drug aDrug, int index)
  {
    boolean wasAdded = false;
    if(drugs.contains(aDrug))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDrugs()) { index = numberOfDrugs() - 1; }
      drugs.remove(aDrug);
      drugs.add(index, aDrug);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDrugAt(aDrug, index);
    }
    return wasAdded;
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
      existingDMS.removeOrder(this);
    }
    dMS.addOrder(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    pharmacist = null;
    administrator = null;
    drugs.clear();
    DMS existingDMS = dMS;
    dMS = null;
    if (existingDMS != null)
    {
      existingDMS.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "pharmacist = "+(getPharmacist()!=null?Integer.toHexString(System.identityHashCode(getPharmacist())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "administrator = "+(getAdministrator()!=null?Integer.toHexString(System.identityHashCode(getAdministrator())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "dMS = "+(getDMS()!=null?Integer.toHexString(System.identityHashCode(getDMS())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 72 "../../DMS_Persistence.ump"
  private static final long serialVersionUID = 8597675110221231714L ;

  
}