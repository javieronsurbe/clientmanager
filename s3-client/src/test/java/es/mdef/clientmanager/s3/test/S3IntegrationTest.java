package es.mdef.clientmanager.s3.test;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

/**
 * User: jonsurbe
 * Date: 8/08/14
 * Time: 15:53
 */
public class S3IntegrationTest {

    private BasicAWSCredentials awsCredentials;
    private String bucket="gestionclientes";

    @Before
    public void setup(){
        awsCredentials= new BasicAWSCredentials("AKIAIHKA3CSDFOLD7O4Q", "0UY0lsdl2dpTAEvT/oa8ZsOhB1PWrVKV4dj6GqU6");
    }
    @Test
    public void uploadFile() throws IOException {
        AmazonS3 s3 = new AmazonS3Client(awsCredentials);

        Region region = Region.getRegion(Regions.EU_WEST_1);
        s3.setRegion(region);
        s3.putObject(new PutObjectRequest(bucket, "nombreFichero", createSampleFile()));
    }
    /**
     * Creates a temporary file with text data to demonstrate uploading a file
     * to Amazon S3
     *
     * @return A newly created temporary file with text data.
     *
     * @throws java.io.IOException
     */
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.write("01234567890112345678901234\n");
        writer.write("!@#$%^&*()-=[]{};':',.<>/?\n");
        writer.write("01234567890112345678901234\n");
        writer.write("abcdefghijklmnopqrstuvwxyz\n");
        writer.close();

        return file;
    }
}
