package es.mdef.clientmanager.s3;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 2/11/14
 * Time: 21:21
 */
public interface FilesService {
    void uploadFile(File file) throws IOException;

    void uploadFile(String name, InputStream inputStream, ObjectMetadata objectMetadata) throws IOException;

    List<S3ObjectSummary> fileList(String key);

    void deleteFile(String name);

    InputStream downloadFile(String name);
}
