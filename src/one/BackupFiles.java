package one;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BackupFiles {
    public static void main(String[] args) {
        String sourceDirectory = "./";
        String backupDirectory = "./backup";

        createBackup(sourceDirectory, backupDirectory);
    }

    public static void createBackup(String sourceDirectory, String backupDirectory) {
        File directory = new File(sourceDirectory);

        // Проверяем, существует ли исходная директория
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("Исходная директория не существует или не является директорией");
            return;
        }

        // Создаем новую папку для резервных копий
        File backupDir = new File(backupDirectory);
        if (!backupDir.exists()) {
            if (!backupDir.mkdir()) {
                System.out.println("Не удалось создать папку для резервных копий");
                return;
            }
        }

        // Получаем список файлов в исходной директории
        File[] files = directory.listFiles();
        if (files == null) {
            System.out.println("Ошибка при получении списка файлов");
            return;
        }

        // Копируем каждый файл в новую папку
        for (File file : files) {
            if (file.isFile()) {
                try {
                    Path sourcePath = file.toPath();
                    Path targetPath = new File(backupDir, file.getName()).toPath();
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Создана резервная копия файла: " + file.getName());
                } catch (IOException e) {
                    System.out.println("Ошибка при копировании файла: " + file.getName());
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Резервная копия всех файлов создана в папке: " + backupDirectory);
    }
}

