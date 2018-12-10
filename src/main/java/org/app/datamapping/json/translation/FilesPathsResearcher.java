package org.app.datamapping.json.translation;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FilesPathsResearcher extends SimpleFileVisitor<Path> {
    private String extension;
    private String tag;

    private List<Path> foundFiles = new ArrayList<>();

    public FilesPathsResearcher(String extension, String tag) {
        this.extension = extension;
        this.tag = tag;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        boolean containsName = true;
        if (extension != null && !file.getFileName().toString().toLowerCase().contains(extension))
            containsName = false;

        String content = new String(Files.readAllBytes(file));
        boolean containsContent = true;
        if (tag != null && !content.contains(tag))
            containsContent = false;

        if (containsName && containsContent)
            foundFiles.add(file);

        return FileVisitResult.CONTINUE;
    }

    public List<Path> getFoundFiles() {
        return foundFiles;
    }
}
