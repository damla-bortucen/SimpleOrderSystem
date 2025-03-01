import java.util.ArrayList;

public class SimpleOrderSystem
{
  public static final int ADD_CUSTOMER = 1;
  public static final int ADD_ORDER = 2;
  public static final int ADD_PRODUCT = 3;
  public static final int LIST_CUSTOMERS = 4;
  public static final int OVERALL_TOTAL = 5;
  public static final int PRODUCT_ORDERS = 6;
  public static final int CUSTOMER_ORDERS = 7;
  public static final int QUIT = 10;
  private Input in = new Input();
  private ArrayList<Customer> customers;
  private ArrayList<Product> products;

  public SimpleOrderSystem()
  {
    customers = new ArrayList<Customer>();
    products = new ArrayList<Product>();
  }

  public void run()
  {
    while(true)
    {
      displayMenu();
      int option = getMenuInput();
      if (option == QUIT)
      {
        break;
      }
      doOption(option);
    }
  }

  private void displayMenu()
  {
    System.out.println("Simple Order System Menu");
    System.out.println(ADD_CUSTOMER + ". Add Customer");
    System.out.println(ADD_ORDER + ". Add Order");
    System.out.println(ADD_PRODUCT + ". Add Product");
    System.out.println(LIST_CUSTOMERS + ". List Customers");
    System.out.println(OVERALL_TOTAL + ". Display the Total Value of All Orders for All Customers");
    System.out.println(PRODUCT_ORDERS + ". Display all Orders of a Product");
    System.out.println(CUSTOMER_ORDERS + ". Display all Orders made by a Customer");
    System.out.println();
    System.out.println(QUIT + ". Quit");
  }
  
  private void doOption(int option)
  {
    switch (option)
    {
      case ADD_CUSTOMER:
        addCustomer();
        break;
      case ADD_ORDER:
         addOrder();
        break;
      case ADD_PRODUCT:
         addProduct();
         break;
      case LIST_CUSTOMERS:
        listCustomers();
        break;
      case OVERALL_TOTAL:
        overallTotal();
        break;
      case PRODUCT_ORDERS:
        listProductOrders();
        break;
      case CUSTOMER_ORDERS:
        listCustomerOrders();
        break;
      default:
        System.out.println("Invalid option - try again");
    }
  }

  private int getMenuInput()
  {
    System.out.print("Enter menu selection: ");
    int option = in.nextInt();
    in.nextLine();
    return option;
  }

  private void addCustomer()
  {
    System.out.println("Add new customer");
    System.out.println("Enter first name:");
    String firstName = in.nextLine();
    System.out.println("Enter last name:");
    String lastName = in.nextLine();
    System.out.println("Enter address:");
    String address = in.nextLine();
    System.out.println("Enter postcode:");
    String postcode = in.nextLine();
    System.out.println("Enter phone number:");
    String phone = in.nextLine();
    System.out.println("Enter email address:");
    String email = in.nextLine();
    Customer customer = new Customer(firstName,lastName,address,postcode,phone,email);
    customers.add(customer);
  }

  private void addOrder()
  {
    Customer customer = findCustomer();
    if (customer == null)
    {
      System.out.println("Unable to add order");
      return;
    }
    Order order = new Order();
    addLineItems(order);
    if (order.getLineItemCount() == 0)
    {
      System.out.println("Cannot have an empty order");
      return;
    }
    customer.addOrder(order);
  }

  private Customer findCustomer()
  {
    System.out.print("Enter customer last name: ");
    String lastName = in.nextLine();
    System.out.print("Enter customer first name: ");
    String firstName = in.nextLine();
    return getCustomer(lastName, firstName);
  }

  private Customer getCustomer(String lastName, String firstName)
  {
    for (Customer customer : customers)
    {
      if (customer.getLastName().equals(lastName)
          && customer.getFirstName().equals(firstName))
      {
        return customer;
      }
    }
    return null;
  }

  private void addLineItems(Order order)
  {
    while (true)
    {
      System.out.print("Enter line item (y/n): ");
      String reply = in.nextLine();
      if (reply.startsWith("y"))
      {
        LineItem item = getLineItem();
        if (item != null)
        {
          order.add(item);
        }
      }
      else
      {
        break;
      }
    }
  }

  private LineItem getLineItem()
  {
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    Product product = getProduct(code);
    if (product == null)
    {
      System.out.println("Invalid product code");
      return null;
    }
    System.out.print("Enter quantity: ");
    int quantity = in.nextInt();
    in.nextLine();
    return new LineItem(quantity,product);
  }

  private Product getProduct(int code)
  {
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return product;
      }
    }
    return null;
  }

  private void addProduct()
  {
    System.out.println("Product types: ");
    System.out.println("1. Book");
    System.out.println("2. Pen");
    System.out.print("Choose product type:");
    int choice = in.nextInt();
    System.out.print("Enter product code: ");
    int code = in.nextInt();
    in.nextLine();
    if (!isAvailableProductCode(code))
    {
      return;
    }
    System.out.print("Enter product price: ");
    int price = in.nextInt();
    in.nextLine();

    Product product;

    switch (choice)
    {
      case 1: // book
        System.out.print("Enter title: ");
        String title = in.nextLine();
        System.out.print("Enter author: ");
        String author = in.nextLine();
        System.out.print("Enter page number: ");
        int pages = in.nextInt();
        product = new Book(code, price, title, author, pages);
        break;
      case 2: // pen
        System.out.print("Enter color: ");
        String color = in.nextLine();
        product = new Pen(code, price, color);
      default:
        return;
    }
    products.add(product);
  }

  private boolean isAvailableProductCode(int code)
  {
    if (code < 1)
    {
      return false;
    }
    for (Product product : products)
    {
      if (product.getCode() == code)
      {
        return false;
      }
    }
    return true;
  }

  public void listCustomers()
  {
    System.out.println("List of customers");
    for (Customer customer : customers)
    {
      System.out.println("Name: " + customer.getLastName()
                                  + ", "
                                  + customer.getFirstName());
      System.out.println("Address: " + customer.getAddress());
      System.out.println("Postcode: " + customer.getPostcode());
      System.out.println("Phone: " + customer.getPhone());
      System.out.println("Email: " + customer.getEmail());
      System.out.println("Orders made: " + customer.getOrders().size());
      System.out.println("Total for all orders: " + customer.getTotalForAllOrders());
    }
  }

  public void overallTotal()
  {
    int total = 0;
    for (Customer customer : customers) {
      total += customer.getTotalForAllOrders();
    }
    System.out.println("Overall total: " + "$" + total);
  }


  public void listProductOrders()
  {
    System.out.print("Enter the code for the product whose orders you would like to see: ");
    int code = in.nextInt();
    int ordNo = 1;

    for (Customer c : customers) {
      for (Order o : c.getOrders()) {
        for (LineItem l : o.getLineItems()) {
          if (l.getProduct().getCode() == code) {
            System.out.println("The order no. " + ordNo + " of the product with the code "+ code +": ");
            System.out.println("Customer: " + c.getFirstName() + " " + c.getLastName());
            System.out.println("Product Description: " + l.getProduct().getDescription());
            System.out.println("Amount of Product: " + l.getQuantity());
            ordNo += 1;
          }
        }
      }
    }
  }

  public void listCustomerOrders()
  {
    System.out.println("Here are all the customers in the system: ");
    for (int i=0; i < customers.size();i++)
    {
      System.out.println(i + ". Name: " + customers.get(i).getLastName() + ", " + customers.get(i).getFirstName());
    }
    System.out.print("Enter the number of the customer whose orders you would like to see: ");
    int custNum = in.nextInt();
    if (custNum >= customers.size()) {
      System.out.println("Number doesn't correspond to a customer");
      return;
    }
    Customer customer = customers.get(custNum);
    System.out.println("Orders of " + customer.getFirstName() + " " + customer.getLastName() + ":");
    for (Order o: customer.getOrders()) {
      for (LineItem l : o.getLineItems()) {
        System.out.println("Product Code: " + l.getProduct().getCode()
                + ", Amount: " + l.getQuantity()
                + ", Product Description: " + l.getProduct().getDescription());
      }
    }
  }


  public void addData()
  {
    Customer damla = new Customer("Damla", "Bortucen", "London", "WA1C", "123456", "db@gmail.com");
    Customer alina = new Customer("Alina", "Casal", "Barcelona", "08008", "78910", "ac@gmail.com");
    customers.add(damla);
    customers.add(alina);

    Product book = new Book(1, 25, "To kill a mockingbird", "Harper Lee", 323);
    Product pen = new Pen(2, 1, "blue");
    products.add(book);
    products.add(pen);

    Order ord1 = new Order();
    LineItem li1 = new LineItem(5, book);
    ord1.add(li1);
    damla.addOrder(ord1);

    Order ord2 = new Order();
    LineItem li2 = new LineItem(10, pen);
    ord2.add(li2);
    alina.addOrder(ord2);
  }

  public static void main(String[] args)
  {
    SimpleOrderSystem orderSystem = new SimpleOrderSystem();
    orderSystem.addData();
    orderSystem.run();
  }
}
