/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.util.*;

// line 73 "../../DMS_Model.ump"
public class Drug
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Drug> drugsByCode = new HashMap<Integer, Drug>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Drug Attributes
  private String name;
  private double price;
  private double concentration;
  private String unit;
  private int inHandQuantity;
  private int orderedQuantity;
  private int minQuantity;
  private int code;

  //Drug Associations
  private Inventory inventory;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Drug(String aName, double aPrice, double aConcentration, String aUnit, int aInHandQuantity, int aMinQuantity, int aCode, Inventory aInventory)
  {
    // line 85 "../../DMS_Model.ump"
    for(Drug drug : aInventory.getDrugs()) {
    			if(drug.getName().equals(aName) && drug.getConcentration() == aConcentration) {
    				throw new RuntimeException("Le m�dicament existe d�j�.");
    			}
    		}
    // END OF UMPLE BEFORE INJECTION
    // line 93 "../../DMS_Model.ump"
    if(aPrice < 0) {
    			throw new RuntimeException("Le prix ne peut �tre n�gatif.");
    		}
    // END OF UMPLE BEFORE INJECTION
    // line 99 "../../DMS_Model.ump"
    if(aInHandQuantity < 0) {
    			throw new RuntimeException("La quantit� en main ne peut �tre n�gative.");
    		}
    // END OF UMPLE BEFORE INJECTION
    // line 111 "../../DMS_Model.ump"
    if(aMinQuantity < 0) {
    			throw new RuntimeException("La quantit� minimale ne peut �tre n�gative.");
    		}
    // END OF UMPLE BEFORE INJECTION
    // line 117 "../../DMS_Model.ump"
    if(aConcentration < 0) {
    			throw new RuntimeException("La concentration ne peut �tre n�gative.");
    		}
    // END OF UMPLE BEFORE INJECTION
    name = aName;
    price = aPrice;
    concentration = aConcentration;
    unit = aUnit;
    inHandQuantity = aInHandQuantity;
    resetOrderedQuantity();
    minQuantity = aMinQuantity;
    if (!setCode(aCode))
    {
      throw new RuntimeException("Cannot create due to duplicate code");
    }
    boolean didAddInventory = setInventory(aInventory);
    if (!didAddInventory)
    {
      throw new RuntimeException("Unable to create drug due to inventory");
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
    // line 93 "../../DMS_Model.ump"
    if(aPrice < 0) {
    			throw new RuntimeException("Le prix ne peut �tre n�gatif.");
    		}
    // END OF UMPLE BEFORE INJECTION
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setConcentration(double aConcentration)
  {
    boolean wasSet = false;
    // line 117 "../../DMS_Model.ump"
    if(aConcentration < 0) {
    			throw new RuntimeException("La concentration ne peut �tre n�gative.");
    		}
    // END OF UMPLE BEFORE INJECTION
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
    // line 99 "../../DMS_Model.ump"
    if(aInHandQuantity < 0) {
    			throw new RuntimeException("La quantit� en main ne peut �tre n�gative.");
    		}
    // END OF UMPLE BEFORE INJECTION
    inHandQuantity = aInHandQuantity;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setOrderedQuantity(int aOrderedQuantity)
  {
    boolean wasSet = false;
    // line 105 "../../DMS_Model.ump"
    if(aOrderedQuantity < 0) {
    			throw new RuntimeException("La quantit� command�e ne peut �tre n�gative.");
    		}
    // END OF UMPLE BEFORE INJECTION
    orderedQuantity = aOrderedQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean resetOrderedQuantity()
  {
    boolean wasReset = false;
    orderedQuantity = getDefaultOrderedQuantity();
    wasReset = true;
    return wasReset;
  }

  public boolean setMinQuantity(int aMinQuantity)
  {
    boolean wasSet = false;
    // line 111 "../../DMS_Model.ump"
    if(aMinQuantity < 0) {
    			throw new RuntimeException("La quantit� minimale ne peut �tre n�gative.");
    		}
    // END OF UMPLE BEFORE INJECTION
    minQuantity = aMinQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setCode(int aCode)
  {
    boolean wasSet = false;
    Integer anOldCode = getCode();
    if (hasWithCode(aCode)) {
      return wasSet;
    }
    code = aCode;
    wasSet = true;
    if (anOldCode != null) {
      drugsByCode.remove(anOldCode);
    }
    drugsByCode.put(aCode, this);
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
  /* Code from template attribute_GetDefaulted */
  public int getDefaultOrderedQuantity()
  {
    return 0;
  }

  public int getMinQuantity()
  {
    return minQuantity;
  }

  public int getCode()
  {
    return code;
  }
  /* Code from template attribute_GetUnique */
  public static Drug getWithCode(int aCode)
  {
    return drugsByCode.get(aCode);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithCode(int aCode)
  {
    return getWithCode(aCode) != null;
  }
  /* Code from template association_GetOne */
  public Inventory getInventory()
  {
    return inventory;
  }
  /* Code from template association_SetOneToMany */
  public boolean setInventory(Inventory aInventory)
  {
    boolean wasSet = false;
    if (aInventory == null)
    {
      return wasSet;
    }

    Inventory existingInventory = inventory;
    inventory = aInventory;
    if (existingInventory != null && !existingInventory.equals(aInventory))
    {
      existingInventory.removeDrug(this);
    }
    inventory.addDrug(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    drugsByCode.remove(getCode());
    Inventory existingInventory = inventory;
    inventory = null;
    if (existingInventory != null)
    {
      existingInventory.delete();
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
            "  " + "inventory = "+(getInventory()!=null?Integer.toHexString(System.identityHashCode(getInventory())):"null");
  }
}