package jar.utils;

import controllers.Controller;
import jar.manipulators.packages.AddedPackages;
import javassist.CtClass;
import javassist.NotFoundException;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.List;

public class JarSaver implements Runnable{

    private String jarPath;

    /**
     *
     * @param jarPath path where new jar will be saved
     */
    public JarSaver(String jarPath){
        this.jarPath=jarPath;
    }

    /**
     * Save jar
     * @throws IOException
     * @throws NotFoundException
     */
    public  void saveFile() throws IOException, NotFoundException {


        List<String> names = TreeBuilder.getInstance().loadClasses();

        unzipJar(jarPath);

        for (String name : names) {
            CtClass toWrite = TreeBuilder.getClassPool().getCtClass(name.replace('/', '.').substring(0, name.length() - 6));
            toWrite.debugWriteFile(jarPath);
        }

        if (!AddedPackages.getInstance().getAddedPackagess().isEmpty()) {
            for (String f : AddedPackages.getInstance().getAddedPackagess()) {
                String temp = f.replace(".", "/");
                new File(jarPath + "\\" + temp).mkdirs();
            }
        }

        zipFile(jarPath);
        File directoryToRemove=new File(jarPath);
        FileUtils.deleteDirectory(directoryToRemove);


    }

    /**
     * Override run method from Runnable interface
     */
    @Override
    public void run() {
        try {
            saveFile();
        } catch (IOException|NotFoundException e) {
            Controller.showError(e.getMessage());
        }
    }

    /**
     * Unzip jar file
     * @param jarPath
     * @throws IOException
     */
    public  void unzipJar(String jarPath) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(TreeBuilder.getInstance().getPath());
            zipFile.extractAll(jarPath);
        } catch (ZipException e) {
            Controller.showError(e.getMessage());
        }
    }

    /**
     * Zip jar file
     * @param jarPath
     */
    public  void zipFile(String jarPath){
        try {
            ZipFile zipFile=new ZipFile(jarPath+".jar");
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setIncludeRootFolder(false);

            zipFile.addFolder(jarPath, parameters);

        } catch (ZipException e) {
            Controller.showError(e.getMessage());
        }
    }


}
