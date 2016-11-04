package es.mdef.clientmanager.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 27/10/14
 * Time: 23:08
 */
@Service
//TODO Controlar excepciones, expecialmente de perdida de sesion
public class FilesServiceImpl implements FilesService {

    static final Logger LOG = LoggerFactory.getLogger(FilesService.class);

    private BasicAWSCredentials awsCredentials;


    @Value("#{systemEnvironment['AMAZON_BUCKET']}")
    private String bucket;
    @Value("#{systemEnvironment['AMAZON_ACCESSKEY']}")
    private String accessKey;
    @Value("#{systemEnvironment['AMAZON_SECRETKEY']}")
    private String secretKey;

    private AmazonS3 s3;

    @PostConstruct
    public void init(){
        awsCredentials=new BasicAWSCredentials(accessKey,secretKey);
        s3 = new AmazonS3Client(awsCredentials);
        Region region = Region.getRegion(Regions.EU_WEST_1);
        s3.setRegion(region);

    }
    @Override
    public void uploadFile(File file) throws IOException {
        s3.putObject(new PutObjectRequest(bucket, file.getName(), file));
    }
    @Override
    public void uploadFile(String name, InputStream inputStream, ObjectMetadata objectMetadata) throws IOException {
        PutObjectResult putObjectResult=s3.putObject(new PutObjectRequest(bucket, name, inputStream, objectMetadata));
    }

    @Override
    public List<S3ObjectSummary> fileList(String key){
        try {
            ObjectListing listing = s3.listObjects(bucket, key);
            List<S3ObjectSummary> s3ObjectSummaryList = listing.getObjectSummaries();
            return s3ObjectSummaryList;
        }catch (AmazonClientException exception){
            LOG.error("Problem with S3="+exception.getMessage());
            return new ArrayList<S3ObjectSummary>();
        }
    }

    @Override
    public void deleteFile(String name){
        s3.deleteObject(bucket, name);
    }

    @Override
    public InputStream downloadFile(String name){
        S3Object s3Object=s3.getObject(bucket, name);
        return s3Object.getObjectContent();
    }
    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

}
