import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {

    public static void main(String[] args) {
    String saveDirectory = "C:/Games/savegames/";
    String zipFileName = "zip.zip";
    String zipFilePath = saveDirectory + zipFileName;
	GameProgress[] gameProgress = {
            new GameProgress(100, 2, 3, 50.0),
            new GameProgress(90, 5, 8, 110.0),
            new GameProgress(44, 13, 39, 488.3)
            };
    List<String> allSaves = new ArrayList<>();
    saveAllGameProgress(gameProgress, saveDirectory, allSaves);
    zipFiles(zipFilePath, allSaves);
    deleteSaveFiles(allSaves);


    }
    public static void saveAllGameProgress(GameProgress[] gameProgresses, String saveDirectory, List<String> allSaves){
        for(int i = 0; i < gameProgresses.length; i++){
            String saveFileName = "save" + i + ".dat";
            String saveFilePath = saveDirectory + saveFileName;
            allSaves.add(saveFilePath);
            saveGame(saveFilePath, gameProgresses[i]);
        }
    }


    public static void saveGame(String way, GameProgress gameProgress){
        try (FileOutputStream fos = new FileOutputStream(way);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex)
        { System.out.println(ex.getMessage(
        ));
        }
    }

    public static void deleteSaveFiles(List<String> allSaves) {
        for (int i = 0; i < allSaves.size(); i++) {
            String saveFilePath = allSaves.get(i);
            File fileDel = new File(saveFilePath);
            if (!fileDel.delete()) {
                System.out.println("Файл \"" + saveFilePath + "\" НЕ УДАЛЕН !");
            }
        }
    }

    public static void zipFiles(String zipFilePath, List<String> allSaves) {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {
            for (String save : allSaves) {
                File fileToZip = new File(save);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOut.putNextEntry(zipEntry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zipOut.write(buffer);
                    zipOut.closeEntry();

                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
