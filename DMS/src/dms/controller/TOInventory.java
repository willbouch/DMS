/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.controller;
import java.util.*;

// line 3 "../../DMS_TObjects.ump"
public class TOInventory
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOInventory Associations
  private List<TODrug> tODrugs;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOInventory()
  {
    tODrugs = new ArrayList<TODrug>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public TODrug getTODrug(int index)
  {
    TODrug aTODrug = tODrugs.get(index);
    return aTODrug;
  }

  public List<TODrug> getTODrugs()
  {
    List<TODrug> newTODrugs = Collections.unmodifiableList(tODrugs);
    return newTODrugs;
  }

  public int numberOfTODrugs()
  {
    int number = tODrugs.size();
    return number;
  }

  public boolean hasTODrugs()
  {
    boolean has = tODrugs.size() > 0;
    return has;
  }

  public int indexOfTODrug(TODrug aTODrug)
  {
    int index = tODrugs.indexOf(aTODrug);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTODrugs()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TODrug addTODrug(String aName, double aPrice, double aConcentration, String aUnit, int aInHandQuantity, int aOrderedQuantity, int aMinQuantity, int aId)
  {
    return new TODrug(aName, aPrice, aConcentration, aUnit, aInHandQuantity, aOrderedQuantity, aMinQuantity, aId, this);
  }

  public boolean addTODrug(TODrug aTODrug)
  {
    boolean wasAdded = false;
    if (tODrugs.contains(aTODrug)) { return false; }
    TOInventory existingTOInventory = aTODrug.getTOInventory();
    boolean isNewTOInventory = existingTOInventory != null && !this.equals(existingTOInventory);
    if (isNewTOInventory)
    {
      aTODrug.setTOInventory(this);
    }
    else
    {
      tODrugs.add(aTODrug);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTODrug(TODrug aTODrug)
  {
    boolean wasRemoved = false;
    //Unable to remove aTODrug, as it must always have a tOInventory
    if (!this.equals(aTODrug.getTOInventory()))
    {
      tODrugs.remove(aTODrug);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTODrugAt(TODrug aTODrug, int index)
  {  
    boolean wasAdded = false;
    if(addTODrug(aTODrug))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTODrugs()) { index = numberOfTODrugs() - 1; }
      tODrugs.remove(aTODrug);
      tODrugs.add(index, aTODrug);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTODrugAt(TODrug aTODrug, int index)
  {
    boolean wasAdded = false;
    if(tODrugs.contains(aTODrug))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTODrugs()) { index = numberOfTODrugs() - 1; }
      tODrugs.remove(aTODrug);
      tODrugs.add(index, aTODrug);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTODrugAt(aTODrug, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tODrugs.size(); i > 0; i--)
    {
      TODrug aTODrug = tODrugs.get(i - 1);
      aTODrug.delete();
    }
  }

}