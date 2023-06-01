package Bai1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class FileController {
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;
    private PrintWriter printWriter;
    private Scanner scanner;

    //Open file
    public void openFileToWrite(String fileName) {
        try {
            fileWriter = new FileWriter(fileName, true);
            bufferedWriter = new BufferedWriter(fileWriter);
            printWriter = new PrintWriter(bufferedWriter);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //Close file
    public void closeFileAfterWrite(String fileName) {
        try {
            printWriter.close();
            bufferedWriter.close();
            fileWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //Open to read
    public void openFileToRead(String fileName){
        try {
            scanner = new Scanner(Paths.get(fileName));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeFileAfterRead(String fileName){
        scanner.close();
    }


    //Write Account
    public void WriteAccountToFile(String fileName, Account acc){
        openFileToWrite(fileName);

        printWriter.println (
                acc.getId() + "|" + acc.getFullName() + "|" + acc.getUserName() + "|" +
                        acc.getPassword() + "|" + acc.getEmail() + "|" + acc.getPhone() + "|" +
                        acc.getCreateAt()
        );
        closeFileAfterWrite(fileName);
    }

    // Data file
    public Account CreateAccountFromData(String data){
        String[] datas = data.split("\\|");
        Account account = new Account(Integer.parseInt(datas[0]), datas[1], datas[2],
                datas[3],datas[4],datas[5],datas[6]);
        return account;
    }

    // Read file
    public List<Account> ReadAccountFromFile(String fileName){
        openFileToRead(fileName);
        List<Account> listAccount = new ArrayList<>();
        while(scanner.hasNext()){
            String data = scanner.nextLine();
            Account account = CreateAccountFromData(data);
            listAccount.add(account);
        }
        closeFileAfterRead(fileName);
        return listAccount;
    }

    //update file
    public void UpdateAccountFile(List<Account> listAccount, String fileName){
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        openFileToWrite(fileName);
        for(Account account : listAccount){
            printWriter.println(account.getId() + "|" + account.getFullName() + "|" +
                    account.getUserName() + "|" + account.getPassword() + "|" +
                    account.getEmail() + "|" + account.getPhone() + "|" +
                    account.getCreateAt());
        }
        closeFileAfterWrite(fileName);
    }
}
