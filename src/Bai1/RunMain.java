package Bai1;

import java.util.*;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;


public class RunMain {
    static Scanner sc = new Scanner(System.in);

    static List<Account> listAccount = new ArrayList<>();
    static FileController fileController = new FileController();

    static Pattern pattern;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static Date dateTime;

    static final String regexUsername = "^[a-zA-Z0-9]{6,}$";
    static final String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*?&])[A-Za-z0-9@$!%*?&]{8,}$";
    static final String regexPhone = "^[0-9\\-\\+]{9,15}$";
    static final String regexEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    public static void main(String[] args) {
        do {
            System.out.println("\n\t++=======* MENU *=======++	");
            System.out.println("1. Create new account.          ");
            System.out.println("2. Login to an existing account.");
            System.out.println("3. Sort accounts by username.   ");
            System.out.println("4. Exit.            			");

            switch (Choose(1,4)) {
                case 1:
                    createNewAccount();
                    break;
                case 2:
                    listAccount = fileController.ReadAccountFromFile("Account.DAT");

                    String username, password;
                    System.out.print("Enter Username: ");
                    username = sc.nextLine();
                    System.out.print("Enter Password: ");
                    password = sc.nextLine();

                    if(checkUsernamePassLogin(username, password)) {
                        //Save the account information just logged in
                        Account account = new Account();
                        for (int i = 0; i < listAccount.size(); i++)
                            if (listAccount.get(i).getUserName().compareTo(username) == 0)
                                account = listAccount.get(i);

                        do {
                            System.out.println("1. Show info.       ");
                            System.out.println("2. Change password. ");
                            System.out.println("3. Log out          ");
                            switch(Choose(1,3)) {
                                case 1:
                                    System.out.format("%-10s %-20s %-20s ", "ID" , "Full name", "Username");
                                    System.out.format("%-30s %-16s %20s \n", "Email", "Phone", "CreateAt");
                                    account.output();
                                    System.out.println();
                                    break;
                                case 2:
                                    changePassword(username, password, account);
                                    System.out.println("Your password has been changed");
                                    break;
                                case 3:
                                    return;
                                default:
                                    System.out.println("Invalid selection!\n");
                            }
                        }while(true);
                    }else
                        System.out.println("Username or password incorrect!");
                    break;
                case 3:
                    listAccount = fileController.ReadAccountFromFile("Account.DAT");
                    Collections.sort(listAccount);
                    System.out.format("%-10s %-20s %-20s ", "ID" , "Full name", "Username");
                    System.out.format("%-30s %-16s %20s \n", "Email", "Phone", "CreateAt");
                    for(int i = 0; i < listAccount.size(); i++)
                        listAccount.get(i).output();
                    System.out.println();
                    break;
                case 4:
                    System.out.println(" Exited the program ");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid selection!\n");
            }
        }while(true);
    }

    // Enter choose
    public static int Choose(int a, int b) {
        int choose;
        do {
            try {
                do {
                    System.out.print("\nInviting you to choose: ");
                    choose = Integer.parseInt(sc.nextLine());
                    if(choose < a || choose > b)
                        System.out.println("Incorrect selection");
                }while (choose < a || choose > b);
                return choose;
            } catch (NumberFormatException e) {
                System.out.println("Invalid data");
            }
        } while(true);
    }

    public static void createNewAccount() {
        Account account = new Account();
        String username, password, confirmPassword, email, phone;

        long ID = maxId();
        ID++;
        account.setId(ID);

        // Enter full name
        System.out.print("\tEnter full name: ");
        account.setFullName(sc.nextLine());

        //Enter user name
        do {
            System.out.print("\tEnter user name: ");
            username = sc.nextLine();
            account.setUserName(username);

            pattern = Pattern.compile(regexUsername);
            if(!pattern.matcher(username).find())
                System.out.println("Invalid username");
            else {
                if(checkExistUsername(username))
                    System.out.println("Username already exists! Re-enter username");
                else
                    break;
            }
        } while(true);

        //Enter password
        do {
            System.out.print("\tEnter password: ");
            password = sc.nextLine();
            account.setPassword(password);

            pattern = Pattern.compile(regexPassword);
            if(!pattern.matcher(password).find())
                System.out.println("Invalid password");
            else
                break;
        } while(true);
        //Enter confirm pass
        do {
            System.out.print("\tConfirm password: ");
            confirmPassword = sc.nextLine();
            if(password.compareTo(confirmPassword) != 0)
                System.out.println("Password and confirmation password do not match");
            else
                break;
        } while(true);

        //Enter email
        do {
            System.out.print("\tEnter email: ");
            email = sc.nextLine();
            account.setEmail(email);

            pattern = Pattern.compile(regexEmail);
            if(!pattern.matcher(email).find())
                System.out.println("Invalid email");
            else
                break;
        } while(true);

        //Enter phone
        do {
            System.out.print("\tEnter phone: ");
            phone = sc.nextLine();
            account.setPhone(phone);

            pattern = Pattern.compile(regexPhone);
            if(!pattern.matcher(phone).find())
                System.out.println("Invalid phone");
            else
                break;
        } while(true);

        dateTime = new Date();
        account.setCreateAt(simpleDateFormat.format(dateTime));

        listAccount.add(account);
        fileController.WriteAccountToFile("Account.DAT", account);
    }


    //check max ID
    public static long maxId() {
        listAccount = fileController.ReadAccountFromFile("Account.DAT");
        long max = 1000;
        for(int i = 0; i < listAccount.size(); i++)
            if(listAccount.get(i).getId() > max)
                max = listAccount.get(i).getId();
        return max;
    }

    // Check username passwork login
    public static boolean checkUsernamePassLogin(String username, String password) {
        listAccount = fileController.ReadAccountFromFile("Account.DAT");
        for(int i = 0; i < listAccount.size(); i++)
            if(listAccount.get(i).getUserName().compareTo(username) == 0
                    && listAccount.get(i).getPassword().compareTo(password) == 0)
                return true;
        return false;
    }

    //check exist user name
    public static boolean checkExistUsername(String username) {
        listAccount = fileController.ReadAccountFromFile("Account.DAT");
        for(int i = 0; i < listAccount.size(); i++)
            if(listAccount.get(i).getUserName().compareTo(username) == 0)
                return true;
        return false;
    }


    public static void changePassword(String username, String oldPassword, Account account) {
        String checkOldPassword, newPassword, confirmNewPassword;
        // Enter old password
        do {
            System.out.print("\tEnter old password: ");
            checkOldPassword = sc.nextLine();

            if(checkOldPassword.compareTo(oldPassword) != 0)
                System.out.println("Your old password is incorrect!");
            else
                break;
        } while(true);

        // Enter new password
        do {
            System.out.print("\tEnter new password: ");
            newPassword = sc.nextLine();

            pattern = Pattern.compile(regexPassword);
            if(newPassword.compareTo(checkOldPassword) == 0)
                System.out.println("New password and old password cannot be the same");
            else if(!pattern.matcher(newPassword).find())
                System.out.println("Invalid password");
            else
                break;
        } while(true);
        // Enter Confirm password
        do {
            System.out.print("\tConfirm new password: ");
            confirmNewPassword = sc.nextLine();
            if(newPassword.compareTo(confirmNewPassword) != 0)
                System.out.println("Password and confirmation password do not match");
            else
                break;
        } while(true);

        // update password
        account.setPassword(newPassword);
        for (int i = 0; i < listAccount.size(); i++)
            if (listAccount.get(i).getUserName().compareTo(username) == 0) {
                listAccount.set(i, account);
                break;
            }
        fileController.UpdateAccountFile(listAccount, "Account.DAT");
    }

}