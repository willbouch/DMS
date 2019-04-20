/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.util.*;

// line 63 "../../DMS_Model.ump"
public class Inventory
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Inventory Attributes
  private char firstLetter;
  private Comparator<Drug> drugsPriority;

  //Inventory Associations
  private DMS dMS;
  private List<Drug> drugs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Inventory(char aFirstLetter, DMS aDMS)
  {
    firstLetter = aFirstLetter;
    drugsPriority = 
      new Comparator<Drug>(){
        @Override
        public int compare(Drug arg0, Drug arg1)
        {
          return ((String)arg0.getName()).compareTo(
                 ((String)arg1.getName()));
        }
      };
    boolean didAddDMS = setDMS(aDMS);
    if (!didAddDMS)
    {
      throw new RuntimeException("Unable to create inventory due to dMS");
    }
    drugs = new ArrayList<Drug>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setFirstLetter(char aFirstLetter)
  {
    boolean wasSet = false;
    firstLetter = aFirstLetter;
    wasSet = true;
    return wasSet;
  }

  public boolean setDrugsPriority(Comparator<Drug> aDrugsPriority)
  {
    boolean wasSet = false;
    drugsPriority = aDrugsPriority;
    wasSet = true;
    return wasSet;
  }

  public char getFirstLetter()
  {
    return firstLetter;
  }

  public Comparator<Drug> getDrugsPriority()
  {
    return drugsPriority;
  }
  /* Code from template association_GetOne */
  public DMS getDMS()
  {
    return dMS;
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
      existingDMS.removeInventory(this);
    }
    dMS.addInventory(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDrugs()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Drug addDrug(String aName, double aPrice, double aConcentration, String aUnit, int aInHandQuantity, int aMinQuantity)
  {
    return new Drug(aName, aPrice, aConcentration, aUnit, aInHandQuantity, aMinQuantity, this);
  }

  public boolean addDrug(Drug aDrug)
  {
    boolean wasAdded = false;
    if (drugs.contains(aDrug)) { return false; }
    Inventory existingInventory = aDrug.getInventory();
    boolean isNewInventory = existingInventory != null && !this.equals(existingInventory);
    if (isNewInventory)
    {
      aDrug.setInventory(this);
    }
    else
    {
      drugs.add(aDrug);
    }
    wasAdded = true;
    if(wasAdded)
        Collections.sort(drugs, drugsPriority);
    
    return wasAdded;
  }

  public boolean removeDrug(Drug aDrug)
  {
    boolean wasRemoved = false;
    //Unable to remove aDrug, as it must always have a inventory
    if (!this.equals(aDrug.getInventory()))
    {
      drugs.remove(aDrug);
      wasRemoved = true;
    }
    return wasRemoved;
  }


  public void delete()
  {
    DMS existingDMS = dMS;
    dMS = null;
    if (existingDMS != null)
    {
      existingDMS.delete();
    }
    while (drugs.size() > 0)
    {
      Drug aDrug = drugs.get(drugs.size() - 1);
      aDrug.delete();
      drugs.remove(aDrug);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "firstLetter" + ":" + getFirstLetter()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "drugsPriority" + "=" + (getDrugsPriority() != null ? !getDrugsPriority().equals(this)  ? getDrugsPriority().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dMS = "+(getDMS()!=null?Integer.toHexString(System.identityHashCode(getDMS())):"null");
  }
}