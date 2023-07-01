package twothree;

import java.io.*;

public class TicTacToe {
    public static void main(String[] args) {
        int[] field = {1, 0, 3, 2, 0, 0, 1, 2, 3};

        String fileName = "field.txt";

        // Записываем состояние игрового поля в файл
        writeFieldToFile(field, fileName);

        // Разворачиваем и отображаем состояние игрового поля из файла
        int[] restoredField = restoreFieldFromFile(fileName);
        printField(restoredField);
    }

    public static void writeFieldToFile(int[] field, String fileName) {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName))) {
            // Записываем состояние каждой ячейки в файл (сжимаем 9 чисел в 3 байта)
            int packedField = packField(field);
            dos.writeInt(packedField);
            System.out.println("Состояние игрового поля записано в файл: " + fileName);
        } catch (IOException e) {
            System.out.println("Ошибка при записи в файл: " + fileName);
            e.printStackTrace();
        }
    }

    public static int[] restoreFieldFromFile(String fileName) {
        int[] field = new int[9];
        try (DataInputStream dis = new DataInputStream(new FileInputStream(fileName))) {
            // Считываем состояние игрового поля из файла (распаковываем 3 байта в 9 чисел)
            int packedField = dis.readInt();
            unpackField(packedField, field);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + fileName);
            e.printStackTrace();
        }
        return field;
    }

    public static void printField(int[] field) {
        System.out.println("Состояние игрового поля:");
        for (int i = 0; i < field.length; i++) {
            char symbol;
            switch (field[i]) {
                case 0:
                    symbol = '_';
                    break;
                case 1:
                    symbol = 'X';
                    break;
                case 2:
                    symbol = 'O';
                    break;
                default:
                    symbol = '.';
                    break;
            }
            System.out.print(symbol + " ");
            if ((i + 1) % 3 == 0) {
                System.out.println();
            }
        }
    }

    public static int packField(int[] field) {
        int packedField = 0;
        for (int i = 0; i < field.length; i++) {
            int value = field[i];
            packedField |= value << (2 * i);
        }
        return packedField;
    }

    public static void unpackField(int packedField, int[] field) {
        for (int i = 0; i < field.length; i++) {
            int value = (packedField >> (2 * i)) & 0b11;
            field[i] = value;
        }
    }
}

