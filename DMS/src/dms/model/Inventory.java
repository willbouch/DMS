/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.io.Serializable;
import java.util.*;

// line 52 "../../DMS_Persistence.ump"
// line 129 "../../DMS_Model.ump"
public class Inventory implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Inventory Attributes
  private char firstLetter;

  //Inventory Associations
  private List<Drug> drugs;
  private DMS dMS;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Inventory(char aFirstLetter, DMS aDMS)
  {
    // line 135 "../../DMS_Model.ump"
    if(aFirstLetter < 'A' || aFirstLetter > 'Z') {
    			throw new RuntimeException("La première lettre de l'inventaire doit être entre A et Z.");			
    		}
    // END OF UMPLE BEFORE INJECTION
    firstLetter = aFirstLetter;
    drugs = new ArrayList<Drug>();
    boolean didAddDMS = setDMS(aDMS);
    if (!didAddDMS)
    {
      throw new RuntimeException("Unable to create inventory due to dMS");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public char getFirstLetter()
  {
    return firstLetter;
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
  /* Code from template association_AddManyToOne */
  public Drug addDrug(String aName, double aPrice, double aConcentration, String aUnit, int aInHandQuantity, int aMinQuantity, String aCode, boolean aToOrder)
  {
    return new Drug(aName, aPrice, aConcentration, aUnit, aInHandQuantity, aMinQuantity, aCode, aToOrder, this);
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
      existingDMS.removeInventory(this);
    }
    dMS.addInventory(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (drugs.size() > 0)
    {
      Drug aDrug = drugs.get(drugs.size() - 1);
      aDrug.delete();
      drugs.remove(aDrug);
    }
    
    DMS existingDMS = dMS;
    dMS = null;
    if (existingDMS != null)
    {
      existingDMS.delete();
    }
  }

  // line 58 "../../DMS_Persistence.ump"
   public static  void reinitializeInventoryDrugs(List<Inventory> inventories){
    List<Drug> allDrugs = new ArrayList<Drug>();
  		for(Inventory inventory : inventories) {
  			allDrugs.addAll(inventory.getDrugs());
  		}
  		Drug.reinitializeUniqueDrugCode(allDrugs);
  }

  // line 141 "../../DMS_Model.ump"
   public Drug findDrug(String code){
    List<Drug> drugs = this.getDrugs();
		for(Drug drug : drugs) {
			if(drug.getCode().equals(code)) {
				return drug;
			}
		}
		
		return null;
  }


  public String toString()
  {
    return super.toString() + "["+
            "firstLetter" + ":" + getFirstLetter()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dMS = "+(getDMS()!=null?Integer.toHexString(System.identityHashCode(getDMS())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 55 "../../DMS_Persistence.ump"
  private static final long serialVersionUID = 5332292624658907512L ;

  
}