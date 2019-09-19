/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.controller;
import java.util.*;

// line 14 "../../DMS_TObjects.ump"
public class TOReceipt
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOReceipt Attributes
  private double totalPrice;

  //TOReceipt Associations
  private List<TOScannedItem> tOScannedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOReceipt(double aTotalPrice)
  {
    totalPrice = aTotalPrice;
    tOScannedItems = new ArrayList<TOScannedItem>();
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
  public TOScannedItem getTOScannedItem(int index)
  {
    TOScannedItem aTOScannedItem = tOScannedItems.get(index);
    return aTOScannedItem;
  }

  public List<TOScannedItem> getTOScannedItems()
  {
    List<TOScannedItem> newTOScannedItems = Collections.unmodifiableList(tOScannedItems);
    return newTOScannedItems;
  }

  public int numberOfTOScannedItems()
  {
    int number = tOScannedItems.size();
    return number;
  }

  public boolean hasTOScannedItems()
  {
    boolean has = tOScannedItems.size() > 0;
    return has;
  }

  public int indexOfTOScannedItem(TOScannedItem aTOScannedItem)
  {
    int index = tOScannedItems.indexOf(aTOScannedItem);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTOScannedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TOScannedItem addTOScannedItem(String aName, String aCode, double aPrice)
  {
    return new TOScannedItem(aName, aCode, aPrice, this);
  }

  public boolean addTOScannedItem(TOScannedItem aTOScannedItem)
  {
    boolean wasAdded = false;
    if (tOScannedItems.contains(aTOScannedItem)) { return false; }
    TOReceipt existingTOReceipt = aTOScannedItem.getTOReceipt();
    boolean isNewTOReceipt = existingTOReceipt != null && !this.equals(existingTOReceipt);
    if (isNewTOReceipt)
    {
      aTOScannedItem.setTOReceipt(this);
    }
    else
    {
      tOScannedItems.add(aTOScannedItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTOScannedItem(TOScannedItem aTOScannedItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aTOScannedItem, as it must always have a tOReceipt
    if (!this.equals(aTOScannedItem.getTOReceipt()))
    {
      tOScannedItems.remove(aTOScannedItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTOScannedItemAt(TOScannedItem aTOScannedItem, int index)
  {  
    boolean wasAdded = false;
    if(addTOScannedItem(aTOScannedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOScannedItems()) { index = numberOfTOScannedItems() - 1; }
      tOScannedItems.remove(aTOScannedItem);
      tOScannedItems.add(index, aTOScannedItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTOScannedItemAt(TOScannedItem aTOScannedItem, int index)
  {
    boolean wasAdded = false;
    if(tOScannedItems.contains(aTOScannedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOScannedItems()) { index = numberOfTOScannedItems() - 1; }
      tOScannedItems.remove(aTOScannedItem);
      tOScannedItems.add(index, aTOScannedItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTOScannedItemAt(aTOScannedItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tOScannedItems.size(); i > 0; i--)
    {
      TOScannedItem aTOScannedItem = tOScannedItems.get(i - 1);
      aTOScannedItem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "totalPrice" + ":" + getTotalPrice()+ "]";
  }
}