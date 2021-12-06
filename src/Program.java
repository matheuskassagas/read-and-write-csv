import entity.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main (String[] args) {

        Scanner sc = new Scanner(System.in);
        List<Product> list = new ArrayList<>();

        System.out.println("Enter file path: ");
        String sourceFileStr = sc.nextLine(); //caminho da pasta

        File sourceFile = new File(sourceFileStr); // colocando caminho da pasta na variavel.
        String sourceFolderStr = sourceFile.getParent(); // pegando a pasta de sourceFile.

        boolean success = new File(sourceFolderStr + "\\out").mkdir(); // criando arquivo dentro da pasta.

        String targetFileStr = sourceFolderStr + "\\out\\summary.csv";  //sourceFolder é a pasta do caminha que adicionamos + o caminho do novo arquivo que vamos criar..

        try (BufferedReader lendo = new BufferedReader(new FileReader(sourceFileStr))) { //lê o arquivo desejado
            String itemCsv = lendo.readLine();
            while(itemCsv != null){
                String[] fields = itemCsv.split(","); //o metodo split separa as strings pelo parametro adicionado, no caso a virgula. E separa nas posições de um vetor.
                String name = fields[0];
                double price = Double.parseDouble(fields[1]); //Convertendo uma String em DOUBLE.
                int quantity = Integer.parseInt(fields[2]); //Convertendo uma String em Inteiro.

                list.add(new Product(name, price, quantity)); // criamos uma lista de produtos de acordo com o arquivo lido

                itemCsv = lendo.readLine(); //lendo a proxima linha.
            }

            try (BufferedWriter escrevendo = new BufferedWriter(new FileWriter(targetFileStr))) {//escre o arquivo desejado
                for (Product produto : list){
                    escrevendo.write(produto.getName() + ", " + String.format("%.2f", produto.total()));
                    escrevendo.newLine();
                }
                System.out.println(targetFileStr + " CREATED!");

            } catch (IOException e) {
                System.out.println("Error writing file: " + e.getMessage());
            }
        }catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

    }
}
