package com.d9nich;
//Галайко Данило Олександрович ІП-92 Номер залікової ІП-9206

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.print("Введіть назву файлу: ");
        File inFile = new File(input.nextLine());
        System.out.print("Введіть скільки рядків має бути в файлі: ");
        String[] lines = new String[input.nextInt()];
        input.nextLine();
        System.out.print("Вводьте рядки: ");
        for (int i = 0; i < lines.length; i++)
            lines[i] = input.nextLine();
        FileWork fileWork = new FileWork(inFile);
        fileWork.writeInFile(lines);
        fileWork.splitOfFile();
        fileWork.sortLinesWords();
        System.out.print("Введіть к-ть рядків в другому файлі, які потрібно впорядкувати: ");
        fileWork.sortLines(input.nextInt());
        fileWork.showFiles();
    }
}

class FileWork {
    private File sourceFile;
    private File firstPart = new File("part1.txt");
    private File secondPart = new File("part2.txt");

    public FileWork() {
        this(new File("output.txt"));
    }

    public FileWork(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public File getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(File sourceFile) {
        this.sourceFile = sourceFile;
    }

    public File getFirstPart() {
        return firstPart;
    }

    public void setFirstPart(File firstPart) {
        this.firstPart = firstPart;
    }

    public File getSecondPart() {
        return secondPart;
    }

    public void setSecondPart(File secondPart) {
        this.secondPart = secondPart;
    }

    private static void copyingOfFile(File sourceFile, File outputFile) {
        if (!sourceFile.delete())
            System.out.println("Problem with deleting");
        if (!outputFile.renameTo(sourceFile))
            System.out.println("Problem with copying");
    }

    public void writeInFile(String[] lines) {
        try (PrintWriter output = new PrintWriter(sourceFile)) {
            for (String line : lines)
                output.println(line);
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with opening file");
        }
    }

    public void splitOfFile() {
        try (Scanner input = new Scanner(sourceFile);
             PrintWriter partOutput1 = new PrintWriter(firstPart);
             PrintWriter partOutput2 = new PrintWriter(secondPart)) {
            int counter = 1;
            while (input.hasNext()) {
                if (counter % 2 == 0)
                    partOutput1.println(input.nextLine());
                else
                    partOutput2.println(input.nextLine());
                counter++;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with finding of files");
        }
    }

    public void sortLinesWords() {
        File temp = new File("temp.txt");
        try (PrintWriter output = new PrintWriter(temp);
             Scanner input = new Scanner(firstPart)) {
            while (input.hasNext()) {
                String[] words = input.nextLine().split(" ");
                Arrays.sort(words);
                for (int i = 0; i < words.length - 1; i++)
                    output.print(words[i] + " ");
                output.println(words[words.length - 1]);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with opening file");
        }
        copyingOfFile(firstPart, temp);
    }

    public void sortLines(int n) {
        File temp = new File("temp.txt");
        try (PrintWriter output = new PrintWriter(temp);
             Scanner input = new Scanner(secondPart)) {
            String[] lines = new String[n];
            int counter = 0;
            while (input.hasNext() && counter < n)
                lines[counter++] = input.nextLine();
            Arrays.sort(lines);
            for (int i = 0; i < counter; i++)
                output.println(lines[i]);
            while (input.hasNext())
                output.println(input.nextLine());
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with opening file");
        }
        copyingOfFile(secondPart, temp);
    }

    public void showFiles(){
        System.out.println("Вихідний файл:");
        showFile(sourceFile);
        System.out.println("\nФайл 1(результат роботи):");
        showFile(firstPart);
        System.out.println("\nФайл 2(результат роботи):");
        showFile(secondPart);
    }
    private static void showFile(File name){
        try (Scanner input = new Scanner(name)) {
            while (input.hasNext()) {
                System.out.println(input.nextLine());
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Problem with opening file");
        }
    }
}

