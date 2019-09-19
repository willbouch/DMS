/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.controller;

// line 3 "../../DMS_TObjects.ump"
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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TODrug(String aName, double aPrice, double aConcentration, String aUnit, int aInHandQuantity, int aOrderedQuantity, int aMinQuantity, String aCode)
  {
    name = aName;
    price = aPrice;
    concentration = aConcentration;
    unit = aUnit;
    inHandQuantity = aInHandQuantity;
    orderedQuantity = aOrderedQuantity;
    minQuantity = aMinQuantity;
    code = aCode;
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

  public void delete()
  {}


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
            "code" + ":" + getCode()+ "]";
  }
}