package es.mdef.clientmanager.ui.component;

import com.vaadin.server.StreamVariable;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 2/11/14
 * Time: 13:41
 */
public class CustomStreamVariable implements StreamVariable {
    private final ProgressBar progressBar;
    private final ByteArrayOutputStream bas;

    public CustomStreamVariable(ProgressBar progressBar, ByteArrayOutputStream bas){
        this.progressBar=progressBar;
        this.bas=bas;
    }
    @Override
    public OutputStream getOutputStream() {
        return bas;

    }
    @Override
    public boolean listenProgress() {
        return false;
    }
    @Override
    public void onProgress(StreamingProgressEvent event) {
        Float length=new Float(event.getContentLength());
        Float received=new Float(event.getBytesReceived());
        Float progress= (1/length)*received;

        progressBar.setValue(progress);
    }
    @Override
    public void streamingStarted(
            StreamingStartEvent event) {

    }
    @Override
    public void streamingFinished(
            StreamingEndEvent event) {
        //removeProgressBar(progressBar);
        progressBar.setValue(1f);

        //addFile(fileName, html5File.getType(), bas);
    }
    @Override
    public void streamingFailed(
            StreamingErrorEvent event) {
        progressBar.setVisible(false);
        Notification.show("Ha habido un problema subiendo el fichero " + event.getException().getMessage(), Notification.Type.ERROR_MESSAGE);
    }
    @Override
    public boolean isInterrupted() {
        return false;
    }

}
