/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package dms.model;
import java.io.Serializable;
import java.util.*;
import java.sql.Date;

/**
 * the reinitialize methods need to be added
 */
// line 5 "../../DMS_Persistence.ump"
// line 5 "../../DMS_Model.ump"
public class DMS implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DMS Associations
  private List<User> users;
  private List<UserRole> userRoles;
  private List<Order> orders;
  private List<Receipt> receipts;
  private List<Inventory> inventories;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DMS()
  {
    users = new ArrayList<User>();
    userRoles = new ArrayList<UserRole>();
    orders = new ArrayList<Order>();
    receipts = new ArrayList<Receipt>();
    inventories = new ArrayList<Inventory>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public UserRole getUserRole(int index)
  {
    UserRole aUserRole = userRoles.get(index);
    return aUserRole;
  }

  public List<UserRole> getUserRoles()
  {
    List<UserRole> newUserRoles = Collections.unmodifiableList(userRoles);
    return newUserRoles;
  }

  public int numberOfUserRoles()
  {
    int number = userRoles.size();
    return number;
  }

  public boolean hasUserRoles()
  {
    boolean has = userRoles.size() > 0;
    return has;
  }

  public int indexOfUserRole(UserRole aUserRole)
  {
    int index = userRoles.indexOf(aUserRole);
    return index;
  }
  /* Code from template association_GetMany */
  public Order getOrder(int index)
  {
    Order aOrder = orders.get(index);
    return aOrder;
  }

  public List<Order> getOrders()
  {
    List<Order> newOrders = Collections.unmodifiableList(orders);
    return newOrders;
  }

  public int numberOfOrders()
  {
    int number = orders.size();
    return number;
  }

  public boolean hasOrders()
  {
    boolean has = orders.size() > 0;
    return has;
  }

  public int indexOfOrder(Order aOrder)
  {
    int index = orders.indexOf(aOrder);
    return index;
  }
  /* Code from template association_GetMany */
  public Receipt getReceipt(int index)
  {
    Receipt aReceipt = receipts.get(index);
    return aReceipt;
  }

  public List<Receipt> getReceipts()
  {
    List<Receipt> newReceipts = Collections.unmodifiableList(receipts);
    return newReceipts;
  }

  public int numberOfReceipts()
  {
    int number = receipts.size();
    return number;
  }

  public boolean hasReceipts()
  {
    boolean has = receipts.size() > 0;
    return has;
  }

  public int indexOfReceipt(Receipt aReceipt)
  {
    int index = receipts.indexOf(aReceipt);
    return index;
  }
  /* Code from template association_GetMany */
  public Inventory getInventory(int index)
  {
    Inventory aInventory = inventories.get(index);
    return aInventory;
  }

  public List<Inventory> getInventories()
  {
    List<Inventory> newInventories = Collections.unmodifiableList(inventories);
    return newInventories;
  }

  public int numberOfInventories()
  {
    int number = inventories.size();
    return number;
  }

  public boolean hasInventories()
  {
    boolean has = inventories.size() > 0;
    return has;
  }

  public int indexOfInventory(Inventory aInventory)
  {
    int index = inventories.indexOf(aInventory);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aUsername, UserRole aUserRole)
  {
    return new User(aUsername, aUserRole, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    DMS existingDMS = aUser.getDMS();
    boolean isNewDMS = existingDMS != null && !this.equals(existingDMS);
    if (isNewDMS)
    {
      aUser.setDMS(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a dMS
    if (!this.equals(aUser.getDMS()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUserRoles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUserRole(UserRole aUserRole)
  {
    boolean wasAdded = false;
    if (userRoles.contains(aUserRole)) { return false; }
    DMS existingDMS = aUserRole.getDMS();
    boolean isNewDMS = existingDMS != null && !this.equals(existingDMS);
    if (isNewDMS)
    {
      aUserRole.setDMS(this);
    }
    else
    {
      userRoles.add(aUserRole);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUserRole(UserRole aUserRole)
  {
    boolean wasRemoved = false;
    //Unable to remove aUserRole, as it must always have a dMS
    if (!this.equals(aUserRole.getDMS()))
    {
      userRoles.remove(aUserRole);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserRoleAt(UserRole aUserRole, int index)
  {  
    boolean wasAdded = false;
    if(addUserRole(aUserRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserRoles()) { index = numberOfUserRoles() - 1; }
      userRoles.remove(aUserRole);
      userRoles.add(index, aUserRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserRoleAt(UserRole aUserRole, int index)
  {
    boolean wasAdded = false;
    if(userRoles.contains(aUserRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserRoles()) { index = numberOfUserRoles() - 1; }
      userRoles.remove(aUserRole);
      userRoles.add(index, aUserRole);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserRoleAt(aUserRole, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOrders()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Order addOrder(Date aDate, Pharmacist aPharmacist, Administrator aAdministrator)
  {
    return new Order(aDate, aPharmacist, aAdministrator, this);
  }

  public boolean addOrder(Order aOrder)
  {
    boolean wasAdded = false;
    if (orders.contains(aOrder)) { return false; }
    DMS existingDMS = aOrder.getDMS();
    boolean isNewDMS = existingDMS != null && !this.equals(existingDMS);
    if (isNewDMS)
    {
      aOrder.setDMS(this);
    }
    else
    {
      orders.add(aOrder);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeOrder(Order aOrder)
  {
    boolean wasRemoved = false;
    //Unable to remove aOrder, as it must always have a dMS
    if (!this.equals(aOrder.getDMS()))
    {
      orders.remove(aOrder);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOrderAt(Order aOrder, int index)
  {  
    boolean wasAdded = false;
    if(addOrder(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOrderAt(Order aOrder, int index)
  {
    boolean wasAdded = false;
    if(orders.contains(aOrder))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOrders()) { index = numberOfOrders() - 1; }
      orders.remove(aOrder);
      orders.add(index, aOrder);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOrderAt(aOrder, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfReceipts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Receipt addReceipt(double aTotalPrice)
  {
    return new Receipt(aTotalPrice, this);
  }

  public boolean addReceipt(Receipt aReceipt)
  {
    boolean wasAdded = false;
    if (receipts.contains(aReceipt)) { return false; }
    DMS existingDMS = aReceipt.getDMS();
    boolean isNewDMS = existingDMS != null && !this.equals(existingDMS);
    if (isNewDMS)
    {
      aReceipt.setDMS(this);
    }
    else
    {
      receipts.add(aReceipt);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeReceipt(Receipt aReceipt)
  {
    boolean wasRemoved = false;
    //Unable to remove aReceipt, as it must always have a dMS
    if (!this.equals(aReceipt.getDMS()))
    {
      receipts.remove(aReceipt);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addReceiptAt(Receipt aReceipt, int index)
  {  
    boolean wasAdded = false;
    if(addReceipt(aReceipt))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReceipts()) { index = numberOfReceipts() - 1; }
      receipts.remove(aReceipt);
      receipts.add(index, aReceipt);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveReceiptAt(Receipt aReceipt, int index)
  {
    boolean wasAdded = false;
    if(receipts.contains(aReceipt))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfReceipts()) { index = numberOfReceipts() - 1; }
      receipts.remove(aReceipt);
      receipts.add(index, aReceipt);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addReceiptAt(aReceipt, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfInventories()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Inventory addInventory(char aFirstLetter)
  {
    return new Inventory(aFirstLetter, this);
  }

  public boolean addInventory(Inventory aInventory)
  {
    boolean wasAdded = false;
    if (inventories.contains(aInventory)) { return false; }
    DMS existingDMS = aInventory.getDMS();
    boolean isNewDMS = existingDMS != null && !this.equals(existingDMS);
    if (isNewDMS)
    {
      aInventory.setDMS(this);
    }
    else
    {
      inventories.add(aInventory);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInventory(Inventory aInventory)
  {
    boolean wasRemoved = false;
    //Unable to remove aInventory, as it must always have a dMS
    if (!this.equals(aInventory.getDMS()))
    {
      inventories.remove(aInventory);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addInventoryAt(Inventory aInventory, int index)
  {  
    boolean wasAdded = false;
    if(addInventory(aInventory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInventories()) { index = numberOfInventories() - 1; }
      inventories.remove(aInventory);
      inventories.add(index, aInventory);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInventoryAt(Inventory aInventory, int index)
  {
    boolean wasAdded = false;
    if(inventories.contains(aInventory))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInventories()) { index = numberOfInventories() - 1; }
      inventories.remove(aInventory);
      inventories.add(index, aInventory);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInventoryAt(aInventory, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (userRoles.size() > 0)
    {
      UserRole aUserRole = userRoles.get(userRoles.size() - 1);
      aUserRole.delete();
      userRoles.remove(aUserRole);
    }
    
    while (orders.size() > 0)
    {
      Order aOrder = orders.get(orders.size() - 1);
      aOrder.delete();
      orders.remove(aOrder);
    }
    
    while (receipts.size() > 0)
    {
      Receipt aReceipt = receipts.get(receipts.size() - 1);
      aReceipt.delete();
      receipts.remove(aReceipt);
    }
    
    while (inventories.size() > 0)
    {
      Inventory aInventory = inventories.get(inventories.size() - 1);
      aInventory.delete();
      inventories.remove(aInventory);
    }
    
  }

  // line 10 "../../DMS_Persistence.ump"
   public void reinitialize(){
    User.reinitializeUniqueUserName(this.getUsers());
		Inventory.reinitializeDrugPriority(this.getInventories());
  }

  // line 13 "../../DMS_Model.ump"
   public Inventory findInventory(char firstLetter){
    for(Inventory inv : this.getInventories()) {
			if(firstLetter == inv.getFirstLetter()) {
				return inv;
			}
		}
		
		return null;
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 8 "../../DMS_Persistence.ump"
  private static final long serialVersionUID = 6181302407834705923L ;

  
}