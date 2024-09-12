package homework12;
import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) throws IOException {
        // Получение списка всех текстовых файлов в текущем каталоге
        String currentDirectory = System.getProperty("user.dir");
        List<String> fileNames = Files.list(Paths.get(currentDirectory))
                .filter(path -> path.toFile().isFile() && path.toString().endsWith(".txt"))
                .map(path -> path.getFileName().toString())
                .sorted()
                .collect(Collectors.toList());

        if (!fileNames.isEmpty()) {
            for (int i = 0; i < fileNames.size(); ++i) {
                System.out.println((i + 1) + ". " + fileNames.get(i));
            }

            Scanner scanner = new Scanner(System.in);
            int index = Integer.parseInt(scanner.nextLine());
            String fileName = fileNames.get(index - 1);

            // Чтение содержимого выбранного файла
            File inputFile = Paths.get(currentDirectory, fileName).toFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла.");
            }

            // Запись новой строки в выбранный файл
            String userInput = "";
            do {
                System.out.print("Введите строку для записи в файл: ");
                userInput = scanner.nextLine();
            } while (!userInput.equalsIgnoreCase("exit"));

            File outputFile = Paths.get(currentDirectory, fileName).toFile();
            try (PrintWriter writer = new PrintWriter(outputFile)) {
                writer.write(userInput);
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл.");
            }
        } else {
            System.out.println("Нет текстовых файлов в текущем каталоге.");
        }
    }
}
