/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.controller;

// line 19 "../../DMS_TObjects.ump"
public class TOScannedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOScannedItem Attributes
  private String name;
  private String code;
  private double price;

  //TOScannedItem Associations
  private TOReceipt tOReceipt;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOScannedItem(String aName, String aCode, double aPrice, TOReceipt aTOReceipt)
  {
    name = aName;
    code = aCode;
    price = aPrice;
    boolean didAddTOReceipt = setTOReceipt(aTOReceipt);
    if (!didAddTOReceipt)
    {
      throw new RuntimeException("Unable to create tOScannedItem due to tOReceipt");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCode(String aCode)
  {
    boolean wasSet = false;
    code = aCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getCode()
  {
    return code;
  }

  public double getPrice()
  {
    return price;
  }
  /* Code from template association_GetOne */
  public TOReceipt getTOReceipt()
  {
    return tOReceipt;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTOReceipt(TOReceipt aTOReceipt)
  {
    boolean wasSet = false;
    if (aTOReceipt == null)
    {
      return wasSet;
    }

    TOReceipt existingTOReceipt = tOReceipt;
    tOReceipt = aTOReceipt;
    if (existingTOReceipt != null && !existingTOReceipt.equals(aTOReceipt))
    {
      existingTOReceipt.removeTOScannedItem(this);
    }
    tOReceipt.addTOScannedItem(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TOReceipt placeholderTOReceipt = tOReceipt;
    this.tOReceipt = null;
    if(placeholderTOReceipt != null)
    {
      placeholderTOReceipt.removeTOScannedItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "code" + ":" + getCode()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tOReceipt = "+(getTOReceipt()!=null?Integer.toHexString(System.identityHashCode(getTOReceipt())):"null");
  }
}