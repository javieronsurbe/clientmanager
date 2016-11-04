package es.mdef.clientmanager.ui.util;

import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptAll;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.StreamVariable;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.Html5File;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import es.mdef.clientmanager.ui.component.FilesComponent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * User: jonsurbe
 * Date: 14/10/14
 * Time: 14:52
 */
public class FileDropBox extends DragAndDropWrapper implements DropHandler {
    private static final long FILE_SIZE_LIMIT = 2 * 1024 * 1024; // 2MB

    private FilesComponent filesComponent;
    public FileDropBox(FilesComponent root) {
        super(root);
        this.filesComponent=root;
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
        if(files!=null){
            for (final Html5File html5File : files) {
                final String fileName = html5File.getFileName();
                if (html5File.getFileSize() > FILE_SIZE_LIMIT) {
                    Notification.show("Tamaño máximo del fichero superado",
                            Notification.Type.WARNING_MESSAGE);
                }else{
                    final ByteArrayOutputStream bas = new ByteArrayOutputStream();
                    StreamVariable streamVariable = new StreamVariable() {
                        private ProgressBar progressBar;

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
                            progressBar=filesComponent.addProgressBar();
                        }

                        public void streamingFinished(
                                StreamingEndEvent event) {
                            filesComponent.removeProgressBar(progressBar);
                            try {
                                filesComponent.addFile(fileName, html5File.getType(), bas);
                            } catch (IOException e) {
                                //TODO Tratar excepcion
                                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                            }
                        }

                        public void streamingFailed(
                                StreamingErrorEvent event) {
                            filesComponent.removeProgressBar(progressBar);
                            Notification.show("Ha habido un problema subiendo el fichero " + event.getException().getMessage(), Notification.Type.ERROR_MESSAGE);
                        }

                        public boolean isInterrupted() {
                            return false;
                        }
                    };
                    html5File.setStreamVariable(streamVariable);

                }
            }
        }
    }

    @Override
    public AcceptCriterion getAcceptCriterion() {
        return AcceptAll.get();
    }
}
