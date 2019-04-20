/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.util.*;

// line 58 "../../DMS_Model.ump"
public class Receipt
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Receipt Attributes
  private double totalPrice;

  //Receipt Associations
  private List<Drug> drugs;
  private DMS dMS;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Receipt(double aTotalPrice, DMS aDMS)
  {
    totalPrice = aTotalPrice;
    drugs = new ArrayList<Drug>();
    boolean didAddDMS = setDMS(aDMS);
    if (!didAddDMS)
    {
      throw new RuntimeException("Unable to create receipt due to dMS");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTotalPrice(double aTotalPrice)
  {
    boolean wasSet = false;
    totalPrice = aTotalPrice;
    wasSet = true;
    return wasSet;
  }

  public double getTotalPrice()
  {
    return totalPrice;
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
      existingDMS.removeReceipt(this);
    }
    dMS.addReceipt(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
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
    return super.toString() + "["+
            "totalPrice" + ":" + getTotalPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dMS = "+(getDMS()!=null?Integer.toHexString(System.identityHashCode(getDMS())):"null");
  }
}