/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.controller;

// line 7 "../../DMS_TObjects.ump"
public class TODrug
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TODrug Attributes
  private String name;
  private double price;
  private double concentration;
  private String unit;
  private int inHandQuantity;
  private int orderedQuantity;
  private int minQuantity;
  private String code;

  //TODrug Associations
  private TOInventory tOInventory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TODrug(String aName, double aPrice, double aConcentration, String aUnit, int aInHandQuantity, int aOrderedQuantity, int aMinQuantity, String aCode, TOInventory aTOInventory)
  {
    name = aName;
    price = aPrice;
    concentration = aConcentration;
    unit = aUnit;
    inHandQuantity = aInHandQuantity;
    orderedQuantity = aOrderedQuantity;
    minQuantity = aMinQuantity;
    code = aCode;
    boolean didAddTOInventory = setTOInventory(aTOInventory);
    if (!didAddTOInventory)
    {
      throw new RuntimeException("Unable to create tODrug due to tOInventory");
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

  public boolean setPrice(double aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setConcentration(double aConcentration)
  {
    boolean wasSet = false;
    concentration = aConcentration;
    wasSet = true;
    return wasSet;
  }

  public boolean setUnit(String aUnit)
  {
    boolean wasSet = false;
    unit = aUnit;
    wasSet = true;
    return wasSet;
  }

  public boolean setInHandQuantity(int aInHandQuantity)
  {
    boolean wasSet = false;
    inHandQuantity = aInHandQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setOrderedQuantity(int aOrderedQuantity)
  {
    boolean wasSet = false;
    orderedQuantity = aOrderedQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinQuantity(int aMinQuantity)
  {
    boolean wasSet = false;
    minQuantity = aMinQuantity;
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

  public String getName()
  {
    return name;
  }

  public double getPrice()
  {
    return price;
  }

  public double getConcentration()
  {
    return concentration;
  }

  public String getUnit()
  {
    return unit;
  }

  public int getInHandQuantity()
  {
    return inHandQuantity;
  }

  public int getOrderedQuantity()
  {
    return orderedQuantity;
  }

  public int getMinQuantity()
  {
    return minQuantity;
  }

  public String getCode()
  {
    return code;
  }
  /* Code from template association_GetOne */
  public TOInventory getTOInventory()
  {
    return tOInventory;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTOInventory(TOInventory aTOInventory)
  {
    boolean wasSet = false;
    if (aTOInventory == null)
    {
      return wasSet;
    }

    TOInventory existingTOInventory = tOInventory;
    tOInventory = aTOInventory;
    if (existingTOInventory != null && !existingTOInventory.equals(aTOInventory))
    {
      existingTOInventory.removeTODrug(this);
    }
    tOInventory.addTODrug(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TOInventory placeholderTOInventory = tOInventory;
    this.tOInventory = null;
    if(placeholderTOInventory != null)
    {
      placeholderTOInventory.removeTODrug(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "price" + ":" + getPrice()+ "," +
            "concentration" + ":" + getConcentration()+ "," +
            "unit" + ":" + getUnit()+ "," +
            "inHandQuantity" + ":" + getInHandQuantity()+ "," +
            "orderedQuantity" + ":" + getOrderedQuantity()+ "," +
            "minQuantity" + ":" + getMinQuantity()+ "," +
            "code" + ":" + getCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "tOInventory = "+(getTOInventory()!=null?Integer.toHexString(System.identityHashCode(getTOInventory())):"null");
  }
}