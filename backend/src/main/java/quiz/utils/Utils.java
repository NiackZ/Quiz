package quiz.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Utils {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
    private static final int DEFAULT_LENGTH = 10;

    public static boolean listContainsValuesIgnoreCase(List<String> srcList, List<String> secondList) {
        return listContainsValues(srcList, secondList, true);
    }

    public static boolean listContainsValues(List<String> srcList, List<String> secondList, boolean ignoreCase) {
        return ignoreCase
               ? secondList.stream().allMatch(item -> srcList.stream().anyMatch(e -> e.equalsIgnoreCase(item)))
               : new HashSet<>(srcList).containsAll(secondList);
    }

    public static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf(".");
        if (lastDotIndex != -1 && lastDotIndex < fileName.length() - 1) {
            return fileName.substring(lastDotIndex + 1);
        }
        return "";
    }

    public static String generateRandomString(int length) {

        if (length <= 0) {
            throw new IllegalArgumentException("Число должно быть больше 0");
        }

        StringBuilder randomString = new StringBuilder(length);
        Random random = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

    public static String generateRandomString() {
        return generateRandomString(DEFAULT_LENGTH);
    }

    public static String convertToRelativePath(String absolutePath) {
        Path projectPath = Path.of(Paths.get("").toAbsolutePath() + "/frontend/");
        Path filePath = Paths.get(absolutePath);

        Path relativePath = projectPath.relativize(filePath);

        return "../" + relativePath.toString().replace("\\", "/");
    }

    public static byte[] resizeImage(byte[] originalImage, String formatName, int targetWidth) throws IOException {
        try(ByteArrayInputStream inputStream = new ByteArrayInputStream(originalImage)) {
            BufferedImage originalBufferedImage = ImageIO.read(inputStream);
            int originalWidth = originalBufferedImage.getWidth();
            if (originalWidth <= targetWidth) {
                return originalImage;
            }
            int originalHeight = originalBufferedImage.getHeight();

            // Вычисляем новую высоту, сохраняя пропорции
            int targetHeight = (int) Math.round((double) targetWidth / originalWidth * originalHeight);

            // Создаем новое изображение с уменьшенными размерами
            Image scaledImage = originalBufferedImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            BufferedImage resizedBufferedImage = new BufferedImage(targetWidth, targetHeight, originalBufferedImage.getType());

            Graphics2D g2d = resizedBufferedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();

            // Конвертируем BufferedImage обратно в массив байтов
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedBufferedImage, formatName, outputStream);
            return outputStream.toByteArray();
        }
    }
}
