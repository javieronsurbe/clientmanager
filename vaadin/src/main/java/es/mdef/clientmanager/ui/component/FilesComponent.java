package es.mdef.clientmanager.ui.component;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.*;
import es.mdef.clientmanager.domain.Client;
import es.mdef.clientmanager.s3.FilesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.io.*;
import java.util.List;

/**
 * User: jonsurbe
 * Date: 16/10/14
 * Time: 18:42
 */
@Configurable(preConstruction = true)
public class FilesComponent extends VerticalLayout {
    static final Logger LOG = LoggerFactory.getLogger(FilesComponent.class);

    private VerticalLayout dropLayout=new VerticalLayout();
    private VerticalLayout fileListLayout=new VerticalLayout();

    @Autowired
    private FilesService filesService;

    private List<S3ObjectSummary> fileList;

    private Client client;
    public FilesComponent(Client client){
        setSizeFull();

        this.client=client;
        dropLayout.setSizeFull();
        dropLayout.addComponent(fileListLayout);

        addComponent(new FileDropBox(dropLayout));

        fileList=filesService.fileList(client.getId().toString());
        for(S3ObjectSummary fileSummary:fileList){
            FileResourceComponent fileResourceComponent =
                    new FileResourceComponent(fileSummary.getKey().split("/")[1], fileSummary.getLastModified(),
                            fileSummary.getSize(), fileSummary.getKey());
            fileListLayout.addComponent(fileResourceComponent);
        }
    }
    public ProgressBar addProgressBar(){
        ProgressBar progressBar=new ProgressBar();
        progressBar.setStyleName("big");
        progressBar.setVisible(true);
        progressBar.setValue(0f);
        addComponent(progressBar);
        return progressBar;
    }
    public void removeProgressBar(ProgressBar progressBar){
        progressBar.setVisible(false);
        removeComponent(progressBar);
    }
    public void addFile(String name, String type, final ByteArrayOutputStream bas) throws IOException {
        StreamResource.StreamSource streamSource = new StreamResource.StreamSource() {
            public InputStream getStream() {
                if (bas != null) {
                    byte[] byteArray = bas.toByteArray();
                    return new ByteArrayInputStream(byteArray);
                }
                return null;
            }
        };

        filesService.uploadFile(client.getId()+"/"+name,streamSource.getStream(),new ObjectMetadata());
        List<S3ObjectSummary> fileList=filesService.fileList(client.getId().toString());
        fileListLayout.removeAllComponents();
        for(S3ObjectSummary fileSummary:fileList){
            FileResourceComponent fileResourceComponent =
                    new FileResourceComponent(fileSummary.getKey().split("/")[1], fileSummary.getLastModified(),
                            fileSummary.getSize(), fileSummary.getKey());
            fileListLayout.addComponent(fileResourceComponent);
        }
    }
    private class FileDropBox extends DragAndDropWrapper implements DropHandler {
        private static final long FILE_SIZE_LIMIT = 2 * 1024 * 1024; // 2MB

        public FileDropBox(Component root) {
            super(root);
            setSizeFull();
            setDropHandler(this);
            setDragStartMode(DragStartMode.HTML5);
            setHTML5DataFlavor("text/plain",
                    "This text was drag'n'dropped from Vaadin.");
            setHTML5DataFlavor("text/html",
                    "<h1>HTML Content</h1><p>This text was drag&quot;n&quot;dropped from Vaadin.");
        }

        @Override
        public void drop(DragAndDropEvent dragAndDropEvent) {
            WrapperTransferable tr = (WrapperTransferable) dragAndDropEvent.getTransferable();
            Html5File[] files = tr.getFiles();
            UI.getCurrent().setPollInterval(500);
            if(files!=null){
                for (final Html5File html5File : files) {
                    final String fileName = html5File.getFileName();
                    if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
                        Notification.show("Tamaño máximo del fichero superado",
                                Notification.Type.WARNING_MESSAGE);
                    }else{
                        final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                        Notification.show("Fichero arrastrado");

                        html5File.setStreamVariable(new StreamVariable() {
                            final ProgressBar progressBar=addProgressBar();
                            public OutputStream getOutputStream() {
                                return bas;

                            }

                            public boolean listenProgress() {
                                return false;
                            }

                            public void onProgress(StreamingProgressEvent event) {
                                Float length=new Float(event.getContentLength());
                                Float received=new Float(event.getBytesReceived());
                                Float progress= (1/length)*received;

                                progressBar.setValue(progress);
                            }

                            public void streamingStarted(
                                    StreamingStartEvent event) {

                            }

                            public void streamingFinished(
                                    StreamingEndEvent event) {
                                removeProgressBar(progressBar);
                                progressBar.setValue(1f);

                                try {
                                    addFile(fileName, html5File.getType(), bas);
                                } catch (IOException e) {
                                    //TODO Tratar excepcion
                                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                                }
                            }

                            public void streamingFailed(
                                    StreamingErrorEvent event) {
                                removeProgressBar(progressBar);
                                Notification.show("Fichero subido");
                            }

                            public boolean isInterrupted() {
                                return false;
                            }
                        });


                    }
                }
            }
        }

        @Override
        public AcceptCriterion getAcceptCriterion() {
            return AcceptAll.get();
        }
    }
    public FilesService getFilesService() {
        return filesService;
    }

    public void setFilesService(FilesService filesService) {
        this.filesService = filesService;
    }
}
